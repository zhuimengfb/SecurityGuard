package com.fbi.securityguard.model;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.fbi.securityguard.db.TrafficDataDbManager;
import com.fbi.securityguard.entity.AppInfo;
import com.fbi.securityguard.entity.AppTrafficInfo;
import com.fbi.securityguard.entity.TrafficData;
import com.fbi.securityguard.model.modelinterface.AppTrafficModelInterface;
import com.fbi.securityguard.utils.Commons;
import com.fbi.securityguard.utils.TrafficPreferencesUtils;
import com.fbi.securityguard.utils.TrafficUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * author: bo on 2016/3/31 20:08.
 * email: bofu1993@163.com
 */
public class AppTrafficModel implements AppTrafficModelInterface {

  List<AppTrafficInfo> appTrafficInfos = new ArrayList<>();
  private Context context;
  private long totalTraffic = 0;
  private long totalRxTraffic = 0;
  private long totalTxTraffic = 0;
  private TrafficPreferencesUtils mobileTrafficPreferencesUtils;
  private TrafficPreferencesUtils wifiTrafficPreferencesUtils;
  private TrafficDataDbManager trafficDataDbManager;
  private boolean isCountingTraffic = false;


  public AppTrafficModel(Context context) {
    this.context = context;
    mobileTrafficPreferencesUtils = new TrafficPreferencesUtils(context,
        TrafficPreferencesUtils
            .MOBILE_TRAFFIC_SHAREDPREFERENCES_TYPE);
    wifiTrafficPreferencesUtils = new TrafficPreferencesUtils(context,
        TrafficPreferencesUtils
            .WIFI_TRAFFIC_SHAREDPREFERENCES_TYPE);
    trafficDataDbManager = TrafficDataDbManager.getInstance(context);
  }

  public List<AppInfo> queryAppWithNet() {
    List<AppInfo> appInfos = new ArrayList<>();
    List<PackageInfo> packageInfos = context.getPackageManager().getInstalledPackages(
        PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_PERMISSIONS);
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
    long toTraffic = 0;
    for (AppInfo appInfo : appInfos) {
      AppTrafficInfo appTrafficInfo = new AppTrafficInfo();
      appTrafficInfo.setAppInfo(appInfo);
      try {
        /*rxTraffic = TrafficStats.getUidRxBytes(context.getPackageManager()
        .getPackageInfo
                (appInfo.getPackageName(), 0).applicationInfo.uid);*/
        rxTraffic = TrafficUtils.getUidRxBytes(context.getPackageManager().getPackageInfo(
            appInfo.getPackageName(), 0).applicationInfo.uid);
        this.totalRxTraffic += rxTraffic;
        /*txTraffic = TrafficStats.getUidTxBytes(context.getPackageManager()
        .getPackageInfo
                (appInfo.getPackageName(), 0).applicationInfo.uid);*/
        txTraffic = TrafficUtils.getUidTxBytes(context.getPackageManager().getPackageInfo(
            appInfo.getPackageName(), 0).applicationInfo.uid);
        this.totalTxTraffic += txTraffic;
        toTraffic = rxTraffic + txTraffic;
        this.totalTraffic += toTraffic;
      } catch (Exception exception) {
        exception.printStackTrace();
      }
      appTrafficInfo.setTotalTraffic(toTraffic);
      appTrafficInfo.setRxTraffic(rxTraffic);
      appTrafficInfo.setTxTraffic(txTraffic);
      appTrafficInfo.setUpdateTime(now);
      appTrafficInfos.add(appTrafficInfo);
    }
  }

