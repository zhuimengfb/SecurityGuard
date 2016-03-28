package com.fbi.securityguard.entity;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * author: bo on 2016/3/28 19:32.
 * email: bofu1993@163.com
 */
public class AppInfo implements Serializable {

  private String appName;
  private String packageName;
  private String versionName;
  private Drawable appIcon;
  private long appInstallTime;
  private long appLastUpdateTime;

  public String getAppName() {
    return appName;
  }

  public void setAppName(String appName) {
    this.appName = appName;
  }

  public String getPackageName() {
    return packageName;
  }

  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }

  public String getVersionName() {
    return versionName;
  }

  public void setVersionName(String versionName) {
    this.versionName = versionName;
  }

  public Drawable getAppIcon() {
    return appIcon;
  }

  public void setAppIcon(Drawable appIcon) {
    this.appIcon = appIcon;
  }

  public long getAppInstallTime() {
    return appInstallTime;
  }

  public void setAppInstallTime(long appInstallTime) {
    this.appInstallTime = appInstallTime;
  }

  public long getAppLastUpdateTime() {
    return appLastUpdateTime;
  }

  public void setAppLastUpdateTime(long appLastUpdateTime) {
    this.appLastUpdateTime = appLastUpdateTime;
  }
}
