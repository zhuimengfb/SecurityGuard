package com.fbi.securityguard.model.modelinterface;

import com.fbi.securityguard.entity.AppTrafficInfo;

import java.util.Date;
import java.util.List;

/**
 * author: bo on 2016/3/31 20:08.
 * email: bofu1993@163.com
 */
public interface AppTrafficModelInterface {

  void queryRxTraffic(GetTrafficCallback callback);

  void queryTxTraffic(GetTrafficCallback callback);

  void queryTotalTraffic(GetTrafficCallback callback);

  void countTraffic(int type);

  void queryMobileTotalTraffic(final Date startTime, final Date endTime, GetTrafficCallback callback);

  void queryWifiTotalTraffic(final Date startTime, final Date endTime, GetTrafficCallback callback);

  interface GetTrafficCallback {
    void getTraffic(List<AppTrafficInfo> appTrafficInfos, long total);
  }
}
