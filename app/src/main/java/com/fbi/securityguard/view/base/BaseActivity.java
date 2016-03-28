package com.fbi.securityguard.view.base;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.fbi.securityguard.R;

/**
 * author: bo on 2016/3/28 19:36.
 * email: bofu1993@163.com
 */
public class BaseActivity extends AppCompatActivity {


  public void showSimpleDialog(String title, String message) {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle(title);
    builder.setMessage(message);
    builder.setPositiveButton(getResources().getString(R.string.ok), null);
    builder.show();
  }

}
