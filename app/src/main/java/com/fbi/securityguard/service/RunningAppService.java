package com.fbi.securityguard.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fbi.securityguard.presenter.RunningAppPresenter;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by fubo on 2016/4/4 0004.
 * email:bofu1993@163.com
 */
public class RunningAppService extends Service {

  private Timer timer;
  private static final int PERIOD = 60000 * 30;
  private RunningAppPresenter runningAppPresenter = null;

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    if (runningAppPresenter == null) {
      runningAppPresenter = new RunningAppPresenter(this);
    }
    if (timer == null) {
      timer = new Timer();
    }
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        if (runningAppPresenter != null) {
          runningAppPresenter.killOthersInService();
        }
      }
    }, 0, PERIOD);
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    return super.onStartCommand(intent, flags, startId);
  }

  @Override
  public void onDestroy() {
    if (timer != null) {
      timer.cancel();
    }
    super.onDestroy();
  }
}
