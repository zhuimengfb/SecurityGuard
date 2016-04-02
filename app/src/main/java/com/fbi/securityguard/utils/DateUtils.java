package com.fbi.securityguard.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * author: bo on 2016/3/28 21:21.
 * email: bofu1993@163.com
 */
public class DateUtils {


  public static final long WEEK_PERIOD = 7 * 24 * 60 * 60 * 1000;

  private static final String BASE_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
  private static final String BASE_DATE_PATTERN = "yyyy-MM-dd";
  private static final String BASE_TIME_PATTERN = "HH:mm:ss";

  private static final SimpleDateFormat baseDateTimeDateFormat = new SimpleDateFormat(
      BASE_DATE_TIME_PATTERN, Locale.CHINA);

  public static String getDateTime(long date) {

    return baseDateTimeDateFormat.format(new Date(date));
  }

  public static Date parseDateTime(String dateTime) {
    Date date = null;
    try {
      date = baseDateTimeDateFormat.parse(dateTime);
    } catch (ParseException exception) {
      exception.printStackTrace();
    }
    return date;
  }
}
