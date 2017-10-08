package com.f2m.common.permission;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.f2m.common.utils.CommonUltil;

/**
 * Created by sev_user on 10/15/2016.
 */
public class PermissionsManager {

    private static final String TAG = CommonUltil.buildTag(PermissionsManager.class);

    private PermissionsManager(){}

    public static final String KEY_PREVIOUS_ACTIVITY_INTENT = "KEY_PREVIOUS_ACTIVITY_INTENT";
    public static final String KEY_PERMISSION_ACTIVITY_INTENT = "KEY_PERMISSION_ACTIVITY_INTENT";
    public static final String KEY_ARRAY_REQUIRED_PERMISSIONS = "KEY_ARRAY_REQUIRED_PERMISSIONS";
    public static final int PERMISSIONS_REQUEST_ALL_PERMISSIONS = 1;
    public static final int REQUEST_FOR_APP = 0;

    private static String[] REQUIRED_PERMISSIONS = new String[] {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_SMS
    };

    private static int mRequestCode;
    private static String mFunctionOrAppName;

    public static boolean startPermissionActivity(Activity activity) {
        Log.d(TAG, "startPermissionActivity(Activity activity)");
        return startPermissionActivity(activity, REQUIRED_PERMISSIONS,
                RequestPermissionActivity.class);
    }

    public static boolean startPermissionActivity(Activity activity, String[] permissions) {
        Log.d(TAG, "startPermissionActivity(Activity activity, String[] permissions)");

        REQUIRED_PERMISSIONS = permissions;
        if(permissions == null || permissions.length == 0){
            return false;
        }
        return startPermissionActivity(activity, permissions,
                RequestPermissionActivity.class);
    }

    private static boolean startPermissionActivity(Activity activity, String[] requiredPermissions, Class<?> classActivityRequestPermission) {
        Log.d(TAG, "startPermissionActivity(Activity activity, String[] requiredPermissions, Class<?> classActivityRequestPermission)");
        return startPermissionActivity(activity, requiredPermissions, classActivityRequestPermission,
                    REQUEST_FOR_APP, activity.getResources().getString(activity.getApplicationInfo().labelRes));
    }

    private static boolean startPermissionActivity(Activity activity, String[] requiredPermissions, Class<?> classActivityRequestPermission, int requestCode, String functionOrAppName) {
        Log.d(TAG, "startPermissionActivity(Activity activity, String[] requiredPermissions, Class<?> classActivityRequestPermission, int requestCode, String functionOrAppName)");
        if (!PermissionsUtil.hasPermissions(activity, requiredPermissions)) {
            mRequestCode = requestCode;
            mFunctionOrAppName = functionOrAppName;

            final Intent intent = new Intent(activity, classActivityRequestPermission);
            intent.putExtra(KEY_PREVIOUS_ACTIVITY_INTENT, activity.getIntent());

            Bundle bPermissions = new Bundle();
            bPermissions.putStringArray(KEY_ARRAY_REQUIRED_PERMISSIONS, requiredPermissions);
            intent.putExtras(bPermissions);

            try {
                activity.startActivity(intent);
                activity.finish();
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

}
