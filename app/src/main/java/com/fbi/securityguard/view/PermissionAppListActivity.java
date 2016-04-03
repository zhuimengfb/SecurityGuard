package com.fbi.securityguard.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.fbi.securityguard.R;
import com.fbi.securityguard.adapter.AppInfoRecyclerAdapter;
import com.fbi.securityguard.entity.AppInfo;
import com.fbi.securityguard.entity.ClassifierPermissionInfo;
import com.fbi.securityguard.utils.Commons;
import com.fbi.securityguard.view.base.BaseActivity;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fubo on 2016/4/3 0003.
 * email:bofu1993@163.com
 */
public class PermissionAppListActivity extends BaseActivity {

  @Bind(R.id.rv_app_info)
  RecyclerView appRecyclerView;
  @Bind(R.id.toolbar)
  Toolbar toolbar;
  private List<AppInfo> appInfoList = new ArrayList<>();
  private String title = "";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_app_list);
    ButterKnife.bind(this);
    initData();
    initView();
  }


  private void initData() {
    Intent intent = getIntent();
    Bundle bundle = intent.getBundleExtra(Commons.KEY_PERMISSION_CLASSIFIER_BUNDLE);
    ClassifierPermissionInfo classifierPermissionInfo = (ClassifierPermissionInfo) bundle
        .get(Commons.KEY_PERMISSION_CLASSIFIER);
    if (classifierPermissionInfo != null) {
      appInfoList.addAll(classifierPermissionInfo.getAppInfos());
      title = classifierPermissionInfo.getPermissionName();
    }
    for (int i = 0; i < appInfoList.size(); i++) {
      try {
        appInfoList.get(i).setAppIcon(getPackageManager().getPackageInfo(appInfoList.get(i)
            .getPackageName(), 0).applicationInfo.loadIcon(getPackageManager()));
      } catch (Exception exception) {
        exception.printStackTrace();
      }

    }
  }

  private void initView() {
    AppInfoRecyclerAdapter appInfoRecyclerAdapter = new AppInfoRecyclerAdapter(this, appInfoList);
    appRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    appRecyclerView.setItemAnimator(new DefaultItemAnimator());
    appRecyclerView.setAdapter(appInfoRecyclerAdapter);
    appRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
    toolbar.setTitle(title);
    setSupportActionBar(toolbar);
  }
}
