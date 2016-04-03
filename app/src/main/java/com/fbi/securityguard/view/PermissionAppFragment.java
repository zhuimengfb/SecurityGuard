package com.fbi.securityguard.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.fbi.securityguard.R;
import com.fbi.securityguard.adapter.AppPermissionRecyclerAdapter;
import com.fbi.securityguard.entity.AppPermissionInfo;
import com.fbi.securityguard.presenter.AppPermissionPresenter;
import com.fbi.securityguard.utils.Commons;
import com.fbi.securityguard.view.base.BaseFragment;
import com.fbi.securityguard.view.viewinterface.AppPermissionInterface;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fubo on 2016/4/3 0003.
 * email:bofu1993@163.com
 */
public class PermissionAppFragment extends BaseFragment implements AppPermissionInterface {

  @Bind(R.id.rv_app_permission)
  RecyclerView appPermissionRecyclerView;
  @Bind(R.id.pb_loading)
  ProgressBar loadingProgressBar;
  private AppPermissionRecyclerAdapter appPermissionRecyclerAdapter;
  private List<AppPermissionInfo> appPermissionInfos = new ArrayList<>();
  private AppPermissionPresenter appPermissionPresenter;

  public static PermissionAppFragment getInstance(String title) {
    PermissionAppFragment permissionAppFragment = new PermissionAppFragment();
    Bundle args = new Bundle();
    args.putString(TITLE, title);
    permissionAppFragment.setArguments(args);
    return permissionAppFragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
      savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_permission_app, container, false);
    ButterKnife.bind(this,view);
    initView();
    initData();
    iniEvent();
    return view;
  }

  private void iniEvent() {
    appPermissionRecyclerAdapter.setOnItemClickListener(new AppPermissionRecyclerAdapter
        .OnItemClickListener() {
      @Override
      public void click(View view, int position) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), PermissionListViewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Commons.KEY_APP_PERMISSION_INFO, appPermissionInfos.get(position));
        intent.putExtra(Commons.KEY_APP_PERMISSION_INFO_BUNDLE, bundle);
        startActivity(intent);
      }
    });
  }
  private void initData() {
    if (appPermissionPresenter == null) {
      appPermissionPresenter = new AppPermissionPresenter(this, getActivity());
    }
    appPermissionPresenter.loadingPermission();
  }
  private void initView() {
    appPermissionRecyclerAdapter = new AppPermissionRecyclerAdapter(getActivity(),
        appPermissionInfos);
    appPermissionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    appPermissionRecyclerView.setAdapter(appPermissionRecyclerAdapter);
    appPermissionRecyclerView.setItemAnimator(new DefaultItemAnimator());
    appPermissionRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(
        getActivity()).build());
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
}
