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
import com.fbi.securityguard.adapter.AppPermissionRecyclerAdapter;
import com.fbi.securityguard.entity.AppPermissionInfo;
import com.fbi.securityguard.presenter.AppPermissionPresenter;
import com.fbi.securityguard.utils.Commons;
import com.fbi.securityguard.view.base.BaseActivity;
import com.fbi.securityguard.view.viewinterface.AppPermissionInterface;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import butterknife.Bind;
import butterknife.ButterKnife;

import java.util.ArrayList;
import java.util.List;

/**
 * author: bo on 2016/3/31 13:52.
 * email: bofu1993@163.com
 */
public class AppPermissionInfoActivity extends BaseActivity implements AppPermissionInterface {


  @Bind(R.id.rv_app_permission)
  RecyclerView appPermissionRecyclerView;
  @Bind(R.id.toolbar)
  Toolbar toolbar;
  @Bind(R.id.pb_loading)
  ProgressBar loadingProgressBar;
  private AppPermissionRecyclerAdapter appPermissionRecyclerAdapter;
  private List<AppPermissionInfo> appPermissionInfos = new ArrayList<>();
  private AppPermissionPresenter appPermissionPresenter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_app_permission_list);
    ButterKnife.bind(this);
    initView();
    initData();
    iniEvent();
  }

  private void iniEvent() {
    appPermissionRecyclerAdapter.setOnItemClickListener(new AppPermissionRecyclerAdapter
            .OnItemClickListener() {
      @Override
      public void click(View view, int position) {
        Intent intent = new Intent();
        intent.setClass(AppPermissionInfoActivity.this, PermissionListViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Commons.KEY_APP_PERMISSION_INFO, appPermissionInfos.get(position));
        intent.putExtra(Commons.KEY_APP_PERMISSION_INFO_BUNDLE, bundle);
        startActivity(intent);
      }
    });
  }

  private void initData() {
    if (appPermissionPresenter == null) {
      appPermissionPresenter = new AppPermissionPresenter(this, getApplicationContext());
    }
    appPermissionPresenter.loadingPermission();
  }

  private void initView() {
    appPermissionRecyclerAdapter = new AppPermissionRecyclerAdapter(getApplicationContext(),
            appPermissionInfos);
    appPermissionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    appPermissionRecyclerView.setAdapter(appPermissionRecyclerAdapter);
    appPermissionRecyclerView.setItemAnimator(new DefaultItemAnimator());
    appPermissionRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
            .build());
    toolbar.setTitle(getResources().getString(R.string.appPermission));
    setSupportActionBar(toolbar);
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
  public void updateAppPermissionInfoList(List<AppPermissionInfo> appPermissionInfos) {
    this.appPermissionInfos.clear();
    this.appPermissionInfos.addAll(appPermissionInfos);
    appPermissionRecyclerAdapter.notifyDataSetChanged();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (appPermissionPresenter != null)
      appPermissionPresenter.unbind();
    appPermissionPresenter = null;
  }
}
