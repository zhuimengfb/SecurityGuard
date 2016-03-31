package com.fbi.securityguard.presenter;

import android.content.Context;

import com.fbi.securityguard.entity.AppTrafficInfo;
import com.fbi.securityguard.model.AppTrafficModel;
import com.fbi.securityguard.model.modelinterface.AppTrafficModelInterface;
import com.fbi.securityguard.utils.Commons;
import com.fbi.securityguard.view.viewinterface.AppTrafficInterface;

import java.util.List;

/**
 * author: bo on 2016/3/31 19:30.
 * email: bofu1993@163.com
 */
public class AppTrafficPresenter {

  private AppTrafficInterface appTrafficInterface;
  private Context context;
  private AppTrafficModelInterface appTrafficModelInterface;

  public AppTrafficPresenter(Context context, AppTrafficInterface appTrafficInterface) {
    this.context = context;
    this.appTrafficInterface = appTrafficInterface;
    this.appTrafficModelInterface = new AppTrafficModel(this.context);
  }

  public void unbind() {
    this.appTrafficInterface = null;
  }

  public void loadTraffic(int type) {
    switch (type) {
      case Commons.RECEIVE_TRAFFIC_TYPE:
        appTrafficModelInterface.queryRxTraffic(new AppTrafficModelInterface.GetTrafficCallback() {
          @Override
          public void getTraffic(List<AppTrafficInfo> appTrafficInfos, long total) {

            appTrafficInterface.updateList(appTrafficInfos, total);
          }
        });
        break;
      case Commons.SEND_TRAFFIC_TYPE:
        appTrafficModelInterface.queryTxTraffic(new AppTrafficModelInterface.GetTrafficCallback() {
          @Override
          public void getTraffic(List<AppTrafficInfo> appTrafficInfos, long total) {
            appTrafficInterface.updateList(appTrafficInfos, total);
          }
        });
        break;
      case Commons.TOTAL_TRAFFIC_TYPE:
        appTrafficModelInterface.queryTotalTraffic(new AppTrafficModelInterface
                .GetTrafficCallback() {
          @Override
          public void getTraffic(List<AppTrafficInfo> appTrafficInfos, long total) {
            appTrafficInterface.updateList(appTrafficInfos, total);
          }
        });
        break;
      default:
        break;
    }
  }

}
