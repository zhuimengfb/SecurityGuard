package com.fbi.securityguard.presenter;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;

import com.fbi.securityguard.entity.RunningAppInfo;
import com.fbi.securityguard.model.RunningAppModel;
import com.fbi.securityguard.model.modelinterface.RunningAppModelInterface;
import com.fbi.securityguard.service.RunningAppService;
import com.fbi.securityguard.utils.Commons;
import com.fbi.securityguard.view.viewinterface.RunningAppActivityInterface;

import java.util.List;

/**
 * Created by fubo on 2016/4/4 0004.
 * email:bofu1993@163.com
 */
public class RunningAppControlPresenter {

  private Context context;
  private RunningAppActivityInterface runningAppActivityInterface;
  private RunningAppModelInterface runningAppModelInterface;
  private ActivityManager activityManager;
  private Handler handler;
  private SharedPreferences sharedPreferences;
  private static final String AUTO_PROTECTION_SETTING = Commons.PACKAGE_NAME + ""
      + ".auto_protection_preference";
  private static final String KEY_AUTO_PROTECTION = Commons.PACKAGE_NAME + ".key_auto_protection";

  public RunningAppControlPresenter(Context context, RunningAppActivityInterface
      runningAppActivityInterface) {
    this.context = context;
    this.runningAppActivityInterface = runningAppActivityInterface;
    runningAppModelInterface = new RunningAppModel(context);
    activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
    handler = new Handler(context.getMainLooper());
    sharedPreferences = context.getSharedPreferences(AUTO_PROTECTION_SETTING, Context.MODE_PRIVATE);
  }

  public void updateAvailMemory() {
    ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
    activityManager.getMemoryInfo(info);
    if (runningAppActivityInterface != null) {
      runningAppActivityInterface.updateAvailMemory(info.totalMem, info.availMem);
    }
  }

  public void loadingRunningApp() {
    runningAppActivityInterface.showLoading();
    runningAppModelInterface.loadingRunningApp(new RunningAppModelInterface
        .OnGetRunningAppCallback() {
      @Override
      public void onGetRunningApps(final List<RunningAppInfo> runningAppInfos) {
        if (runningAppActivityInterface != null) {
          handler.post(new Runnable() {
            @Override
            public void run() {
              runningAppActivityInterface.updateRunningAppList(runningAppInfos);
              runningAppActivityInterface.hideLoading();
            }
          });
        }
      }
    });
  }

  public void addToWhiteList(String packageName) {
    runningAppModelInterface.addToWhiteList(packageName);
  }

  public void removeWhiteList(String packageName) {
    runningAppModelInterface.removeWhiteList(packageName);
  }

  public void killService(String packageName) {
    runningAppModelInterface.killService(packageName);
    updateAvailMemory();
  }

  public void setAutoProtection(boolean autoProtection) {
    sharedPreferences.edit().putBoolean(KEY_AUTO_PROTECTION, autoProtection).apply();
  }

  public void initSwitch() {
    if (sharedPreferences.getBoolean(KEY_AUTO_PROTECTION, false)) {
      runningAppActivityInterface.openAutoProtect();
      Intent intent = new Intent();
      intent.setClass(context, RunningAppService.class);
      context.startService(intent);
    } else {
      runningAppActivityInterface.closeAutoProtect();
    }
  }
}
