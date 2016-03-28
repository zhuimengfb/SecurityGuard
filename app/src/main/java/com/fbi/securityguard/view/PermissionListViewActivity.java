package com.fbi.securityguard.view;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.fbi.securityguard.R;
import com.fbi.securityguard.adapter.PermissionRecyclerAdapter;
import com.fbi.securityguard.view.base.BaseActivity;
import com.fbi.securityguard.view.viewinterface.PermissionListViewInterface;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class PermissionListViewActivity extends BaseActivity implements PermissionListViewInterface {

    @Bind(R.id.rv_permission_list)
    RecyclerView permissionRecyclerView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    private PermissionRecyclerAdapter permissionRecyclerAdapter;
    private List<String> permissionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_list);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView(){
        setSupportActionBar(toolbar);
    }

    private void initData() {
        permissionRecyclerAdapter = new PermissionRecyclerAdapter(this, permissionList);
        permissionRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        permissionRecyclerView.setAdapter(permissionRecyclerAdapter);
        permissionRecyclerView.setItemAnimator(new DefaultItemAnimator());
        permissionRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).build());
    }

    @Override
    public void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }

    @Override
    public void updatePermissionList(List<String> permissionList) {
        this.permissionList.clear();
        this.permissionList.addAll(permissionList);
        permissionRecyclerAdapter.notifyDataSetChanged();
    }
}
