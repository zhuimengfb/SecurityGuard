package com.fbi.securityguard.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fbi.securityguard.db.helper.TrafficDataDbHelper;
import com.fbi.securityguard.entity.TrafficData;
import com.fbi.securityguard.utils.DateUtils;

/**
 * author: bo on 2016/3/31 22:21.
 * email: bofu1993@163.com
 */
public class TrafficDataDbManager {

  private static TrafficDataDbManager trafficDataDbManager;
  private TrafficDataDbHelper helper;
  private SQLiteDatabase db;

  private TrafficDataDbManager(Context context) {
    helper = new TrafficDataDbHelper(context);
    db = helper.getWritableDatabase();
  }

  public static synchronized TrafficDataDbManager getInstance(Context context) {
    if (trafficDataDbManager == null) {
      trafficDataDbManager = new TrafficDataDbManager(context);
    }
    return trafficDataDbManager;
  }

  public void insertTrafficData(TrafficData trafficData) {
    ContentValues contentValues = new ContentValues();
    contentValues.put(TrafficData.END_TIME, DateUtils.getDateTime(trafficData.getEndTime()
            .getTime()));
    contentValues.put(TrafficData.RX_TRAFFIC, trafficData.getRxTraffic());
    contentValues.put(TrafficData.START_TIME, DateUtils.getDateTime(trafficData.getStartTime()
            .getTime()));
    contentValues.put(TrafficData.TOTAL_TRAFFIC, trafficData.getTotalTraffic());
    contentValues.put(TrafficData.TX_TRAFFIC, trafficData.getTxTraffic());
    contentValues.put(TrafficData.TYPE, trafficData.getType());
    contentValues.put(TrafficData.UID, trafficData.getUid());
    db.insert(helper.TABLE_TRAFFIC_DATA, null, contentValues);
  }


  private TrafficData getTrafficDataFromCursor(Cursor cursor) {
    TrafficData trafficData = new TrafficData();
    if (cursor != null) {
      trafficData.setTxTraffic(cursor.getLong(cursor.getColumnIndex(TrafficData.TX_TRAFFIC)));
      trafficData.setRxTraffic(cursor.getLong(cursor.getColumnIndex(TrafficData.RX_TRAFFIC)));
      trafficData.setTotalTraffic(cursor.getLong(cursor.getColumnIndex(TrafficData.TOTAL_TRAFFIC)));
      trafficData.setType(cursor.getInt(cursor.getColumnIndex(TrafficData.TYPE)));
      trafficData.setUid(cursor.getInt(cursor.getColumnIndex(TrafficData.UID)));
      trafficData.setEndTime(DateUtils.parseDateTime(cursor.getString(cursor.getColumnIndex
              (TrafficData.END_TIME))));
      trafficData.setStartTime(DateUtils.parseDateTime(cursor.getString(cursor.getColumnIndex
              (TrafficData.START_TIME))));
    }
    return trafficData;
  }

}
