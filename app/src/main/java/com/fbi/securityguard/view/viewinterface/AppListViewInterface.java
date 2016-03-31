package com.fbi.securityguard.view.viewinterface;

import com.fbi.securityguard.entity.AppInfo;

import java.util.List;

/**
 * author: bo on 2016/3/28 19:39.
 * email: bofu1993@163.com
 */
public interface AppListViewInterface {

  void showLoadingProgress();

  void hideLoadingProgress();

  void updateAppInfoList(List<AppInfo> appInfoList);

  void showAppDetailInfo(AppInfo appInfo);

  void uninstallApp(AppInfo appInfo);
}
