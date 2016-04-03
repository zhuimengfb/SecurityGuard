package com.fbi.securityguard.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fubo on 2016/4/3 0003.
 * email:bofu1993@163.com
 */
public class PermissionPairUtils {

  private Map<String, String> permissionPairMap = new HashMap<>();

  private static PermissionPairUtils permissionPairUtils = null;
  private PermissionPairUtils() {
    initMap();
  }

  private void initMap() {
    permissionPairMap.put("android.permission.WAKE_LOCK", "允许唤醒设备");
    permissionPairMap.put("android.permission.INTERNET", "允许访问网络");
    permissionPairMap.put("android.permission.CALL_PHONE", "拨打电话");
    permissionPairMap.put("android.permission.READ_CONTACTS", "读取联系人");
    permissionPairMap.put("android.permission.READ_SMS", "读取短信");
    permissionPairMap.put("android.permission.READ_CALENDAR", "读取日程");
    permissionPairMap.put("android.permission.RECEIVE_SMS", "接收短信");
    permissionPairMap.put("android.permission.RECEIVE_MMS", "接收彩信");
    permissionPairMap.put("android.permission.SEND_SMS", "发送短信");
    permissionPairMap.put("android.permission.WRITE_SMS", "编写短信");
    permissionPairMap.put("android.permission.WRITE_CONTACTS", "修改/写入联系人");
    permissionPairMap.put("android.permission.WRITE_EXTERNAL_STORAGE", "修改/写入外部存储");
    permissionPairMap.put("android.permission.RECEIVE_BOOT_COMPLETED", "开机自启动");
  }

  public String parsePermission(String permission) {
    return permissionPairMap.get(permission);
  }

  public synchronized static PermissionPairUtils getInstance() {
    if (permissionPairUtils == null) {
      return new PermissionPairUtils();
    }
    return permissionPairUtils;
  }

}
