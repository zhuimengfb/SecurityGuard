package com.fbi.securityguard;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fbi.securityguard.presenter.RunningAppPresenter;
import com.fbi.securityguard.service.TrafficService;
import com.fbi.securityguard.view.AppListViewActivity;
import com.fbi.securityguard.view.PermissionControlActivity;
import com.fbi.securityguard.view.TrafficControlActivity;
import com.fbi.securityguard.view.base.BaseActivity;
import com.fbi.securityguard.view.viewinterface.RunningAppInterface;
import com.fbi.securityguard.view.widget.circleview.DashedCircularProgress;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements RunningAppInterface {

  @Bind(R.id.toolbar)
  Toolbar toolbar;
  @Bind(R.id.circle_view)
  DashedCircularProgress circularProgress;
  @Bind(R.id.tv_memory_number)
  TextView memoryNumberTextView;
  @Bind(R.id.traffic_layout)
  RelativeLayout trafficLayout;
  @Bind(R.id.permission_layout)
  RelativeLayout permissionLayout;
  @Bind(R.id.app_layout)
  RelativeLayout appLayout;
  @Bind(R.id.running_layout)
  RelativeLayout runningLayout;
  @Bind(R.id.wifi_layout)
  RelativeLayout wifiLayout;
  @Bind(R.id.accelerateButton)
  Button accelerateButton;
  private RunningAppPresenter runningAppPresenter = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    initView();
    initData();
    getMemoryInfo();
    initEvent();
  }

  private void initData() {
    if (runningAppPresenter == null) {
      runningAppPresenter = new RunningAppPresenter(this, this);
    }
    runningAppPresenter.getAvailMemory();
  }

  private void initEvent() {
    trafficLayout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        gotoTrafficActivity();
      }
    });
    permissionLayout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        gotoPermissionActivity();
      }
    });
    appLayout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        gotoAppControlActiviyt();
      }
    });
    runningLayout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        gotoRunningAppActivity();
      }
    });
    wifiLayout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        gotoWifiMonitorActivity();
      }
    });
    accelerateButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        runningAppPresenter.killOthers();
      }
    });
  }

  private void gotoRunningAppActivity() {

  }
  private void gotoWifiMonitorActivity() {

  }
  private void gotoTrafficActivity() {
    Intent intent = new Intent();
    intent.setClass(this, TrafficControlActivity.class);
    startActivity(intent);
  }
  private void gotoPermissionActivity() {
    Intent intent = new Intent();
    intent.setClass(this, PermissionControlActivity.class);
    startActivity(intent);
  }

  private void gotoAppControlActiviyt() {
    Intent intent = new Intent();
    intent.setClass(this, AppListViewActivity.class);
    startActivity(intent);
  }
  private void initView() {
    toolbar.setTitle(getResources().getString(R.string.app_name));
    setSupportActionBar(toolbar);
  }

  private void getMemoryInfo() {
    final ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
    ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
    activityManager.getMemoryInfo(info);
    circularProgress.setValue(100 - info.availMem * 100 / info.totalMem);
    String number = String.valueOf(100 - info.availMem * 100 / info.totalMem) + "%";
    memoryNumberTextView.setText(number);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Intent intent = new Intent();
    intent.setClass(this, TrafficService.class);
    stopService(intent);
  }

  @Override
  public void updateAvailMemory(long percent) {
    circularProgress.setValue(percent);
    String number = String.valueOf(percent) + "%";
    memoryNumberTextView.setText(number);
  }
}
