package com.fbi.securityguard.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fbi.securityguard.R;
import com.fbi.securityguard.adapter.helper.ItemTouchHelperAdapter;
import com.fbi.securityguard.entity.RunningAppInfo;
import com.fbi.securityguard.model.RunningAppModel;
import com.fbi.securityguard.utils.ListUtils;
import com.fbi.securityguard.utils.TrafficUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by fubo on 2016/4/4 0004.
 * email:bofu1993@163.com
 */
public class RunningAppRecyclerViewAdapter
    extends RecyclerView.Adapter<RunningAppRecyclerViewAdapter.RunningAppViewHolder> {

  private Context context;
  private List<RunningAppInfo> runningAppInfos;
  private OnAddWhiteListListener onAddWhiteListListener;
  private RunningAppModel runningAppModel;

  public RunningAppRecyclerViewAdapter(Context context, List<RunningAppInfo> runningAppInfos) {
    this.context = context;
    this.runningAppInfos = runningAppInfos;
    runningAppModel = new RunningAppModel(context);
  }

  @Override
  public RunningAppViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.item_running_app_info, parent, false);
    return new RunningAppViewHolder(view);
  }

  @Override
  public void onBindViewHolder(RunningAppViewHolder holder, final int position) {
    holder.setAppIcon(runningAppInfos.get(position).getAppInfo().getAppIcon());
    holder.setAppName(runningAppInfos.get(position).getAppInfo().getAppName());
    holder.setAppMemoryOccupy(runningAppInfos.get(position).getMemoryOccupy());
    if (! runningAppInfos.get(position).isInWhiteList()) {
      holder.showWhiteList();
    } else {
      holder.hideWhiteList();
    }
    holder.getIsInWhiteList().setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (onAddWhiteListListener != null) {
          onAddWhiteListListener.changeWhiteList(position);
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    return ListUtils.getListSize(runningAppInfos);
  }



  public interface OnAddWhiteListListener {
    void changeWhiteList(int position);
  }

  public void setOnAddWhiteListListener(OnAddWhiteListListener onAddWhiteListListener) {
    this.onAddWhiteListListener = onAddWhiteListListener;
  }

  class RunningAppViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.iv_app_icon)
    ImageView appIcon;
    @Bind(R.id.tv_app_name)
    TextView appName;
    @Bind(R.id.tvMemoryOccupy)
    TextView appMemoryOccupy;
    @Bind(R.id.tvWhiteList)
    TextView isInWhiteList;

    public RunningAppViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void setAppIcon(Drawable icon) {
      appIcon.setImageDrawable(icon);
    }

    public void setAppName(String name) {
      appName.setText(name);
    }

    public void setAppMemoryOccupy(int occupy) {
      String text;
      if (TrafficUtils.getMBFromKB(occupy) > 1) {
        text = TrafficUtils.getMBFromKB(occupy) + context.getResources()
            .getString(R.string.flowUnitMB);
      } else {
        text = occupy + context.getResources().getString(R.string.flowUnitKB);
      }
      appMemoryOccupy.setText(text);
    }

    public void showWhiteList() {
      isInWhiteList.setText(context.getResources().getString(R.string.whiteList));
      isInWhiteList.setTextColor(context.getResources().getColor(R.color.colorPrimary));
    }

    public void hideWhiteList() {
      isInWhiteList.setText(context.getResources().getString(R.string.removeWhiteList));
      isInWhiteList.setTextColor(context.getResources().getColor(R.color.colorAccent));
    }

    public TextView getIsInWhiteList() {
      return isInWhiteList;
    }
  }
}
