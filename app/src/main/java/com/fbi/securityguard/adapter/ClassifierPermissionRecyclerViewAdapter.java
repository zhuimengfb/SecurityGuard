package com.fbi.securityguard.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fbi.securityguard.R;
import com.fbi.securityguard.entity.ClassifierPermissionInfo;
import com.fbi.securityguard.utils.ListUtils;

import java.util.List;

/**
 * Created by fubo on 2016/4/3 0003.
 * email:bofu1993@163.com
 */
public class ClassifierPermissionRecyclerViewAdapter
    extends RecyclerView.Adapter<ClassifierPermissionRecyclerViewAdapter.ClassifierViewHolder> {

  private List<ClassifierPermissionInfo> classifierPermissionInfos;
  private Context context;
  private OnItemClickListener onItemClickListener;
  public ClassifierPermissionRecyclerViewAdapter(Context context, List<ClassifierPermissionInfo>
      classifierPermissionInfos) {
    this.context = context;
    this.classifierPermissionInfos = classifierPermissionInfos;
  }

  @Override
  public ClassifierViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(context).inflate(R.layout.item_permission_classfier, parent,
        false);
    return new ClassifierViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ClassifierViewHolder holder, final int position) {
    holder.setAppNumber(classifierPermissionInfos.get(position).getAppInfos().size());
    holder.setPermissionName(classifierPermissionInfos.get(position).getPermissionName());
    holder.linearLayout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (onItemClickListener != null) {
          onItemClickListener.click(view, position);
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    return ListUtils.getListSize(classifierPermissionInfos);
  }
  public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
    this.onItemClickListener = onItemClickListener;
  }
  public interface OnItemClickListener {
    void click(View view, int position);
  }

  class ClassifierViewHolder extends RecyclerView.ViewHolder {

    public TextView permissionName;
    public TextView appNumber;
    public LinearLayout linearLayout;
    public ClassifierViewHolder(View itemView) {
      super(itemView);
      permissionName = (TextView) itemView.findViewById(R.id.tv_permission_classifier_name);
      appNumber = (TextView) itemView.findViewById(R.id.tv_permission_classifier_number);
      linearLayout = (LinearLayout) itemView.findViewById(R.id.layout_permission_classifier);
    }

    private String getAppPermissionSuffix() {
      return context.getResources().getString(R.string.number_unit) + context.getResources()
          .getString(R.string.app);
    }
    public void setAppNumber(int number) {
      String text = number + getAppPermissionSuffix();
      appNumber.setText(text);
    }
    public void setPermissionName(String name) {
      permissionName.setText(name);
    }
  }
}
