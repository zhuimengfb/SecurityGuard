package com.fbi.securityguard.entity;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by fubo on 2016/4/4 0004.
 * email:bofu1993@163.com
 */
public class RunningAppInfo implements Serializable {

  private AppInfo appInfo;
  private long activeTime;
  private boolean isInWhiteList;
  private int memoryOccupy;

  public AppInfo getAppInfo() {
    return appInfo;
  }

  public void setAppInfo(AppInfo appInfo) {
    this.appInfo = appInfo;
  }

  public long getActiveTime() {
    return activeTime;
  }

  public void setActiveTime(long activeTime) {
    this.activeTime = activeTime;
  }

  public boolean isInWhiteList() {
    return isInWhiteList;
  }

  public void setIsInWhiteList(boolean isInWhiteList) {
    this.isInWhiteList = isInWhiteList;
  }

  public int getMemoryOccupy() {
    return memoryOccupy;
  }

  public void setMemoryOccupy(int memoryOccupy) {
    this.memoryOccupy = memoryOccupy;
  }

  public static class RunningAppComparator implements Comparator<RunningAppInfo> {

    @Override
    public int compare(RunningAppInfo lhs, RunningAppInfo rhs) {
      return rhs.getMemoryOccupy() - lhs.getMemoryOccupy();
    }
  }
}
