package com.fbi.securityguard;

import android.content.Intent;
import android.os.Bundle;

import com.fbi.securityguard.service.TrafficService;
import com.fbi.securityguard.view.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent();
        intent.setClass(this, TrafficService.class);
        startService(intent);
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Intent intent = new Intent();
        intent.setClass(this, TrafficService.class);
        stopService(intent);
    }
}
