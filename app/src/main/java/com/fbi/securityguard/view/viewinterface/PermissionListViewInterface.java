package com.fbi.securityguard.view.viewinterface;

import java.util.List;

/**
 * Created by fubo on 2016/3/30 0030.
 */
public interface PermissionListViewInterface {

    void setToolbarTitle(String title);

    void updatePermissionList(List<String> permissionList);
}
