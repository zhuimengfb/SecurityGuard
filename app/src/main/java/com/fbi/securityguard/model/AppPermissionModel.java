package com.fbi.securityguard.model;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.fbi.securityguard.entity.AppInfo;
import com.fbi.securityguard.entity.AppPermissionInfo;
import com.fbi.securityguard.model.modelinterface.AppPermissionModelInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * author: bo on 2016/3/31 13:55.
 * email: bofu1993@163.com
 */
public class AppPermissionModel implements AppPermissionModelInterface {


  private Context context;

  public AppPermissionModel(Context context) {
    this.context = context;
  }

  @Override
  public void queryAppPermissionList(final AppPermissionListCallback
                                             appPermissionListCallback) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        List<AppPermissionInfo> appPermissionInfos = new ArrayList<>();
        List<PackageInfo> packageInfos = context.getPackageManager().getInstalledPackages
                (PackageManager.GET_PERMISSIONS);
        for (int i = 0; i < packageInfos.size(); i++) {
          if ((packageInfos.get(i).applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
            AppPermissionInfo appPermissionInfo = new AppPermissionInfo();
            AppInfo appInfo = new AppInfo();
            appInfo.setAppName(packageInfos.get(i).applicationInfo.loadLabel(context
                    .getPackageManager()).toString());
            appInfo.setAppIcon(packageInfos.get(i).applicationInfo.loadIcon(context
                    .getPackageManager()));
            appInfo.setAppInstallTime(packageInfos.get(i).firstInstallTime);
            appInfo.setAppLastUpdateTime(packageInfos.get(i).lastUpdateTime);
            appInfo.setPackageName(packageInfos.get(i).packageName);
            appInfo.setVersionName(packageInfos.get(i).versionName);
            appPermissionInfo.setPermissions(packageInfos.get(i).requestedPermissions);
            appPermissionInfo.setPermissionFlags(packageInfos.get(i).requestedPermissionsFlags);
            appPermissionInfo.setAppInfo(appInfo);
            appPermissionInfos.add(appPermissionInfo);
          }
        }
        appPermissionListCallback.onGetAppPermissionList(appPermissionInfos);
      }
    }).run();
  }
}
