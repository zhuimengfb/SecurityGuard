package com.fbi.securityguard.model.modelinterface;

import com.fbi.securityguard.entity.RunningAppInfo;

import java.util.List;

/**
 * Created by fubo on 2016/4/4 0004.
 * email:bofu1993@163.com
 */
public interface RunningAppModelInterface {

  interface OnGetRunningAppCallback {
    void onGetRunningApps(List<RunningAppInfo> runningAppInfos);
  }

  void loadingRunningApp(OnGetRunningAppCallback onGetRunningAppCallback);

  void addToWhiteList(String packageName);

  void removeWhiteList(String packageName);

  void killService(String packageName);
}
