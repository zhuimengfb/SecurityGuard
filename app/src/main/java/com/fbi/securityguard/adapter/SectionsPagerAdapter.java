package com.fbi.securityguard.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.fbi.securityguard.view.base.BaseFragment;

import java.util.List;

/**
 * Created by fubo on 2016/4/3 0003.
 * email:bofu1993@163.com
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
  private List<Fragment> fragmentList;
  public SectionsPagerAdapter(FragmentManager fm,List<Fragment> fragmentList) {
    super(fm);
    this.fragmentList = fragmentList;
  }

  @Override
  public Fragment getItem(int position) {
    return fragmentList.get(position);
  }

  @Override
  public int getCount() {
    return fragmentList.size();
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return (String) fragmentList.get(position).getArguments().get(BaseFragment.TITLE);
  }
}
