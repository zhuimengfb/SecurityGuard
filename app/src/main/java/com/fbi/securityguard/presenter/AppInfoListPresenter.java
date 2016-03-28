package com.fbi.securityguard.presenter;

import android.content.Context;

import com.fbi.securityguard.entity.AppInfo;
import com.fbi.securityguard.model.AppInfoListModel;
import com.fbi.securityguard.model.modelinterface.AppInfoListModelInterface;
import com.fbi.securityguard.view.viewinterface.AppListViewInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * author: bo on 2016/3/28 20:14.
 * email: bofu1993@163.com
 */
public class AppInfoListPresenter {

  private AppListViewInterface appListView = null;
  private AppInfoListModelInterface appInfoListModel = null;
  private List<AppInfo> appInfoList = new ArrayList<>();

  public AppInfoListPresenter(AppListViewInterface appListView, Context context) {
    this.appListView = appListView;
    this.appInfoListModel = new AppInfoListModel(context);
  }

  public void loadingAppInfoList() {
    appListView.showLoadingProgress();
    appInfoListModel.queryAppListWithoutSystemApp(new AppInfoListModelInterface.AppListCallback() {
      @Override
      public void onGetAppList(List<AppInfo> appInfoList) {
        appListView.updateAppInfoList(appInfoList);
        appListView.hideLoadingProgress();
      }
    });
  }

}
