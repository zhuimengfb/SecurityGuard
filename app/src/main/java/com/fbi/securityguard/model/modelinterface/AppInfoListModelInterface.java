package com.fbi.securityguard.model.modelinterface;

import com.fbi.securityguard.entity.AppInfo;

import java.util.List;

/**
 * author: bo on 2016/3/28 21:46.
 * email: bofu1993@163.com
 */
public interface AppInfoListModelInterface {

  interface AppListCallback{
    void onGetAppList(List<AppInfo> appInfoList);
  }

  void queryAppListWithoutSystemApp(AppListCallback callback);
}
