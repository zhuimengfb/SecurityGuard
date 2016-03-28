package com.fbi.securityguard.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fbi.securityguard.R;

import java.util.List;

/**
 * Created by fubo on 2016/3/30 0030.
 * email:bofu1993@163.com
 */
public class PermissionRecyclerAdapter extends RecyclerView.Adapter<PermissionRecyclerAdapter.PermissionViewHolder> {

    private List<String> permissionList;
    private Context context;

    public PermissionRecyclerAdapter(Context context, List<String> permissionList) {
        this.context = context;
        this.permissionList = permissionList;
    }

    @Override
    public PermissionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_permission_info, parent, false);
        return new PermissionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PermissionViewHolder holder, int position) {
        holder.permissionName.setText(permissionList.get(position));
    }

    @Override
    public int getItemCount() {
        return (permissionList == null ? 0 : permissionList.size());
    }

    class PermissionViewHolder extends RecyclerView.ViewHolder {

        public TextView permissionName;

        public PermissionViewHolder(View itemView) {
            super(itemView);
            permissionName = (TextView) itemView.findViewById(R.id.tv_permission_name);
        }
    }
}
