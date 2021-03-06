package com.fbi.securityguard.presenter;

import android.content.Context;

import com.fbi.securityguard.entity.AppInfo;
import com.fbi.securityguard.model.AppInfoListModel;
import com.fbi.securityguard.model.modelinterface.AppInfoListModelInterface;
import com.fbi.securityguard.view.viewinterface.AppListViewInterface;

import java.util.List;

/**
 * author: bo on 2016/3/28 20:14.
 * email: bofu1993@163.com
 */
public class AppInfoListPresenter {

    private AppListViewInterface appListView = null;
    private AppInfoListModelInterface appInfoListModel = null;
    private Context context;

    public AppInfoListPresenter(AppListViewInterface appListView, Context context) {
        this.appListView = appListView;
        this.context = context;
        this.appInfoListModel = new AppInfoListModel(this.context);
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

    public void unBind(AppListViewInterface appListView, Context context) {
        this.appListView = null;
    }

}
