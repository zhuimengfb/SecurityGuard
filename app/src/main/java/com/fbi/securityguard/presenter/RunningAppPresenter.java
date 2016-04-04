package com.fbi.securityguard.presenter;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Handler;

import com.fbi.securityguard.model.RunningAppModel;
import com.fbi.securityguard.view.viewinterface.RunningAppInterface;

import java.util.List;

/**
 * Created by fubo on 2016/4/4 0004.
 * email:bofu1993@163.com
 */
public class RunningAppPresenter {
  private List<ActivityManager.RunningServiceInfo> runningProcesses;
  private ActivityManager activityManager;
  private Context context;
  private RunningAppInterface runningAppInterface;
  private RunningAppModel runningAppModel;
  private Handler handler;
  private static final int MEMORY_THREADHOLD = 20;

  public RunningAppPresenter(Context context) {
    this.context = context;
    activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    runningAppModel = new RunningAppModel(context);
    handler = new Handler(context.getMainLooper());
  }

  public RunningAppPresenter(Context context, RunningAppInterface runningAppInterface) {
    this.context = context;
    this.runningAppInterface = runningAppInterface;
    activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    runningAppModel = new RunningAppModel(context);
    handler = new Handler(context.getMainLooper());
  }


  public void getAvailMemory() {
    ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
    activityManager.getMemoryInfo(info);
    if (runningAppInterface != null) {
      runningAppInterface.updateAvailMemory(100 - info.availMem * 100 / info.totalMem);
    }
  }

  public void killOthersInService() {
    handler.post(new Runnable() {
      @Override
      public void run() {
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(info);
        if (100 - info.availMem * 100 / info.totalMem < MEMORY_THREADHOLD) {
          runningProcesses = activityManager.getRunningServices(100);
          for (ActivityManager.RunningServiceInfo runningProcess : runningProcesses) {
            try {
              String packName = runningProcess.service.getPackageName();
              ApplicationInfo applicationInfo = context.getPackageManager().getPackageInfo
                  (packName, 0).applicationInfo;
              if (! runningAppModel.isInWhiteList(packName) && filterApp(applicationInfo)) {
                context.stopService(new Intent(context, runningProcess.service.getClass()));
                forceStopPackage(packName, context);
              }
            } catch (Exception exception) {
              exception.printStackTrace();
            }
          }
        }
      }
    });
  }



  public void killOthers() {
    handler.post(new Runnable() {
      @Override
      public void run() {
        runningProcesses = activityManager.getRunningServices(100);
        for (ActivityManager.RunningServiceInfo runningProcess : runningProcesses) {
          try {
            String packName = runningProcess.service.getPackageName();
            ApplicationInfo applicationInfo = context.getPackageManager().getPackageInfo
                (packName, 0).applicationInfo;
            if (! runningAppModel.isInWhiteList(packName) && filterApp(applicationInfo)) {
              context.stopService(new Intent(context, runningProcess.service.getClass()));
              forceStopPackage(packName, context);
            }
          } catch (Exception exception) {
            exception.printStackTrace();
          }
        }
        getAvailMemory();
      }
    });
  }

  private void forceStopPackage(String pkgName,Context context) throws Exception {
    ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
    am.killBackgroundProcesses(pkgName);
    /*Method method = Class.forName("android.app.ActivityManager").getMethod("forceStopPackage", String.class);
    method.setAccessible(true);
    method.invoke(am, pkgName);*/
  }

  /**判断某个应用程序是 不是三方的应用程序.
   *
   * @param info:应用信息
   * @return boolean
   */
  public boolean filterApp(ApplicationInfo info) {
    if ((info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
      return true;
    } else if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
      return true;
    }
    return false;
  }

  public void unbind() {
    this.context = null;
    this.runningAppInterface = null;
  }
}
