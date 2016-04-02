package com.fbi.securityguard.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fbi.securityguard.presenter.AppTrafficPresenter;
import com.fbi.securityguard.utils.Commons;


/**
 * author: bo on 2016/4/1 13:59.
 * email: bofu1993@163.com
 */
public class TrafficService extends Service {

  private static int lastNetworkState = Commons.STATE_NETWORK_NULL;
  private MyReceiver myReceiver = new MyReceiver();
  private AppTrafficPresenter appTrafficPresenter;

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    registerMyReceiver();
    new Thread(new Runnable() {
      @Override
      public void run() {
        getInitNetworkState();
        appTrafficPresenter = new AppTrafficPresenter(getApplicationContext());
      }
    }).start();
  }

  private void getInitNetworkState() {
    ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context
        .CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
    if (networkInfo == null) {
      lastNetworkState = Commons.STATE_NETWORK_NULL;
    } else {
      lastNetworkState = networkInfo.getType();
    }
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    if (appTrafficPresenter != null) {
      appTrafficPresenter.countTraffic(lastNetworkState);
    }
    return super.onStartCommand(intent, flags, startId);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    unregisterReceiver(myReceiver);
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
      //如果是从无网络切换过来，只需更新lastNetworkState
      if (lastNetworkState == Commons.STATE_NETWORK_NULL) {
        lastNetworkState = networkInfo.getType();
        Log.d("traffic_change", "100--" + networkInfo.getType());
      } else {
        //更新状态并且进行流量统计
        appTrafficPresenter.countTraffic(lastNetworkState);
        if (networkInfo == null) {
          Log.d("traffic_change", lastNetworkState + "--" + Commons.STATE_NETWORK_NULL);
          lastNetworkState = Commons.STATE_NETWORK_NULL;
        } else {
          if (lastNetworkState != networkInfo.getType()) {
            Log.d("traffic_change", lastNetworkState + "--" + networkInfo.getType());
            lastNetworkState = networkInfo.getType();
          }
        }
      }
    }
  }

}
