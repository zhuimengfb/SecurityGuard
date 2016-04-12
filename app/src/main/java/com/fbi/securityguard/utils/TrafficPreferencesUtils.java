package com.fbi.securityguard.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Date;

/**
 * Created by fubo on 2016/4/2 0002.
 * email:bofu1993@163.com
 */
public class TrafficPreferencesUtils {

  private static final String BASE_TRAFFIC_SHAREDPREFERENCES_RX = Commons.PACKAGE_NAME + ""
      + ".traffic_sharedPreferences_rx.";
  private static final String BASE_TRAFFIC_SHAREDPREFERENCES_TX = Commons.PACKAGE_NAME + ""
      + ".traffic_sharedPreferences_tx.";
  private static final String TRAFFIC_SHAREDPREFERENCES_RX_TOTAL = Commons.PACKAGE_NAME + ""
      + ".traffic_sharedPreferences_rx_total.";
  private static final String TRAFFIC_SHAREDPREFERENCES_TX_TOTAL = Commons.PACKAGE_NAME + ""
      + ".traffic_sharedPreferences_tx_total.";
  private static final String TRAFFIC_SHAREDPREFERENCES_TOTAL = Commons.PACKAGE_NAME + ""
      + ".traffic_sharedPreferences_total.";
  private static final String TRAFFIC_SHAREDPREFERENCES_UPDATE_TIME = Commons.PACKAGE_NAME + ""
      + ".traffic_sharedPreferences_update_time";
  private static final String TRAFFIC_SHAREDPREFERENCES = Commons.PACKAGE_NAME + ""
      + ".sharedPreferences_traffic";
  private static final String IS_FIRST = Commons.PACKAGE_NAME + ".is_first";

  public static final int MOBILE_TRAFFIC_SHAREDPREFERENCES_TYPE = 0;
  public static final int WIFI_TRAFFIC_SHAREDPREFERENCES_TYPE = 1;

  private volatile SharedPreferences sharedPreferences;


  /**
   * 存储流量信息工具构造函数.
   *
   * @param context:上下文
   *
   */
  public TrafficPreferencesUtils(Context context) {
    sharedPreferences = context.getSharedPreferences(TRAFFIC_SHAREDPREFERENCES, Context
        .MODE_PRIVATE);
  }

  public long getOldRx(String packageName) {
    return sharedPreferences.getLong(BASE_TRAFFIC_SHAREDPREFERENCES_RX + packageName, 0);
  }

  public long getOldTx(String packageName) {
    return sharedPreferences.getLong(BASE_TRAFFIC_SHAREDPREFERENCES_TX + packageName, 0);
  }

  public long getOldUpdateTime() {
    return sharedPreferences.getLong(TRAFFIC_SHAREDPREFERENCES_UPDATE_TIME, new Date().getTime());
  }

  public long getOldRxTotal() {
    return sharedPreferences.getLong(TRAFFIC_SHAREDPREFERENCES_RX_TOTAL, 0);
  }

  public long getOldTxTotal() {
    return sharedPreferences.getLong(TRAFFIC_SHAREDPREFERENCES_TX_TOTAL, 0);
  }

  public long getOldTotal() {
    return sharedPreferences.getLong(TRAFFIC_SHAREDPREFERENCES_TOTAL, 0);
  }

  public void setNewRx(String packageName, long newRx) {
    sharedPreferences.edit().putLong(BASE_TRAFFIC_SHAREDPREFERENCES_RX + packageName, newRx)
        .apply();
  }

  public void setNewTx(String packageName, long newTx) {
    sharedPreferences.edit().putLong(BASE_TRAFFIC_SHAREDPREFERENCES_TX + packageName, newTx)
        .apply();
  }

  public void setNewRxTotal(long newRxTotal) {
    sharedPreferences.edit().putLong(TRAFFIC_SHAREDPREFERENCES_RX_TOTAL, newRxTotal).apply();
  }

  public void setNewTxTotal(long newTxTotal) {
    sharedPreferences.edit().putLong(TRAFFIC_SHAREDPREFERENCES_TX_TOTAL, newTxTotal).apply();
  }

  public void setUpdateTime(long newUpdateTime) {
    sharedPreferences.edit().putLong(TRAFFIC_SHAREDPREFERENCES_UPDATE_TIME, newUpdateTime).apply();
  }

  public void setTotalTraffic(long totalTraffic) {
    sharedPreferences.edit().putLong(TRAFFIC_SHAREDPREFERENCES_TOTAL, totalTraffic).apply();
  }

  public boolean isFirst() {
    return sharedPreferences.getBoolean(IS_FIRST, true);
  }

  public void setFirstFalse() {
    sharedPreferences.edit().putBoolean(IS_FIRST, false).apply();
  }
}
