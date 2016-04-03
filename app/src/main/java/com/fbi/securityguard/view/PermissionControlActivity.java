package com.fbi.securityguard.view;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.fbi.securityguard.R;
import com.fbi.securityguard.adapter.SectionsPagerAdapter;
import com.fbi.securityguard.view.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fubo on 2016/4/3 0003.
 * email:bofu1993@163.com
 */
public class PermissionControlActivity extends BaseActivity {

  @Bind(R.id.toolbar)
  Toolbar toolbar;
  @Bind(R.id.tab_permission)
  TabLayout tabLayout;
  @Bind(R.id.container)
  ViewPager viewPager;
  private List<Fragment> fragmentList = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_permission_control);
    ButterKnife.bind(this);
    initView();
  }

  private void initView() {
    toolbar.setTitle(getResources().getString(R.string.permission_control));
    setSupportActionBar(toolbar);
    setToolbarDisplayHomeAsUp();
    PermissionClassifierFragment permissionClassifierFragment = PermissionClassifierFragment
        .getInstance(getResources().getString(R.string.permission));
    PermissionAppFragment permissionAppFragment = PermissionAppFragment.getInstance(getResources()
        .getString(R.string.app));
    fragmentList.add(permissionClassifierFragment);
    fragmentList.add(permissionAppFragment);
    SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),
        fragmentList);
    viewPager.setAdapter(sectionsPagerAdapter);
    tabLayout.setupWithViewPager(viewPager);
  }
}
