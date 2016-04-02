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


}
