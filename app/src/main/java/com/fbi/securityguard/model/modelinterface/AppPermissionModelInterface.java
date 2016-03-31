package com.fbi.securityguard.model.modelinterface;

import com.fbi.securityguard.entity.AppPermissionInfo;

import java.util.List;

/**
 * author: bo on 2016/3/31 13:55.
 * email: bofu1993@163.com
 */
public interface AppPermissionModelInterface {

  void queryAppPermissionList(final AppPermissionListCallback
                                      appPermissionListCallback);

  interface AppPermissionListCallback {
    void onGetAppPermissionList(List<AppPermissionInfo> appPermissionInfoList);
  }

}
