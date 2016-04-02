package com.fbi.securityguard.presenter;

import android.content.Context;

import com.fbi.securityguard.entity.AppPermissionInfo;
import com.fbi.securityguard.model.AppPermissionModel;
import com.fbi.securityguard.model.modelinterface.AppPermissionModelInterface;
import com.fbi.securityguard.view.viewinterface.AppPermissionInterface;

import java.util.List;

/**
 * author: bo on 2016/3/31 13:54.
 * email: bofu1993@163.com
 */
public class AppPermissionPresenter {

  private Context context;
  private AppPermissionInterface appPermissionInterface;
  private AppPermissionModelInterface appPermissionModelInterface;

  public AppPermissionPresenter(AppPermissionInterface appPermissionInterface, Context context) {
    this.appPermissionInterface = appPermissionInterface;
    this.context = context;
    appPermissionModelInterface = new AppPermissionModel(this.context);
  }

  public void loadingPermission() {
    appPermissionInterface.showLoadingProgress();
    appPermissionModelInterface.queryAppPermissionList(new AppPermissionModelInterface
            .AppPermissionListCallback() {
      @Override
      public void onGetAppPermissionList(List<AppPermissionInfo> appPermissionInfoList) {
        appPermissionInterface.updateAppPermissionInfoList(appPermissionInfoList);
        appPermissionInterface.hideLoadingProgress();
      }
    });
  }

  public void unbind() {
    appPermissionInterface = null;
  }

}
