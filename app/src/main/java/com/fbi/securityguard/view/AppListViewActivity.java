package com.fbi.securityguard.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.fbi.securityguard.R;
import com.fbi.securityguard.adapter.AppInfoRecyclerAdapter;
import com.fbi.securityguard.entity.AppInfo;
import com.fbi.securityguard.presenter.AppInfoListPresenter;
import com.fbi.securityguard.utils.DateUtils;
import com.fbi.securityguard.view.base.BaseActivity;
import com.fbi.securityguard.view.viewinterface.AppListViewInterface;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import butterknife.Bind;
import butterknife.ButterKnife;

import java.util.ArrayList;
import java.util.List;

/**
 * author: bo on 2016/3/28 19:40.
 * email: bofu1993@163.com
 */
public class AppListViewActivity extends BaseActivity implements AppListViewInterface {

  private static AppInfoListPresenter appInfoListPresenter = null;
  @Bind(R.id.rv_app_info)
  RecyclerView appRecyclerView;
  @Bind(R.id.pb_loading)
  ProgressBar loadingProgressBar;
  @Bind(R.id.toolbar)
  Toolbar toolbar;
  private AppInfoRecyclerAdapter appInfoRecyclerAdapter;
  private List<AppInfo> appInfoList = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_app_list);
    ButterKnife.bind(this);
    initView();
    initData();
    iniEvent();
  }

  private void iniEvent() {
    appInfoRecyclerAdapter.setOnItemClickListener(new AppInfoRecyclerAdapter
            .OnItemClickListener() {
      @Override
      public void click(View view, int position) {
        showAppDetailInfo(appInfoList.get(position));
      }

      @Override
      public boolean longClick(View view, int position) {
        uninstallApp(appInfoList.get(position));
        appInfoList.remove(position);
        appInfoRecyclerAdapter.notifyItemRemoved(position);
        return true;
      }
    });
  }

  private void initData() {
    if (appInfoListPresenter == null)
      appInfoListPresenter = new AppInfoListPresenter(this, this);
    appInfoListPresenter.loadingAppInfoList();
  }

  private void initView() {
    appInfoRecyclerAdapter = new AppInfoRecyclerAdapter(this, appInfoList);
    appRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    appRecyclerView.setItemAnimator(new DefaultItemAnimator());
    appRecyclerView.setAdapter(appInfoRecyclerAdapter);
    appRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
    toolbar.setTitle(getResources().getString(R.string.appList));
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
  public void updateAppInfoList(List<AppInfo> appInfoList) {
    this.appInfoList.clear();
    this.appInfoList.addAll(appInfoList);
    appInfoRecyclerAdapter.notifyDataSetChanged();
  }

  @Override
  public void showAppDetailInfo(AppInfo appInfo) {
    showSimpleDialog(appInfo.getAppName(), getAppInfoDetail(appInfo));
  }

  private String getAppInfoDetail(AppInfo appInfo) {
    return getResources().getString(R.string.installTime) + ":" + DateUtils.getDateTime(appInfo
            .getAppInstallTime()) + "\n" + getResources().getString(R.string.versionCode) + ":" +
            appInfo.getVersionName() + "\n" + getResources().getString(R.string.packageName) +
            ":" + appInfo.getPackageName();
  }

  @Override
  public void uninstallApp(AppInfo appInfo) {
    Uri uri = Uri.parse("package:" + appInfo.getPackageName());
    //获取删除包名的URI
    Intent intent = new Intent(Intent.ACTION_DELETE, uri);
    startActivity(intent);
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    if (appInfoListPresenter != null)
      appInfoListPresenter.unBind(this, this);
    appInfoListPresenter = null;
  }
}
