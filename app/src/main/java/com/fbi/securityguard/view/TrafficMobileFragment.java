package com.fbi.securityguard.view;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.fbi.securityguard.R;
import com.fbi.securityguard.adapter.AppTrafficRecyclerViewAdapter;
import com.fbi.securityguard.entity.AppTrafficInfo;
import com.fbi.securityguard.presenter.AppTrafficPresenter;
import com.fbi.securityguard.utils.Commons;
import com.fbi.securityguard.utils.TrafficUtils;
import com.fbi.securityguard.view.base.BaseFragment;
import com.fbi.securityguard.view.viewinterface.AppTrafficInterface;
import com.fbi.securityguard.view.widget.sinkview.SinkView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fubo on 2016/4/3 0003.
 * email:bofu1993@163.com
 */
public class TrafficMobileFragment extends BaseFragment implements AppTrafficInterface {
  @Bind(R.id.rv_traffic_info)
  RecyclerView trafficRecyclerView;
  @Bind(R.id.pb_loading_mobile)
  ProgressBar loadingProgressBar;
  @Bind(R.id.sinkView)
  SinkView sinkView;
  private AppTrafficRecyclerViewAdapter appTrafficRecyclerViewAdapter;
  private List<AppTrafficInfo> appTrafficInfos = new ArrayList<>();
  private AppTrafficPresenter appTrafficPresenter;
  private long totalTraffic = 0;

  public static TrafficMobileFragment getInstance(String title) {
    TrafficMobileFragment trafficMobileFragment = new TrafficMobileFragment();
    Bundle args = new Bundle();
    args.putString(TITLE, title);
    trafficMobileFragment.setArguments(args);
    return trafficMobileFragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
      savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_traffic_mobile, container, false);
    ButterKnife.bind(this, view);
    initView();
    initData();
    initEvent();
    return view;
  }

  private void initEvent() {

  }

  private void initData() {
    appTrafficRecyclerViewAdapter = new AppTrafficRecyclerViewAdapter(getActivity()
        .getApplicationContext(), appTrafficInfos, totalTraffic, Commons.RECEIVE_TRAFFIC_TYPE);
    trafficRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    trafficRecyclerView.setAdapter(appTrafficRecyclerViewAdapter);
    trafficRecyclerView.setItemAnimator(new DefaultItemAnimator());
    trafficRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
        .build());
    if (appTrafficPresenter == null) {
      appTrafficPresenter = new AppTrafficPresenter(getActivity(), this);
    }
    appTrafficPresenter.loadMobileTrafficThisMonth();
    showLoadingProgress();
  }

  private void initView() {

  }

  @Override
  public void onDestroy() {
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
    String text="";
    if (TrafficUtils.getMBFromByte(totalTraffic) < 1) {
      text = TrafficUtils.getKBFromByte(totalTraffic) + getResources().getString(R.string
          .flowUnitKB);
    } else {
      text = TrafficUtils.getMBFromByte(totalTraffic) + getResources().getString(R.string
          .flowUnitMB);
    }
    sinkView.setText(text, SinkView.TYPE_USED);
    appTrafficRecyclerViewAdapter.setTotalTraffic(totalTraffic);
    appTrafficRecyclerViewAdapter.notifyDataSetChanged();
    hideLoadingProgress();
  }
}
