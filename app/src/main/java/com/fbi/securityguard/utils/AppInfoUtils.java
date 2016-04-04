package com.fbi.securityguard.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;

import com.fbi.securityguard.entity.AppInfo;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by fubo on 2016/4/4 0004.
 * email:bofu1993@163.com
 */
public class AppInfoUtils {

  public static AppInfo getAppInfoFromPackageInfo(Context context, PackageInfo packageInfo) {
    AppInfo appInfo = new AppInfo();
    appInfo.setAppName(packageInfo.applicationInfo.loadLabel(context
        .getPackageManager()).toString());
    appInfo.setAppIcon(packageInfo.applicationInfo.loadIcon(context
        .getPackageManager()));
    appInfo.setAppInstallTime(packageInfo.firstInstallTime);
    appInfo.setAppLastUpdateTime(packageInfo.lastUpdateTime);
    appInfo.setPackageName(packageInfo.packageName);
    appInfo.setVersionName(packageInfo.versionName);
    return appInfo;
  }
  public static boolean isUserApp(ApplicationInfo info) {
    if (!StringUtils.equals(info.packageName,Commons.PACKAGE_NAME)) {
      if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
        return true;
      } else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
        return true;
      }
    }
    return false;
  }
}
