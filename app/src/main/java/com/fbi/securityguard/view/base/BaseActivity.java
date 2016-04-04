package com.fbi.securityguard.view.base;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

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

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      finish();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  public void setToolbarDisplayHomeAsUp() {
    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setHomeButtonEnabled(true);
      actionBar.setDisplayHomeAsUpEnabled(true);
    }
  }

  public void gotoNewActivity(Class<?> cls ) {
    Intent intent = new Intent();
    intent.setClass(this, cls);
    startActivity(intent);
  }

}
