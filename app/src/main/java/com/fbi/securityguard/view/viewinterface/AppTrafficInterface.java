package com.fbi.securityguard.view.viewinterface;

import com.fbi.securityguard.entity.AppTrafficInfo;

import java.util.List;

/**
 * author: bo on 2016/3/31 19:56.
 * email: bofu1993@163.com
 */
public interface AppTrafficInterface {

  void updateList(List<AppTrafficInfo> appTrafficInfoList, long totalTraffic);
}
