package com.fbi.securityguard.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.fbi.securityguard.utils.Commons;

/**
 * Created by fubo on 2016/4/4 0004.
 * email:bofu1993@163.com
 */
public class RunningAppModel {

  private static final String RUNNING_WHITE_LIST = Commons.PACKAGE_NAME + ".running_white_list";
  private SharedPreferences runningWhiteList;

  public RunningAppModel(Context context) {
    runningWhiteList = context.getSharedPreferences(RUNNING_WHITE_LIST, Context.MODE_PRIVATE);
    if (! isInWhiteList(Commons.PACKAGE_NAME)) {
      addToWhiteList(Commons.PACKAGE_NAME);
    }
  }

  public boolean isInWhiteList(String packageName) {
    return runningWhiteList.getBoolean(packageName, false);
  }

  public void addToWhiteList(String packageName) {
    runningWhiteList.edit().putBoolean(packageName, true).apply();
  }
}
