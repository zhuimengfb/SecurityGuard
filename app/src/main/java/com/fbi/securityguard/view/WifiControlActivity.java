package com.fbi.securityguard.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.fbi.securityguard.R;
import com.fbi.securityguard.service.WifiService;
import com.fbi.securityguard.view.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fubo on 2016/4/12 0012.
 * email:bofu1993@163.com
 */
public class WifiControlActivity extends BaseActivity {

  @Bind(R.id.toolbar)
  Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_wifi_control);
    ButterKnife.bind(this);
    toolbar.setTitle(getResources().getString(R.string.trafficMonitor));
    setSupportActionBar(toolbar);
    setToolbarDisplayHomeAsUp();
    Intent intent = new Intent();
    intent.setClass(this, WifiService.class);
    startService(intent);
  }

}
