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
import com.fbi.securityguard.entity.AppInfo;

import java.util.List;

/**
 * author: bo on 2016/3/28 20:00.
 * email: bofu1993@163.com
 */
public class AppInfoRecyclerAdapter extends RecyclerView.Adapter<AppInfoRecyclerAdapter
        .AppViewHolder> {

  private List<AppInfo> appInfoList;
  private Context context;
  private OnItemClickListener onItemClickListener;

  public AppInfoRecyclerAdapter(Context context, List<AppInfo> appInfoList) {
    this.context = context;
    this.appInfoList = appInfoList;
  }

  @Override
  public AppViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.item_app_info, parent, false);
    return new AppViewHolder(view);
  }

  @Override
  public void onBindViewHolder(AppViewHolder holder, final int position) {
    holder.setAppIcon(appInfoList.get(position).getAppIcon());
    holder.setAppName(appInfoList.get(position).getAppName());
    if (onItemClickListener != null) {
      holder.appItemLinearLayout.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          onItemClickListener.click(view, position);
        }
      });
      holder.appItemLinearLayout.setOnLongClickListener(new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View view) {
          return onItemClickListener.longClick(view, position);
        }
      });
    }
  }

  @Override
  public int getItemCount() {
    return (appInfoList == null ? 0 : appInfoList.size());
  }

  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }

  public interface OnItemClickListener {
    void click(View view, int position);

    boolean longClick(View view, int position);
  }

  class AppViewHolder extends RecyclerView.ViewHolder {
    public ImageView appIconImageView;
    public TextView appNameTextView;
    public LinearLayout appItemLinearLayout;

    public AppViewHolder(View itemView) {
      super(itemView);
      appIconImageView = (ImageView) itemView.findViewById(R.id.iv_app_icon);
      appNameTextView = (TextView) itemView.findViewById(R.id.tv_app_name);
      appItemLinearLayout = (LinearLayout) itemView.findViewById(R.id.app_info_item);
    }

    public void setAppIcon(Drawable drawable) {
      appIconImageView.setImageDrawable(drawable);
    }

    public void setAppName(String appName) {
      appNameTextView.setText(appName);
    }
  }
}
