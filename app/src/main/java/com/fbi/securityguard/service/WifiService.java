package com.fbi.securityguard.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.fbi.securityguard.R;
import com.fbi.securityguard.utils.Commons;

import java.util.List;

/**
 * Created by fubo on 2016/4/7 0007.
 * email:bofu1993@163.com
 */
public class WifiService extends Service {

  private MyReceiver myReceiver = new MyReceiver();
  private WifiManager wifiManager;

  static final int SECURITY_NONE = 0;
  static final int SECURITY_WEP = 1;
  static final int SECURITY_PSK = 2;
  static final int SECURITY_EAP = 3;

  static int getSecurity(WifiConfiguration config) {
    if (config.allowedKeyManagement.get(WifiConfiguration.KeyMgmt.WPA_PSK)) {
      return SECURITY_PSK;
    }
    if (config.allowedKeyManagement.get(WifiConfiguration.KeyMgmt.WPA_EAP) || config.allowedKeyManagement.get(WifiConfiguration.KeyMgmt.IEEE8021X)) {
      return SECURITY_EAP;
    }
    return (config.wepKeys[0] != null) ? SECURITY_WEP : SECURITY_NONE;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
    registerMyReceiver();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    return START_STICKY;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    unregisterReceiver(myReceiver);
    Intent intent = new Intent();
    intent.setClass(this, WifiService.class);
    startService(intent);
  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  private void registerMyReceiver() {
    IntentFilter intentFilter = new IntentFilter();
    intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
    intentFilter.addAction(Commons.ACTION_COUNT_TRAFFIC);
    registerReceiver(myReceiver, intentFilter);
  }

  class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
      ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context
          .CONNECTIVITY_SERVICE);
      NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
      if (networkInfo != null) {
        if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
          WifiInfo wifiInfo = wifiManager.getConnectionInfo();
          List<WifiConfiguration> wifiConfigList = wifiManager.getConfiguredNetworks();
          for (WifiConfiguration wifiConfiguration : wifiConfigList) {
            //配置过的SSID
            String configSSid = wifiConfiguration.SSID;
            configSSid = configSSid.replace("\"", "");

            //当前连接SSID
            String currentSSid =wifiInfo.getSSID();
            currentSSid = currentSSid.replace("\"", "");

            //比较networkId，防止配置网络保存相同的SSID
            if (currentSSid.equals(configSSid) && wifiInfo.getNetworkId() == wifiConfiguration
                .networkId) {
              if (getSecurity(wifiConfiguration) == 0) {
                Toast.makeText(context, getResources().getString(R.string.noSecurity), Toast
                    .LENGTH_LONG).show();
              } else {
                Toast.makeText(context, getResources().getString(R.string.WifiSecurity), Toast
                    .LENGTH_LONG).show();
              }
            }
          }
        }
      }
    }
  }
}
