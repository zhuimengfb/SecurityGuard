package com.fbi.securityguard.presenter;

import android.content.Context;
import android.os.Handler;

import com.fbi.securityguard.entity.ClassifierPermissionInfo;
import com.fbi.securityguard.model.ClassifierPermissionModel;
import com.fbi.securityguard.model.modelinterface.ClassifierPermissionModelInterface;
import com.fbi.securityguard.view.viewinterface.PermissionClassifierInterface;

import java.util.List;

/**
 * Created by fubo on 2016/4/3 0003.
 * email:bofu1993@163.com
 */
public class PermissionClassifierPresenter {

  private PermissionClassifierInterface permissionClassifierInterface;
  private Context context;
  private ClassifierPermissionModelInterface classifierPermissionModelInterface;
  private Handler handler;
  public PermissionClassifierPresenter(Context context, PermissionClassifierInterface
      permissionClassifierInterface) {
    this.context = context;
    this.permissionClassifierInterface = permissionClassifierInterface;
    classifierPermissionModelInterface = new ClassifierPermissionModel(context);
    handler = new Handler(context.getMainLooper());
  }


  public void loadingPermissionClassifier() {
    permissionClassifierInterface.showLoadingProgress();
    classifierPermissionModelInterface.queryPermissionClassifierResult(new ClassifierPermissionModelInterface.OnGetResultCallback() {
      @Override
      public void getResult(final List<ClassifierPermissionInfo> classifierPermissionInfos) {
        handler.post(new Runnable() {
          @Override
          public void run() {
            permissionClassifierInterface.updatePermissionList(classifierPermissionInfos);
            permissionClassifierInterface.hideLoadingProgress();
          }
        });
      }
    });
  }

  public void unbind() {
    this.permissionClassifierInterface = null;
  }

}
