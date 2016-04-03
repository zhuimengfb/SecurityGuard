package com.fbi.securityguard.view.viewinterface;

import com.fbi.securityguard.entity.ClassifierPermissionInfo;

import java.util.List;

/**
 * Created by fubo on 2016/4/3 0003.
 * email:bofu1993@163.com
 */
public interface PermissionClassifierInterface {

  void showLoadingProgress();

  void hideLoadingProgress();

  void updatePermissionList(List<ClassifierPermissionInfo> classifierPermissionInfos);
}
