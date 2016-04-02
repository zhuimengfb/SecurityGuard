package com.fbi.securityguard;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fbi.securityguard.service.TrafficService;
import com.fbi.securityguard.view.AppPermissionInfoActivity;
import com.fbi.securityguard.view.AppTrafficActivity;
import com.fbi.securityguard.view.base.BaseActivity;
import com.fbi.securityguard.view.widget.circleview.DashedCircularProgress;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

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

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    initView();
    /*Intent intent = new Intent();
    intent.setClass(this, TrafficService.class);
    startService(intent);*/
    getMemoryInfo();
    initEvent();
    //initTrafficService();
  }

  private void initTrafficService() {
    Intent intent = new Intent();
    intent.setClass(this, TrafficService.class);
    startService(intent);
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
  }
  private void gotoTrafficActivity() {
    Intent intent = new Intent();
    intent.setClass(this, AppTrafficActivity.class);
    startActivity(intent);
  }
  private void gotoPermissionActivity() {
    Intent intent = new Intent();
    intent.setClass(this, AppPermissionInfoActivity.class);
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
}
