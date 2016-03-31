package com.fbi.securityguard.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fbi.securityguard.entity.TrafficData;

/**
 * author: bo on 2016/3/31 22:23.
 * email: bofu1993@163.com
 */
public class TrafficDataDbHelper extends SQLiteOpenHelper {

  public static final String TRAFFIC_DB_NAME = "traffic.db";
  public static final String TABLE_TRAFFIC_DATA = "tb_traffic_data";
  private static final int DATABASE_VERSION = 1;

  public TrafficDataDbHelper(Context context) {
    super(context, TRAFFIC_DB_NAME, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL("create table if not exists " + TABLE_TRAFFIC_DATA +
            "(id integer primary key autoincrement," + TrafficData.UID + " VARCHAR, " + TrafficData
            .RX_TRAFFIC + " NUMBER, " + TrafficData.TX_TRAFFIC + " number, " + TrafficData
            .TOTAL_TRAFFIC + " number, " + TrafficData.START_TIME + " timestamp, " + TrafficData
            .END_TIME + " timestamp");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("drop table " + TABLE_TRAFFIC_DATA);
    onCreate(db);
  }
}
