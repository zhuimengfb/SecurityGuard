package com.fbi.securityguard.model.modelinterface;

import com.fbi.securityguard.entity.ClassifierPermissionInfo;

import java.util.List;

/**
 * Created by fubo on 2016/4/3 0003.
 * email:bofu1993@163.com
 */
public interface ClassifierPermissionModelInterface {

  interface OnGetResultCallback {
    void getResult(List<ClassifierPermissionInfo> classifierPermissionInfos);
  }

  void queryPermissionClassifierResult(OnGetResultCallback callback);
}
