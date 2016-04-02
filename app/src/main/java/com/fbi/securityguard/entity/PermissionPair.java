package com.fbi.securityguard.entity;

import java.io.Serializable;

/**
 * author: bo on 2016/3/31 15:16.
 * email: bofu1993@163.com
 */
public class PermissionPair implements Serializable{

  private String name;
  private int flag;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getFlag() {
    return flag;
  }

  public void setFlag(int flag) {
    this.flag = flag;
  }
}
