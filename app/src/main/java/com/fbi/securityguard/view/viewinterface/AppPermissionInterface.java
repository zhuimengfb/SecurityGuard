package com.fbi.securityguard.view.viewinterface;

import com.fbi.securityguard.entity.AppPermissionInfo;

import java.util.List;

/**
 * author: bo on 2016/3/31 13:52.
 * email: bofu1993@163.com
 */
public interface AppPermissionInterface {

  void showLoadingProgress();

  void hideLoadingProgress();

  void updateAppPermissionInfoList(List<AppPermissionInfo> appPermissionInfos);
}
