package com.fbi.securityguard.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.fbi.securityguard.db.TrafficDataDbManager;
import com.fbi.securityguard.entity.AppInfo;
import com.fbi.securityguard.entity.AppTrafficInfo;
import com.fbi.securityguard.entity.TrafficData;
import com.fbi.securityguard.model.modelinterface.AppTrafficModelInterface;
import com.fbi.securityguard.utils.Commons;
import com.fbi.securityguard.utils.TrafficUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * author: bo on 2016/3/31 20:08.
 * email: bofu1993@163.com
 */
public class AppTrafficModel implements AppTrafficModelInterface {

  private static final String MOBILE_TRAFFIC_SHAREDPREFERENCES = Commons.PACKAGE_NAME + "" +
          ".sharedPreferences_traffic_mobile";
  private static final String WIFI_TRAFFIC_SHAREDPREFERENCES = Commons.PACKAGE_NAME + "" +
          ".sharedPreferences_traffic_wifi";
  List<AppTrafficInfo> appTrafficInfos = new ArrayList<>();
  private Context context;
  private long totalTraffic = 0;
  private long totalRxTraffic = 0;
  private long totalTxTraffic = 0;
  private SharedPreferences mobileTrafficSharedPreferences;
  private SharedPreferences wifiTrafficSharedPreferences;
  private TrafficDataDbManager trafficDataDbManager;

  public AppTrafficModel(Context context) {
    this.context = context;
    mobileTrafficSharedPreferences = this.context.getSharedPreferences
            (MOBILE_TRAFFIC_SHAREDPREFERENCES, Context.MODE_PRIVATE);
    wifiTrafficSharedPreferences = this.context.getSharedPreferences
            (WIFI_TRAFFIC_SHAREDPREFERENCES, Context.MODE_PRIVATE);
    trafficDataDbManager = TrafficDataDbManager.getInstance(context);
  }

  public List<AppInfo> queryAppWithNet() {
    List<AppInfo> appInfos = new ArrayList<>();
    List<PackageInfo> packageInfos = context.getPackageManager().getInstalledPackages
            (PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_PERMISSIONS);
    for (int i = 0; i < packageInfos.size(); i++) {
      String[] permissions = packageInfos.get(i).requestedPermissions;
      if (permissions != null && permissions.length > 0) {
        for (String permission : permissions) {
          if ("android.permission.INTERNET".equals(permission)) {
            AppInfo appInfo = new AppInfo();
            appInfo.setAppName(packageInfos.get(i).applicationInfo.loadLabel(context
                    .getPackageManager()).toString());
            appInfo.setAppIcon(packageInfos.get(i).applicationInfo.loadIcon(context
                    .getPackageManager()));
            appInfo.setAppInstallTime(packageInfos.get(i).firstInstallTime);
            appInfo.setAppLastUpdateTime(packageInfos.get(i).lastUpdateTime);
            appInfo.setPackageName(packageInfos.get(i).packageName);
            appInfo.setVersionName(packageInfos.get(i).versionName);
            appInfo.setUid(packageInfos.get(i).applicationInfo.uid);
            appInfos.add(appInfo);
          }
        }
      }
    }
    return appInfos;
  }

  private void queryTraffic() {
    Date now = new Date();
    appTrafficInfos.clear();
    List<AppInfo> appInfos = queryAppWithNet();
    long rxTraffic = 0;
    long txTraffic = 0;
    long tTraffic = 0;
    for (AppInfo appInfo : appInfos) {
      AppTrafficInfo appTrafficInfo = new AppTrafficInfo();
      appTrafficInfo.setAppInfo(appInfo);
      try {
        /*rxTraffic = TrafficStats.getUidRxBytes(context.getPackageManager().getPackageInfo
                (appInfo.getPackageName(), 0).applicationInfo.uid);*/
        rxTraffic = TrafficUtils.getUidRxBytes(context.getPackageManager().getPackageInfo
                (appInfo.getPackageName(), 0).applicationInfo.uid);
        this.totalRxTraffic += rxTraffic;
        /*txTraffic = TrafficStats.getUidTxBytes(context.getPackageManager().getPackageInfo
                (appInfo.getPackageName(), 0).applicationInfo.uid);*/
        txTraffic = TrafficUtils.getUidTxBytes(context.getPackageManager().getPackageInfo
                (appInfo.getPackageName(), 0).applicationInfo.uid);
        this.totalTxTraffic += txTraffic;
        tTraffic = rxTraffic + txTraffic;
        this.totalTraffic += tTraffic;
      } catch (Exception e) {
        e.printStackTrace();
      }
      appTrafficInfo.setTotalTraffic(tTraffic);
      appTrafficInfo.setRxTraffic(rxTraffic);
      appTrafficInfo.setTxTraffic(txTraffic);
      appTrafficInfo.setUpdateTime(now);
      appTrafficInfos.add(appTrafficInfo);
    }
  }

