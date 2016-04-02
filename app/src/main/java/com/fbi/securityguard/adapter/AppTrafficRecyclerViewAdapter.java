package com.fbi.securityguard.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fbi.securityguard.R;
import com.fbi.securityguard.entity.AppTrafficInfo;
import com.fbi.securityguard.utils.Commons;
import com.fbi.securityguard.utils.ListUtils;
import com.fbi.securityguard.utils.TrafficUtils;

import java.util.List;

/**
 * author: bo on 2016/3/31 19:30.
 * email: bofu1993@163.com
 */
public class AppTrafficRecyclerViewAdapter extends RecyclerView
        .Adapter<AppTrafficRecyclerViewAdapter.TrafficViewHolder> {

  private List<AppTrafficInfo> appTrafficInfos;
  private Context context;
  private long totalTraffic;
  private int type;

  public AppTrafficRecyclerViewAdapter(Context context, List<AppTrafficInfo> appTrafficInfos,
                                       long totalTraffic, int type) {
    this.context = context;
    this.appTrafficInfos = appTrafficInfos;
    this.totalTraffic = totalTraffic;
    this.type = type;
  }


  @Override
  public TrafficViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.item_traffic_info, parent, false);
    return new TrafficViewHolder(view);
  }

  @Override
  public void onBindViewHolder(TrafficViewHolder holder, int position) {
    holder.setAppIcon(appTrafficInfos.get(position).getAppInfo().getAppIcon());
    holder.setAppName(appTrafficInfos.get(position).getAppInfo().getAppName());
    long traffic = 0;
    switch (type) {
      case Commons.TOTAL_TRAFFIC_TYPE:
        traffic = appTrafficInfos.get(position).getTotalTraffic();
        break;
      case Commons.RECEIVE_TRAFFIC_TYPE:
        traffic = appTrafficInfos.get(position).getRxTraffic();
        break;
      case Commons.SEND_TRAFFIC_TYPE:
        traffic = appTrafficInfos.get(position).getTxTraffic();
        break;
      default:
        break;
    }
    holder.setTrafficNumber(traffic);
    int progress = (int) (traffic * 100 / totalTraffic);
    holder.setProgress(progress);
  }


  public void setTotalTraffic(long totalTraffic) {
    this.totalTraffic = totalTraffic;
  }

  @Override
  public int getItemCount() {
    return ListUtils.getListSize(appTrafficInfos);
  }

  class TrafficViewHolder extends RecyclerView.ViewHolder {

    public ImageView appIconImageView;
    public TextView appNameTextView;
    public ProgressBar progressBar;
    public TextView trafficNumber;

    public TrafficViewHolder(View itemView) {
      super(itemView);
      appIconImageView = (ImageView) itemView.findViewById(R.id.iv_app_icon);
      appNameTextView = (TextView) itemView.findViewById(R.id.tv_app_name);
      progressBar = (ProgressBar) itemView.findViewById(R.id.pb_progress);
      trafficNumber = (TextView) itemView.findViewById(R.id.tv_traffic_number);
    }

    public void setAppIcon(Drawable drawable) {
      appIconImageView.setImageDrawable(drawable);
    }

    public void setAppName(String appName) {
      appNameTextView.setText(appName);
    }

    public void setProgress(int progress) {
      progressBar.setProgress(progress);
    }

    public void setTrafficNumber(long number) {
      StringBuffer traffic = new StringBuffer("");
      double kb = TrafficUtils.getKBFromByte(number);
      if (kb < 1024) {
        traffic.append(String.valueOf(kb) + context.getResources().getString(R.string
                .flowUnitKB));
      } else {
        traffic.append(String.valueOf(TrafficUtils.getMBFromByte(number)) + context.getResources()
                .getString(R.string.flowUnitMB));
      }
      trafficNumber.setText(traffic);
    }
  }

}
