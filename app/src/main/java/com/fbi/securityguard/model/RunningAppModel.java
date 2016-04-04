package com.fbi.securityguard.model;

import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Debug;

import com.fbi.securityguard.entity.RunningAppInfo;
import com.fbi.securityguard.model.modelinterface.RunningAppModelInterface;
import com.fbi.securityguard.utils.AppInfoUtils;
import com.fbi.securityguard.utils.Commons;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by fubo on 2016/4/4 0004.
 * email:bofu1993@163.com
 */
public class RunningAppModel implements RunningAppModelInterface {

  private static final String RUNNING_WHITE_LIST = Commons.PACKAGE_NAME + ".running_white_list";
  private SharedPreferences runningWhiteList;
  private Context context;

  public RunningAppModel(Context context) {
    this.context = context;
    runningWhiteList = context.getSharedPreferences(RUNNING_WHITE_LIST, Context.MODE_PRIVATE);
    if (! isInWhiteList(Commons.PACKAGE_NAME)) {
      addToWhiteList(Commons.PACKAGE_NAME);
    }
  }

  public boolean isInWhiteList(String packageName) {
    return runningWhiteList.getBoolean(packageName, false);
  }

  @Override
  public void addToWhiteList(String packageName) {
    runningWhiteList.edit().putBoolean(packageName, true).apply();
  }

  @Override
  public void removeWhiteList(String packageName) {
    runningWhiteList.edit().putBoolean(packageName, false).apply();
  }

  @Override
  public void killService(String packageName) {
    try {
      if (! isInWhiteList(packageName)) {
        forceStopPackage(packageName, context);
      }
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  private void forceStopPackage(String pkgName,Context context) throws Exception {
    ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
    am.killBackgroundProcesses(pkgName);
  }

  @Override
  public void loadingRunningApp(final OnGetRunningAppCallback onGetRunningAppCallback) {
    new Thread(new Runnable() {
      @Override
      public void run() {
        List<RunningAppInfo> runningAppInfos = new ArrayList<>();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context
            .ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServiceInfos = activityManager
            .getRunningServices(100);
        for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServiceInfos) {
          try {
            String packName = runningServiceInfo.service.getPackageName();
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packName, 0);
            ApplicationInfo applicationInfo = context.getPackageManager().getPackageInfo(
                packName, 0).applicationInfo;
            //滤出系统app
            if (AppInfoUtils.isUserApp(applicationInfo)) {
              if (indexOfPackage(runningAppInfos, packName) == - 1) {
                RunningAppInfo runningAppInfo = new RunningAppInfo();
                runningAppInfo.setAppInfo(AppInfoUtils.getAppInfoFromPackageInfo(context, packageInfo));
                runningAppInfo.setIsInWhiteList(isInWhiteList(packName));
                Debug.MemoryInfo[] memoryInfos = activityManager.getProcessMemoryInfo(new int[]{
                    runningServiceInfo.pid});
                runningAppInfo.setMemoryOccupy(memoryInfos[0].dalvikPrivateDirty);
                if (memoryInfos[0].dalvikPrivateDirty>1024) {
                  runningAppInfos.add(runningAppInfo);
                }
              }
            }
          } catch (Exception exception) {
            exception.printStackTrace();
          }
        }
        Collections.sort(runningAppInfos, new RunningAppInfo.RunningAppComparator());
        onGetRunningAppCallback.onGetRunningApps(runningAppInfos);
      }
    }).start();
  }

  private int indexOfPackage(List<RunningAppInfo> runningAppInfos, String packageName) {
    for (int i = 0; i < runningAppInfos.size(); i++) {
      if (StringUtils.equals(runningAppInfos.get(i).getAppInfo().getPackageName(), packageName)) {
        return i;
      }
    }
    return - 1;
  }
}
