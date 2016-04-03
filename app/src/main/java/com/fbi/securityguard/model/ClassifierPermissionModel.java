package com.fbi.securityguard.model;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.fbi.securityguard.entity.AppInfo;
import com.fbi.securityguard.entity.ClassifierPermissionInfo;
import com.fbi.securityguard.model.modelinterface.ClassifierPermissionModelInterface;
import com.fbi.securityguard.utils.PermissionPairUtils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fubo on 2016/4/3 0003.
 * email:bofu1993@163.com
 */
public class ClassifierPermissionModel implements ClassifierPermissionModelInterface {

  private Context context;
  private List<ClassifierPermissionInfo> classifierPermissionInfos = new ArrayList<>();
  public ClassifierPermissionModel(Context context) {
    this.context = context;
  }

  @Override
  public void queryPermissionClassifierResult(final OnGetResultCallback callback) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        PermissionPairUtils permissionPairUtils = PermissionPairUtils.getInstance();
        List<PackageInfo> packageInfos = context.getPackageManager().getInstalledPackages(
            PackageManager.GET_PERMISSIONS);
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
            String[] permissions = packageInfos.get(i).requestedPermissions;
            for (String permission : permissions) {
              if (permissionPairUtils.parsePermission(permission) != null) {
                insertAppInfo(permissionPairUtils.parsePermission(permission), appInfo);
              }
            }
          }
        }
        callback.getResult(classifierPermissionInfos);
      }
    }).start();
  }

  private void insertAppInfo(String permission, AppInfo appInfo) {
    int index = indexOfPermission(permission);
    if (index == -1) {
      ClassifierPermissionInfo classifierPermissionInfo = new ClassifierPermissionInfo();
      classifierPermissionInfo.setPermissionName(permission);
      classifierPermissionInfo.getAppInfos().add(appInfo);
      classifierPermissionInfos.add(classifierPermissionInfo);
    } else {
      classifierPermissionInfos.get(index).getAppInfos().add(appInfo);
    }

  }

  private int indexOfPermission(String permission) {
    for (int i = 0; i < classifierPermissionInfos.size(); i++) {
      if (StringUtils.equals(classifierPermissionInfos.get(i).getPermissionName(), permission)) {
        return i;
      }
    }
    return -1;
  }

}
