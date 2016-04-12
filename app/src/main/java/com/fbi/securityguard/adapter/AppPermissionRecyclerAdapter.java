package com.fbi.securityguard.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fbi.securityguard.R;
import com.fbi.securityguard.entity.AppPermissionInfo;

import java.util.List;

/**
 * author: bo on 2016/3/31 13:59.
 * email: bofu1993@163.com
 */
public class AppPermissionRecyclerAdapter extends RecyclerView
        .Adapter<AppPermissionRecyclerAdapter.AppPermissionViewHolder> {

  private Context context;
  private List<AppPermissionInfo> appPermissionInfos;
  private AppPermissionRecyclerAdapter.OnItemClickListener onItemClickListener;

  public AppPermissionRecyclerAdapter(Context context, List<AppPermissionInfo> appPermissionInfos) {
    this.context = context;
    this.appPermissionInfos = appPermissionInfos;
  }

  public void setOnItemClickListener(AppPermissionRecyclerAdapter.OnItemClickListener
                                             onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  @Override
  public AppPermissionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.item_app_permission_info, parent,
            false);
    return new AppPermissionViewHolder(view);
  }

  @Override
  public void onBindViewHolder(AppPermissionViewHolder holder, final int position) {
    holder.setAppName(appPermissionInfos.get(position).getAppInfo().getAppName());
    holder.setAppIcon(appPermissionInfos.get(position).getAppInfo().getAppIcon());
    if (appPermissionInfos.get(position).getPermissions()!=null) {
      holder.setAppPermissionNumber(appPermissionInfos.get(position).getPermissions().length);
    } else {
      holder.setAppPermissionNumber(0);
    }
    holder.appPermissionItemLayout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        onItemClickListener.click(v, position);
      }
    });
  }

  @Override
  public int getItemCount() {
    return (appPermissionInfos == null ? 0 : appPermissionInfos.size());
  }

  public interface OnItemClickListener {
    void click(View view, int position);
  }

  class AppPermissionViewHolder extends RecyclerView.ViewHolder {

    public ImageView appIcon;
    public TextView appName;
    public TextView appPermissionNumber;
    public LinearLayout appPermissionItemLayout;

    public AppPermissionViewHolder(View itemView) {
      super(itemView);
      appIcon = (ImageView) itemView.findViewById(R.id.iv_app_icon);
      appName = (TextView) itemView.findViewById(R.id.tv_app_name);
      appPermissionNumber = (TextView) itemView.findViewById(R.id.tv_permission_number);
      appPermissionItemLayout = (LinearLayout) itemView.findViewById(R.id.app_permission_item);
    }

    public void setAppIcon(Drawable drawable) {
      appIcon.setImageDrawable(drawable);
    }

    public void setAppName(String appName) {
      this.appName.setText(appName);
    }

    public void setAppPermissionNumber(int number) {
      String permissionNumber = context.getResources().getString(R.string.need) + String
              .valueOf(number) + context.getResources().getString(R.string.n_permission);
      this.appPermissionNumber.setText(permissionNumber);
    }
  }
}
