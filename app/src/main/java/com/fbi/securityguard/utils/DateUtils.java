package com.fbi.securityguard.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author: bo on 2016/3/28 21:21.
 * email: bofu1993@163.com
 */
public class DateUtils {

  private static final String BASE_DATE_TIME_PATTERN="yyyy-MM-dd HH:mm:ss";
  private static final String BASE_DATE_PATTERN="yyyy-MM-dd";
  private static final String BASE_TIME_PATTERN="HH:mm:ss";


  public static String getDateTime(long date) {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(BASE_DATE_TIME_PATTERN);
    return simpleDateFormat.format(new Date(date));
  }
}
