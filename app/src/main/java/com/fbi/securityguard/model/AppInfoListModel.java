package com.fbi.securityguard.model;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;

import com.fbi.securityguard.entity.AppInfo;
import com.fbi.securityguard.model.modelinterface.AppInfoListModelInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * author: bo on 2016/3/28 21:45.
 * email: bofu1993@163.com
 */
public class AppInfoListModel implements AppInfoListModelInterface {

  private Context context;

  public AppInfoListModel(Context context) {
    this.context = context;
  }

  @Override
  public void queryAppListWithoutSystemApp(final AppListCallback callback) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        List<AppInfo> appInfos = new ArrayList<>();
        List<PackageInfo> packageInfos = context.getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packageInfos.size(); i++) {
          if ((packageInfos.get(i).applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
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
        callback.onGetAppList(appInfos);
      }
    }).run();
  }
}
