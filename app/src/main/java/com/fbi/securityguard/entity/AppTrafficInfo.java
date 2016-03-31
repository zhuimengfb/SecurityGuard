package com.fbi.securityguard.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

/**
 * author: bo on 2016/3/31 19:25.
 * email: bofu1993@163.com
 */
public class AppTrafficInfo implements Serializable {

  private AppInfo appInfo;
  private long rxTraffic;
  private long txTraffic;
  private long totalTraffic;
  private Date updateTime;

  public AppInfo getAppInfo() {
    return appInfo;
  }

  public void setAppInfo(AppInfo appInfo) {
    this.appInfo = appInfo;
  }

  public long getRxTraffic() {
    return rxTraffic;
  }

  public void setRxTraffic(long rxTraffic) {
    this.rxTraffic = rxTraffic;
  }

  public long getTxTraffic() {
    return txTraffic;
  }

  public void setTxTraffic(long txTraffic) {
    this.txTraffic = txTraffic;
  }

  public long getTotalTraffic() {
    return totalTraffic;
  }

  public void setTotalTraffic(long totalTraffic) {
    this.totalTraffic = totalTraffic;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public static class TotoalTrafficComparator implements Comparator<AppTrafficInfo> {

    @Override
    public int compare(AppTrafficInfo lhs, AppTrafficInfo rhs) {
      return (int) (rhs.getTotalTraffic() - lhs.getTotalTraffic());
    }
  }

  public static class RxTrafficComparator implements Comparator<AppTrafficInfo> {

    @Override
    public int compare(AppTrafficInfo lhs, AppTrafficInfo rhs) {
      return (int) (rhs.getRxTraffic() - lhs.getRxTraffic());
    }
  }

  public static class TxTrafficComparator implements Comparator<AppTrafficInfo> {

    @Override
    public int compare(AppTrafficInfo lhs, AppTrafficInfo rhs) {
      return (int) (rhs.getTxTraffic() - lhs.getTxTraffic());
    }
  }

}
