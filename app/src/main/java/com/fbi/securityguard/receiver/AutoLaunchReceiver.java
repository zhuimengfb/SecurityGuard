package com.fbi.securityguard.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.fbi.securityguard.service.TrafficService;
import com.fbi.securityguard.service.WifiService;

/**
 * Created by fubo on 2016/4/7 0007.
 * email:bofu1993@163.com
 */
public class AutoLaunchReceiver extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent) {
    Intent intent1 = new Intent();
    intent1.setClass(context, TrafficService.class);
    context.startService(intent1);
    Intent intent2 = new Intent();
    intent2.setClass(context, WifiService.class);
    context.startService(intent2);
  }
}
