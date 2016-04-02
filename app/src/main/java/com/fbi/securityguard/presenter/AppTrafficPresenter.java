package com.fbi.securityguard.presenter;

import android.content.Context;
import android.os.Handler;

import com.fbi.securityguard.entity.AppTrafficInfo;
import com.fbi.securityguard.model.AppTrafficModel;
import com.fbi.securityguard.model.modelinterface.AppTrafficModelInterface;
import com.fbi.securityguard.utils.Commons;
import com.fbi.securityguard.utils.DateUtils;
import com.fbi.securityguard.view.viewinterface.AppTrafficInterface;

import java.util.Date;
import java.util.List;

/**
 * author: bo on 2016/3/31 19:30.
 * email: bofu1993@163.com
 */
public class AppTrafficPresenter {

  private AppTrafficInterface appTrafficInterface;
  private Context context;
  private AppTrafficModelInterface appTrafficModelInterface;
  private Handler handler;

  /**
   * 构造函数.
   *
   * @param context:上下文
   * @param appTrafficInterface：界面
   */
  public AppTrafficPresenter(Context context, AppTrafficInterface appTrafficInterface) {
    this.context = context;
    this.appTrafficInterface = appTrafficInterface;
    this.appTrafficModelInterface = new AppTrafficModel(this.context);
    handler = new Handler(context.getMainLooper());
  }

  public AppTrafficPresenter(Context context) {
    this.context = context;
    this.appTrafficModelInterface = new AppTrafficModel(this.context);
  }

  /**
   * 统计上个时段的网络用量情况.
   *
   * @param type：0为mobile，1为wifi
   */
  public void countTraffic(int type) {
    appTrafficModelInterface.countTraffic(type);
  }

  public void unbind() {
    this.appTrafficInterface = null;
  }

  /**
   * 展示网络用量情况.
   *
   * @param type:发送，接收和总共流量
   */
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

  public void loadWifiTrafficThisWeek() {
    appTrafficInterface.showLoadingProgress();
    Date now = new Date();
    Date lastWeek = new Date(now.getTime() - DateUtils.WEEK_PERIOD);
    appTrafficModelInterface.queryWifiTotalTraffic(lastWeek, now, new AppTrafficModelInterface
        .GetTrafficCallback() {
      @Override
      public void getTraffic(final List<AppTrafficInfo> appTrafficInfos, final long total) {
        handler.post(new Runnable() {
          @Override
          public void run() {
            appTrafficInterface.updateList(appTrafficInfos, total);
            appTrafficInterface.hideLoadingProgress();
          }
        });
      }
    });
  }


  private void loadTrafficThisMonth(int trafficType) {

  }

}