  @Override
  public void queryRxTraffic(final GetTrafficCallback callback) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        queryTraffic();
        callback.getTraffic(TrafficUtils.sortAndFilterTraffic(appTrafficInfos, Commons
                .RECEIVE_TRAFFIC_TYPE), totalRxTraffic);
      }
    }).run();
  }

  @Override
  public void queryTxTraffic(final GetTrafficCallback callback) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        queryTraffic();
        callback.getTraffic(TrafficUtils.sortAndFilterTraffic(appTrafficInfos, Commons
                .SEND_TRAFFIC_TYPE), totalTxTraffic);
      }
    }).run();
  }

  @Override
  public void queryTotalTraffic(final GetTrafficCallback callback) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        queryTraffic();
        callback.getTraffic(TrafficUtils.sortAndFilterTraffic(appTrafficInfos, Commons
                .TOTAL_TRAFFIC_TYPE), totalTraffic);
      }
    }).run();
  }

  private void countMobileTraffic() {
    new Thread(new Runnable() {
      @Override
      public void run() {
        queryTraffic();
        for (AppTrafficInfo appTrafficInfo : appTrafficInfos) {
          long rxIncrease;
          long txIncrease;
          //如果是新开机导致得到的流量清零,直接更新
          if (getOldMobileRx(appTrafficInfo.getAppInfo().getPackageName()) > appTrafficInfo
                  .getRxTraffic()) {
            rxIncrease = appTrafficInfo.getRxTraffic();
            txIncrease = appTrafficInfo.getTxTraffic();
          } else {
            rxIncrease = appTrafficInfo.getRxTraffic() - getOldMobileRx(appTrafficInfo.getAppInfo
                    ().getPackageName());
            txIncrease = appTrafficInfo.getTxTraffic() - getOldMobileTx(appTrafficInfo.getAppInfo()
                    .getPackageName());
          }
          TrafficData trafficData = new TrafficData();
          trafficData.setUid(appTrafficInfo.getAppInfo().getUid());
          trafficData.setType(Commons.STATE_NETWORK_MOBILE);
          trafficData.setRxTraffic(rxIncrease);
          trafficData.setTxTraffic(txIncrease);
          trafficData.setTotalTraffic(rxIncrease + txIncrease);


          trafficDataDbManager.insertTrafficData(trafficData);
        }
      }
    }).run();
  }

  private long getOldMobileRx(String packageName) {
    return mobileTrafficSharedPreferences.getLong(Commons
            .BASE_TRAFFIC_SHAREDPREFERENCES_RX + packageName, 0);
  }

  private long getOldMobileTx(String packageName) {
    return mobileTrafficSharedPreferences.getLong(Commons
            .BASE_TRAFFIC_SHAREDPREFERENCES_TX + packageName, 0);
  }

  private long getOldMobileRxTotal() {
    return mobileTrafficSharedPreferences.getLong(Commons
            .TRAFFIC_SHAREDPREFERENCES_RX_TOTAL, 0);
  }

  private long getOldMobileTxTotal() {
    return mobileTrafficSharedPreferences.getLong(Commons
            .TRAFFIC_SHAREDPREFERENCES_TX_TOTAL, 0);
  }

  @Override
  public void countTraffic(int type) {
    switch (type) {
      case Commons.STATE_NETWORK_MOBILE:

        break;
      case Commons.STATE_NETWORK_WIFI:
        break;
      default:
        break;
    }
  }
}
