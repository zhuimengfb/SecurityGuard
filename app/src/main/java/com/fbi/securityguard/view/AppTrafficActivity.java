package com.fbi.securityguard.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.fbi.securityguard.R;
import com.fbi.securityguard.adapter.AppTrafficRecyclerViewAdapter;
import com.fbi.securityguard.entity.AppTrafficInfo;
import com.fbi.securityguard.presenter.AppTrafficPresenter;
import com.fbi.securityguard.service.TrafficService;
import com.fbi.securityguard.utils.Commons;
import com.fbi.securityguard.view.base.BaseActivity;
import com.fbi.securityguard.view.viewinterface.AppTrafficInterface;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * author: bo on 2016/3/31 19:27.
 * email: bofu1993@163.com
 */
public class AppTrafficActivity extends BaseActivity implements AppTrafficInterface {

  @Bind(R.id.rv_traffic_info)
  RecyclerView trafficRecyclerView;
  @Bind(R.id.toolbar)
  Toolbar toolbar;
  @Bind(R.id.pb_loading)
  ProgressBar loadingProgressBar;
  private AppTrafficRecyclerViewAdapter appTrafficRecyclerViewAdapter;
  private List<AppTrafficInfo> appTrafficInfos = new ArrayList<>();
  private AppTrafficPresenter appTrafficPresenter;
  private long totalTraffic = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_traffic_list);
    ButterKnife.bind(this);
    initTrafficService();
    initView();
    initData();
    initEvent();
  }

  private void initTrafficService() {
    Intent intent = new Intent();
    intent.setClass(this, TrafficService.class);
    startService(intent);
  }

  private void initView() {
    toolbar.setTitle(getResources().getString(R.string.trafficMonitor));
    setSupportActionBar(toolbar);
  }

  private void initData() {
    appTrafficRecyclerViewAdapter = new AppTrafficRecyclerViewAdapter(getApplicationContext(),
        appTrafficInfos, totalTraffic, Commons.RECEIVE_TRAFFIC_TYPE);
    trafficRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    trafficRecyclerView.setAdapter(appTrafficRecyclerViewAdapter);
    trafficRecyclerView.setItemAnimator(new DefaultItemAnimator());
    trafficRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
        .build());
    if (appTrafficPresenter == null) {
      appTrafficPresenter = new AppTrafficPresenter(this, this);
    }
    appTrafficPresenter.loadWifiTrafficThisWeek();
  }

  private void initEvent() {

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    appTrafficPresenter.unbind();
    appTrafficPresenter = null;
  }

  @Override
  public void showLoadingProgress() {
    loadingProgressBar.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideLoadingProgress() {
    loadingProgressBar.setVisibility(View.GONE);
  }

  @Override
  public void updateList(List<AppTrafficInfo> appTrafficInfoList, long totalTraffic) {
    this.appTrafficInfos.clear();
    this.appTrafficInfos.addAll(appTrafficInfoList);
    this.totalTraffic = totalTraffic;
    appTrafficRecyclerViewAdapter.setTotalTraffic(totalTraffic);
    appTrafficRecyclerViewAdapter.notifyDataSetChanged();
  }
}