  @Override
  public void queryRxTraffic(final GetTrafficCallback callback) {
    new Thread(
        new Runnable() {
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
    new Thread(
        new Runnable() {
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
    new Thread(
        new Runnable() {
          @Override
          public void run() {
            queryTraffic();
            callback.getTraffic(TrafficUtils.sortAndFilterTraffic(appTrafficInfos, Commons
                .TOTAL_TRAFFIC_TYPE), totalTraffic);
          }
        }).run();
  }

  private void countMobileTraffic() {
    countTraffic(mobileTrafficPreferencesUtils, Commons.STATE_NETWORK_MOBILE);
  }

  private void countWifiTraffic() {
    countTraffic(wifiTrafficPreferencesUtils, Commons.STATE_NETWORK_WIFI);
  }

  private void countTraffic(final TrafficPreferencesUtils trafficPreferencesUtils, final int type) {
    if (! isCountingTraffic) {
      isCountingTraffic = true;
      queryTraffic();
      Date updateTime = new Date();
      for (AppTrafficInfo appTrafficInfo : appTrafficInfos) {
        long rxIncrease;
        long txIncrease;
        //如果是新开机导致得到的流量清零,直接更新
        if (trafficPreferencesUtils.getOldRx(appTrafficInfo.getAppInfo().getPackageName())
            > appTrafficInfo.getRxTraffic()) {
          rxIncrease = appTrafficInfo.getRxTraffic();
          txIncrease = appTrafficInfo.getTxTraffic();
        } else {
          rxIncrease = appTrafficInfo.getRxTraffic() - trafficPreferencesUtils.getOldRx(
              appTrafficInfo.getAppInfo().getPackageName());
          txIncrease = appTrafficInfo.getTxTraffic() - trafficPreferencesUtils.getOldTx(
              appTrafficInfo.getAppInfo().getPackageName());
        }
        if (! trafficPreferencesUtils.isFirst()) {
          TrafficData trafficData = new TrafficData();
          trafficData.setUid(appTrafficInfo.getAppInfo().getUid());
          trafficData.setType(type);
          trafficData.setRxTraffic(rxIncrease);
          trafficData.setTxTraffic(txIncrease);
          trafficData.setTotalTraffic(rxIncrease + txIncrease);
          if (trafficPreferencesUtils.getOldUpdateTime() > updateTime.getTime()) {
            trafficData.setStartTime(updateTime);
          } else {
            trafficData.setStartTime(new Date(trafficPreferencesUtils.getOldUpdateTime()));
          }
          trafficData.setEndTime(updateTime);
          trafficDataDbManager.insertTrafficData(trafficData);
        }
        trafficPreferencesUtils.setNewRx(appTrafficInfo.getAppInfo().getPackageName(),
            appTrafficInfo.getRxTraffic());
        trafficPreferencesUtils.setNewTx(appTrafficInfo.getAppInfo().getPackageName(),
            appTrafficInfo.getTxTraffic());
      }
      trafficPreferencesUtils.setNewRxTotal(totalRxTraffic);
      trafficPreferencesUtils.setNewTxTotal(totalTxTraffic);
      trafficPreferencesUtils.setTotalTraffic(totalTraffic);
      trafficPreferencesUtils.setUpdateTime(updateTime.getTime());
      if (trafficPreferencesUtils.isFirst()) {
        trafficPreferencesUtils.setFirstFalse();
      }
      isCountingTraffic = false;
    }
  }

  @Override
  public void countTraffic(int type) {
    switch (type) {
      case Commons.STATE_NETWORK_MOBILE:
        countMobileTraffic();
        break;
      case Commons.STATE_NETWORK_WIFI:
        countWifiTraffic();
        break;
      default:
        break;
    }
  }

  private List<AppTrafficInfo> queryDetailTraffic(int type, Date startTime, Date endTime) {
    countTraffic(type);
    List<AppTrafficInfo> appTrafficInfoList = new ArrayList<>();
    for (AppTrafficInfo appTrafficInfo : appTrafficInfos) {
      List<TrafficData> trafficDatas = trafficDataDbManager.selectTrafficDataByUidAndTime(
          appTrafficInfo.getAppInfo().getUid(), startTime, endTime, type);
      long rxTraffic = 0;
      long txTraffic = 0;
      long toTraffic = 0;
      for (TrafficData trafficData : trafficDatas) {
        rxTraffic += trafficData.getRxTraffic();
        txTraffic += trafficData.getTxTraffic();
        toTraffic += trafficData.getTotalTraffic();
      }
      appTrafficInfo.setRxTraffic(rxTraffic);
      appTrafficInfo.setTxTraffic(txTraffic);
      appTrafficInfo.setTotalTraffic(toTraffic);
      appTrafficInfoList.add(appTrafficInfo);
    }
    return appTrafficInfoList;
  }

  @Override
  public void queryMobileTotalTraffic(final Date startTime, final Date endTime, final
  GetTrafficCallback callback) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        List<AppTrafficInfo> appTrafficInfos = TrafficUtils.sortAndFilterTraffic(queryDetailTraffic(
            Commons.STATE_NETWORK_MOBILE, startTime, endTime), Commons.TOTAL_TRAFFIC_TYPE);
        long total = 0;
        for (int i = 0; i < appTrafficInfos.size(); i++) {
          total += appTrafficInfos.get(i).getTotalTraffic();
        }
        callback.getTraffic(appTrafficInfos, total);
      }
    }).run();
  }

  @Override
  public void queryWifiTotalTraffic(final Date startTime, final Date endTime, final
  GetTrafficCallback callback) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        List<AppTrafficInfo> appTrafficInfos = TrafficUtils.sortAndFilterTraffic(queryDetailTraffic(
            Commons.STATE_NETWORK_WIFI, startTime, endTime), Commons.TOTAL_TRAFFIC_TYPE);
        long total = 0;
        for (int i = 0; i < appTrafficInfos.size(); i++) {
          total += appTrafficInfos.get(i).getTotalTraffic();
        }
        callback.getTraffic(appTrafficInfos, total);
      }
    }).start();
  }
}
