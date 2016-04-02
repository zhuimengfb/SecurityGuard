package com.fbi.securityguard.utils;

/**
 * author: bo on 2016/3/28 19:36.
 * email: bofu1993@163.com
 */
public class Commons {

  public static final String PACKAGE_NAME = "com.fbi.securityguard";
  public static final String KEY_APP_PERMISSION_INFO = PACKAGE_NAME + ".app_permission_info";
  public static final String KEY_APP_PERMISSION_INFO_BUNDLE = PACKAGE_NAME + "" +
          ".app_permission_info_bundle";

  public static final String ACTION_COUNT_TRAFFIC = PACKAGE_NAME + ".count_traffic";

  public static final int TOTAL_TRAFFIC_TYPE = 0;
  public static final int SEND_TRAFFIC_TYPE = 1;
  public static final int RECEIVE_TRAFFIC_TYPE = 2;

  public static final int STATE_NETWORK_NULL = 100;
  public static final int STATE_NETWORK_MOBILE = 0;
  public static final int STATE_NETWORK_WIFI = 1;

  public static final String BASE_TRAFFIC_SHAREDPREFERENCES_RX= PACKAGE_NAME + "" +
          ".traffic_sharedPreferences_rx.";
  public static final String BASE_TRAFFIC_SHAREDPREFERENCES_TX = PACKAGE_NAME + "" +
          ".traffic_sharedPreferences_tx.";
  public static final String TRAFFIC_SHAREDPREFERENCES_RX_TOTAL = PACKAGE_NAME + "" +
          ".traffic_sharedPreferences_rx_total.";
  public static final String TRAFFIC_SHAREDPREFERENCES_TX_TOTAL = PACKAGE_NAME + "" +
          ".traffic_sharedPreferences_tx_total.";
  public static final String TRAFFIC_SHAREDPREFERENCES_TOTAL = PACKAGE_NAME + "" +
          ".traffic_sharedPreferences_total.";

/*  public static final String BASE_TRAFFIC_SHAREDPREFERENCES_RX_WIFI = PACKAGE_NAME + "" +
          ".traffic_sharedPreferences_rx_wifi.";
  public static final String BASE_TRAFFIC_SHAREDPREFERENCES_TX_WIFI = PACKAGE_NAME + "" +
          ".traffic_sharedPreferences_tx_wifi.";
  public static final String TRAFFIC_SHAREDPREFERENCES_RX_TOTAL_WIFI = PACKAGE_NAME + "" +
          ".traffic_sharedPreferences_rx_total_wifi";
  public static final String TRAFFIC_SHAREDPREFERENCES_TX_TOTAL_WIFI = PACKAGE_NAME + "" +
          ".traffic_sharedPreferences_tx_total_wifi";
  public static final String TRAFFIC_SHAREDPREFERENCES_TOTAL_WIFI = PACKAGE_NAME + "" +
          ".traffic_sharedPreferences_total_wifi";*/
}
