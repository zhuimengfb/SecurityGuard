package com.fbi.securityguard.view.base;

import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.fbi.securityguard.R;

/**
 * Created by fubo on 2016/4/3 0003.
 * email:bofu1993@163.com
 */
public class BaseFragment extends Fragment {
  public static final String TITLE = "FRAGMENT_TITLE";

  public void showSimpleDialog(String title, String message) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setTitle(title);
    builder.setMessage(message);
    builder.setPositiveButton(getResources().getString(R.string.ok), null);
    builder.show();
  }
}
