package com.fbi.securityguard.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fbi.securityguard.db.helper.TrafficDataDbHelper;
import com.fbi.securityguard.entity.TrafficData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * author: bo on 2016/3/31 22:21.
 * email: bofu1993@163.com
 */
public class TrafficDataDbManager {

  private static TrafficDataDbManager trafficDataDbManager;
  private SQLiteDatabase db;

  private TrafficDataDbManager(Context context) {
    TrafficDataDbHelper trafficDataDbHelper = new TrafficDataDbHelper(context);
    db = trafficDataDbHelper.getWritableDatabase();
  }

  public static synchronized TrafficDataDbManager getInstance(Context context) {
    if (trafficDataDbManager == null) {
      trafficDataDbManager = new TrafficDataDbManager(context);
    }
    return trafficDataDbManager;
  }

  public void insertTrafficData(TrafficData trafficData) {
    ContentValues contentValues = new ContentValues();
    contentValues.put(TrafficData.END_TIME, trafficData.getEndTime().getTime());
    contentValues.put(TrafficData.RX_TRAFFIC, trafficData.getRxTraffic());
    contentValues.put(TrafficData.START_TIME, trafficData.getStartTime().getTime());
    contentValues.put(TrafficData.TOTAL_TRAFFIC, trafficData.getTotalTraffic());
    contentValues.put(TrafficData.TX_TRAFFIC, trafficData.getTxTraffic());
    contentValues.put(TrafficData.TYPE, trafficData.getType());
    contentValues.put(TrafficData.UID, trafficData.getUid());
    db.insert(TrafficDataDbHelper.TABLE_TRAFFIC_DATA, null, contentValues);
  }

  public List<TrafficData> selectTrafficDataByUidAndTime(int uid, Date startTime, Date endTime,
                                                          int type) {
    List<TrafficData> trafficDatas = new ArrayList<>();
    String sql = "select * from " + TrafficDataDbHelper.TABLE_TRAFFIC_DATA + " where "
        + TrafficData.UID + " =? and " + TrafficData.START_TIME + " >? and " + TrafficData.END_TIME
        + " <? and " + TrafficData.TYPE + " =? ";
    Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(uid),
        String.valueOf(startTime.getTime()), String.valueOf(endTime.getTime()),
        String.valueOf(type)});
    while (cursor.moveToNext()) {
      trafficDatas.add(getTrafficDataFromCursor(cursor));
    }
    return trafficDatas;
  }

  private TrafficData getTrafficDataFromCursor(Cursor cursor) {
    TrafficData trafficData = new TrafficData();
    if (cursor != null) {
      trafficData.setTxTraffic(cursor.getLong(cursor.getColumnIndex(TrafficData.TX_TRAFFIC)));
      trafficData.setRxTraffic(cursor.getLong(cursor.getColumnIndex(TrafficData.RX_TRAFFIC)));
      trafficData.setTotalTraffic(cursor.getLong(cursor.getColumnIndex(TrafficData.TOTAL_TRAFFIC)));
      trafficData.setType(cursor.getInt(cursor.getColumnIndex(TrafficData.TYPE)));
      trafficData.setUid(cursor.getInt(cursor.getColumnIndex(TrafficData.UID)));
      trafficData.setEndTime(new Date(cursor.getLong(cursor.getColumnIndex(TrafficData.END_TIME))));
      trafficData.setStartTime(new Date(cursor.getLong(cursor.getColumnIndex(TrafficData
          .START_TIME))));
    }
    return trafficData;
  }

}
