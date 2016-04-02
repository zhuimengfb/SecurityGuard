package com.fbi.securityguard.entity;

import java.io.Serializable;

/**
 * author: bo on 2016/3/31 13:33.
 * email: bofu1993@163.com
 */
public class AppPermissionInfo implements Serializable {

  private AppInfo appInfo;
  private String[] permissions;
  private int[] permissionFlags;

  public AppInfo getAppInfo() {
    return appInfo;
  }

  public void setAppInfo(AppInfo appInfo) {
    this.appInfo = appInfo;
  }

  public String[] getPermissions() {
    return permissions;
  }

  public void setPermissions(String[] permissions) {
    this.permissions = permissions;
  }

  public int[] getPermissionFlags() {
    return permissionFlags;
  }

  public void setPermissionFlags(int[] permissionFlags) {
    this.permissionFlags = permissionFlags;
  }
}
