package com.fbi.securityguard.utils;

import com.fbi.securityguard.entity.AppTrafficInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * author: bo on 2016/3/31 20:47.
 * email: bofu1993@163.com
 */
public class TrafficUtils {

  private static final DecimalFormat df = new DecimalFormat("######0.00");

  public static double getMBFromByte(long traffic) {
    return Double.valueOf(df.format(traffic / 1024 / 1024));
  }

  public static double getKBFromByte(long traffic) {
    return Double.valueOf(df.format(traffic / 1024));
  }

  public static List<AppTrafficInfo> sortAndFilterTraffic(List<AppTrafficInfo> appTrafficInfos,
                                                          int type) {
    List<AppTrafficInfo> appTrafficInfoList = new ArrayList<>();
    for (int i = 0; i < appTrafficInfos.size(); i++) {
      switch (type) {
        case Commons.TOTAL_TRAFFIC_TYPE:
          if (appTrafficInfos.get(i).getTotalTraffic() > 1024) {
            appTrafficInfoList.add(appTrafficInfos.get(i));
          }
          break;
        case Commons.SEND_TRAFFIC_TYPE:
          if (appTrafficInfos.get(i).getTxTraffic() > 1024) {
            appTrafficInfoList.add(appTrafficInfos.get(i));
          }
          break;
        case Commons.RECEIVE_TRAFFIC_TYPE:
          if (appTrafficInfos.get(i).getRxTraffic() > 1024) {
            appTrafficInfoList.add(appTrafficInfos.get(i));
          }
          break;
        default:
          break;
      }
    }
    switch (type) {
      case Commons.TOTAL_TRAFFIC_TYPE:
        Collections.sort(appTrafficInfoList, new AppTrafficInfo.TotoalTrafficComparator());
        break;
      case Commons.SEND_TRAFFIC_TYPE:
        Collections.sort(appTrafficInfoList, new AppTrafficInfo.TxTrafficComparator());
        break;
      case Commons.RECEIVE_TRAFFIC_TYPE:
        Collections.sort(appTrafficInfoList, new AppTrafficInfo.RxTrafficComparator());
        break;
      default:
        break;
    }
    return appTrafficInfoList;
  }


  public static Long getUidRxBytes(int localUid) {

    File dir = new File("/proc/uid_stat/");
    String[] children = dir.list();
    if (!Arrays.asList(children).contains(String.valueOf(localUid))) {
      return 0L;
    }
    File uidFileDir = new File("/proc/uid_stat/" + String.valueOf(localUid));
    File uidActualFileReceived = new File(uidFileDir, "tcp_rcv");
    String textReceived = "0";
    try {
      BufferedReader brReceived = new BufferedReader(new FileReader(
              uidActualFileReceived));
      String receivedLine;
      if ((receivedLine = brReceived.readLine()) != null) {
        textReceived = receivedLine;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return Long.valueOf(textReceived);
  }

  public static Long getUidTxBytes(int localUid) {
    File dir = new File("/proc/uid_stat/");
    String[] children = dir.list();

    if (!Arrays.asList(children).contains(String.valueOf(localUid))) {
      return 0L;
    }
    File uidFileDir = new File("/proc/uid_stat/" + String.valueOf(localUid));
    File uidActualFileReceived = new File(uidFileDir, "tcp_snd");
    String textSend = "0";
    try {
      BufferedReader brSend = new BufferedReader(new FileReader(
              uidActualFileReceived));
      String sendLine;
      if ((sendLine = brSend.readLine()) != null) {
        textSend = sendLine;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return Long.valueOf(textSend);
  }
}
