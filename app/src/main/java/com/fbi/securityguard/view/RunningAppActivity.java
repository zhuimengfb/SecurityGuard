package com.fbi.securityguard.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;

import com.fbi.securityguard.R;
import com.fbi.securityguard.adapter.RunningAppRecyclerViewAdapter;
import com.fbi.securityguard.adapter.helper.ItemTouchHelperAdapter;
import com.fbi.securityguard.adapter.helper.OnStartDragListener;
import com.fbi.securityguard.adapter.helper.SimpleItemTouchHelperCallback;
import com.fbi.securityguard.entity.RunningAppInfo;
import com.fbi.securityguard.presenter.RunningAppControlPresenter;
import com.fbi.securityguard.service.RunningAppService;
import com.fbi.securityguard.utils.TrafficUtils;
import com.fbi.securityguard.view.base.BaseActivity;
import com.fbi.securityguard.view.viewinterface.RunningAppActivityInterface;
import com.fbi.securityguard.view.widget.sinkview.SinkView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fubo on 2016/4/4 0004.
 * email:bofu1993@163.com
 */
public class RunningAppActivity extends BaseActivity implements RunningAppActivityInterface,OnStartDragListener,ItemTouchHelperAdapter {

  @Bind(R.id.toolbar)
  Toolbar toolbar;
  @Bind(R.id.rvRunningApp)
  RecyclerView recyclerView;
  @Bind(R.id.pb_loading)
  ProgressBar loadingProgress;
  @Bind(R.id.sinkView)
  SinkView sinkView;
  @Bind(R.id.autoProtectSwitch)
  Switch autoProtectSwitch;

  private RunningAppRecyclerViewAdapter runningAppRecyclerViewAdapter;
  private List<RunningAppInfo> runningAppInfos = new ArrayList<>();
  private RunningAppControlPresenter runningAppControlPresenter;
  private ItemTouchHelper mItemTouchHelper;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_running_app);
    ButterKnife.bind(this);
    initView();
    initData();
    initEvent();
  }

  private void startAutoProtectionService() {
    Intent intent = new Intent();
    intent.setClass(this, RunningAppService.class);
    startService(intent);
  }

  private void closeAutoProtectionService() {
    Intent intent = new Intent();
    intent.setClass(this, RunningAppService.class);
    stopService(intent);
  }

  private void initEvent() {
    autoProtectSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        runningAppControlPresenter.setAutoProtection(isChecked);
        if (isChecked) {
          startAutoProtectionService();
        } else {
          closeAutoProtectionService();
        }
      }
    });
    runningAppRecyclerViewAdapter.setOnAddWhiteListListener(new RunningAppRecyclerViewAdapter
        .OnAddWhiteListListener() {
      @Override
      public void changeWhiteList(int position) {
        if (runningAppInfos.get(position).isInWhiteList()) {
          runningAppInfos.get(position).setIsInWhiteList(false);
          runningAppControlPresenter.removeWhiteList(runningAppInfos.get(position).getAppInfo()
              .getPackageName());
        } else {
          runningAppControlPresenter.addToWhiteList(runningAppInfos.get(position).getAppInfo()
              .getPackageName());
          runningAppInfos.get(position).setIsInWhiteList(true);
        }
        runningAppRecyclerViewAdapter.notifyDataSetChanged();
      }
    });
  }

  private void initData() {
    if (runningAppControlPresenter == null) {
      runningAppControlPresenter = new RunningAppControlPresenter(this, this);
    }
    runningAppControlPresenter.updateAvailMemory();
    runningAppControlPresenter.loadingRunningApp();
    initSwitch();
  }

  private void initView() {
    toolbar.setTitle(getResources().getString(R.string.runningMonitor));
    setSupportActionBar(toolbar);
    setToolbarDisplayHomeAsUp();
    runningAppRecyclerViewAdapter = new RunningAppRecyclerViewAdapter(this, runningAppInfos);
    recyclerView.setAdapter(runningAppRecyclerViewAdapter);
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    recyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(this);
    mItemTouchHelper = new ItemTouchHelper(callback);
    mItemTouchHelper.attachToRecyclerView(recyclerView);
  }

  private void initSwitch() {
    runningAppControlPresenter.initSwitch();
  }

  @Override
  public boolean onItemMove(int fromPosition, int toPosition) {
    return false;
  }

  @Override
  public void onItemDismiss(int position) {
    RunningAppInfo runningAppInfo = runningAppInfos.get(position);
    runningAppInfos.remove(position);
    runningAppRecyclerViewAdapter.notifyItemRemoved(position);
    if (runningAppInfo.isInWhiteList()) {
      runningAppInfos.add(position, runningAppInfo);
      runningAppRecyclerViewAdapter.notifyItemInserted(position);
    } else {
      runningAppControlPresenter.killService(runningAppInfo.getAppInfo().getPackageName());
    }
  }

  @Override
  public void updateRunningAppList(List<RunningAppInfo> runningAppInfos) {
    this.runningAppInfos.clear();
    this.runningAppInfos.addAll(runningAppInfos);
    runningAppRecyclerViewAdapter.notifyDataSetChanged();
  }

  @Override
  public void openAutoProtect() {
    autoProtectSwitch.setChecked(true);
  }

  @Override
  public void closeAutoProtect() {
    autoProtectSwitch.setChecked(false);
  }

  @Override
  public void showLoading() {
    loadingProgress.setVisibility(View.VISIBLE);
  }

  @Override
  public void hideLoading() {
    loadingProgress.setVisibility(View.GONE);
  }

  @Override
  public void updateAvailMemory(long total, long avail) {
    float percent =  (float)(total-avail) / total;
    sinkView.setPercent(percent);
    String text = TrafficUtils.getMBFromKB(avail/1024) + getResources().getString(R.string
        .flowUnitMB);
    sinkView.setText(text, SinkView.TYPE_UNUSED);
  }

  @Override
  public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
    mItemTouchHelper.startDrag(viewHolder);
  }
}
