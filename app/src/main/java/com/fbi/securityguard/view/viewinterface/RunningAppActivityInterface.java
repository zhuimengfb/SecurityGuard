package com.fbi.securityguard.view.viewinterface;

import com.fbi.securityguard.entity.RunningAppInfo;

import java.util.List;

/**
 * Created by fubo on 2016/4/4 0004.
 * email:bofu1993@163.com
 */
public interface RunningAppActivityInterface {

  void updateAvailMemory(long total, long used);

  void updateRunningAppList(List<RunningAppInfo> runningAppInfos);

  void openAutoProtect();

  void closeAutoProtect();

  void showLoading();

  void hideLoading();
}
