package com.fbi.securityguard.service.intentservice;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by fubo on 2016/4/2 0002.
 * email:bofu1993@163.com
 */
public class TrafficIntentService extends IntentService {


  /**
   * Creates an IntentService.  Invoked by your subclass's constructor.
   *
   * @param name Used to name the worker thread, important only for debugging.
   */
  public TrafficIntentService(String name) {
    super(name);
  }

  @Override
  protected void onHandleIntent(Intent intent) {

  }
}
