package com.fbi.securityguard.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * author: bo on 2016/3/31 22:16.
 * email: bofu1993@163.com
 */
public class TrafficData implements Serializable {

  public static final String UID="uid";
  public static final String TYPE="type";
  public static final String RX_TRAFFIC="rx_Traffic";
  public static final String TX_TRAFFIC="tx_traffic";
  public static final String TOTAL_TRAFFIC="total_traffic";
  public static final String START_TIME="start_time";
  public static final String END_TIME="end_time";

  private int uid;
  private int type;//联网类型，0位wifi，1位移动流量
  private long rxTraffic;
  private long txTraffic;
  private long totalTraffic;
  private Date startTime;
  private Date endTime;

  public int getUid() {
    return uid;
  }

  public void setUid(int uid) {
    this.uid = uid;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
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

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }
}
