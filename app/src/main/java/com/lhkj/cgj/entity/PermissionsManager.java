package com.lhkj.cgj.entity;

import android.Manifest;
import android.app.Activity;

import pub.devrel.easypermissions.EasyPermissions;


/**
 * Created by 浩琦 on 2017/7/10.
 * EasyPermissions
 */

public class PermissionsManager {

    private String[] perms = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    private static PermissionsManager permissionsManager = new PermissionsManager();

    public static PermissionsManager getPermissionsManager() {
        return permissionsManager;
    }

    private PermissionsManager() {

    }

    public void signManager(Activity activity) {
        if (EasyPermissions.hasPermissions(activity, perms)) {
            // Already have permission, do the thing
            // ...
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(activity, "油品惠运行需要权限，拒绝可能导致有些功能无法正常运行", 0, perms);
        }
    }


}
