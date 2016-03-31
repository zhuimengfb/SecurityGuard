package com.fbi.securityguard.utils;

import android.content.pm.PermissionInfo;

import com.fbi.securityguard.entity.PermissionPair;

import java.util.ArrayList;
import java.util.List;

/**
 * author: bo on 2016/3/31 14:56.
 * email: bofu1993@163.com
 */
public class PermissionUtils {

  public static List<PermissionPair> getPermissionPairFromPermissionInfo(PermissionInfo[]
                                                                                 permissionInfos) {
    List<PermissionPair> permissionPairs = new ArrayList<>();
    for (PermissionInfo permissionInfo : permissionInfos) {
      PermissionPair permissionPair = new PermissionPair();
      permissionPair.setName(permissionInfo.name);
      permissionPair.setFlag(permissionInfo.flags);
    }
    return permissionPairs;
  }

}
