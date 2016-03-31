package com.fbi.securityguard.model;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.fbi.securityguard.entity.AppInfo;
import com.fbi.securityguard.entity.AppTrafficInfo;
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

  List<AppTrafficInfo> appTrafficInfos = new ArrayList<>();
  private Context context;
  private long totalTraffic = 0;
  private long totalRxTraffic = 0;
  private long totalTxTraffic = 0;

  public AppTrafficModel(Context context) {
    this.context = context;
  }

  public List<AppInfo> queryAppWithNet() {
    List<AppInfo> appInfos = new ArrayList<>();
    List<PackageInfo> packageInfos = context.getPackageManager().getInstalledPackages
            (PackageManager.GET_UNINSTALLED_PACKAGES | PackageManager.GET_PERMISSIONS);
    for (int i = 0; i < packageInfos.size(); i++) {
      String[] permissions = packageInfos.get(i).requestedPermissions;
      if (permissions != null && permissions.length > 0) {
        for (int j = 0; j < permissions.length; j++) {
          if ("android.permission.INTERNET".equals(permissions[j])) {
            AppInfo appInfo = new AppInfo();
            appInfo.setAppName(packageInfos.get(i).applicationInfo.loadLabel(context
                    .getPackageManager()).toString());
            appInfo.setAppIcon(packageInfos.get(i).applicationInfo.loadIcon(context
                    .getPackageManager()));
            appInfo.setAppInstallTime(packageInfos.get(i).firstInstallTime);
            appInfo.setAppLastUpdateTime(packageInfos.get(i).lastUpdateTime);
            appInfo.setPackageName(packageInfos.get(i).packageName);
            appInfo.setVersionName(packageInfos.get(i).versionName);
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
}
