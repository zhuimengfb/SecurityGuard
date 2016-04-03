package com.fbi.securityguard.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fubo on 2016/4/3 0003.
 * email:bofu1993@163.com
 */
public class ClassifierPermissionInfo implements Serializable {


  private String permissionName;
  private List<AppInfo> appInfos = new ArrayList<>();

  public String getPermissionName() {
    return permissionName;
  }

  public void setPermissionName(String permissionName) {
    this.permissionName = permissionName;
  }

  public List<AppInfo> getAppInfos() {
    return appInfos;
  }
}
