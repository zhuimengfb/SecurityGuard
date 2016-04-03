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
import com.fbi.securityguard.adapter.ClassifierPermissionRecyclerViewAdapter;
import com.fbi.securityguard.entity.ClassifierPermissionInfo;
import com.fbi.securityguard.presenter.PermissionClassifierPresenter;
import com.fbi.securityguard.utils.Commons;
import com.fbi.securityguard.view.base.BaseFragment;
import com.fbi.securityguard.view.viewinterface.PermissionClassifierInterface;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fubo on 2016/4/3 0003.
 * email:bofu1993@163.com
 */
public class PermissionClassifierFragment extends BaseFragment implements
                                                               PermissionClassifierInterface {
  @Bind(R.id.rv_permission_classifier)
  RecyclerView permissionClassifierRecyclerView;
  @Bind(R.id.pb_loading)
  ProgressBar loadingProgressBar;
  private ClassifierPermissionRecyclerViewAdapter classifierPermissionRecyclerViewAdapter;

  private List<ClassifierPermissionInfo> classifierPermissionInfos = new ArrayList<>();
  private PermissionClassifierPresenter permissionClassifierPresenter = null;


  public static PermissionClassifierFragment getInstance(String title) {
    PermissionClassifierFragment permissionClassifierFragment = new PermissionClassifierFragment();
    Bundle args = new Bundle();
    args.putString(TITLE, title);
    permissionClassifierFragment.setArguments(args);
    return permissionClassifierFragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
      savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_permission_classfier, container, false);
    ButterKnife.bind(this,view);
    initView();
    initData();
    initEvent();
    return view;
  }

  private void initEvent() {
    classifierPermissionRecyclerViewAdapter.setOnItemClickListener(new ClassifierPermissionRecyclerViewAdapter.OnItemClickListener() {
      @Override
      public void click(View view, int position) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), PermissionAppListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Commons.KEY_PERMISSION_CLASSIFIER, classifierPermissionInfos
            .get(position));
        intent.putExtra(Commons.KEY_PERMISSION_CLASSIFIER_BUNDLE, bundle);
        getActivity().startActivity(intent);
      }
    });
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    if (permissionClassifierPresenter != null) {
      permissionClassifierPresenter.unbind();
    }
    permissionClassifierPresenter = null;
  }

  private void initData() {
    if (permissionClassifierPresenter == null) {
      permissionClassifierPresenter = new PermissionClassifierPresenter(getActivity(), this);
    }
    permissionClassifierPresenter.loadingPermissionClassifier();
  }

  private void initView() {
    classifierPermissionRecyclerViewAdapter = new ClassifierPermissionRecyclerViewAdapter(
        getActivity().getApplicationContext(), classifierPermissionInfos);
    permissionClassifierRecyclerView.setAdapter(classifierPermissionRecyclerViewAdapter);
    permissionClassifierRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    permissionClassifierRecyclerView.setItemAnimator(new DefaultItemAnimator());
    permissionClassifierRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration
        .Builder(getActivity()).build());
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
  public void updatePermissionList(List<ClassifierPermissionInfo> classifierPermissionInfos) {
    this.classifierPermissionInfos.clear();
    this.classifierPermissionInfos.addAll(classifierPermissionInfos);
    classifierPermissionRecyclerViewAdapter.notifyDataSetChanged();
  }
}
