package com.f2m.common.permission;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;
import android.net.Uri;

import com.f2m.common.managers.AppManager;

import java.util.ArrayList;

/**
 * Created by sev_user on 10/15/2016.
 */
public class PermissionsUtil {
    private final static String TAG = "PermissionsUtil";

    private static ArrayList<String> mCheckedPermission = new ArrayList<>();

    public static boolean hasPermission(Context context, String permission) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
            return true;
        }
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean hasPermissions(Context context, final String[] permissions) {
        boolean hasPermission = true;
        for (final String permission : permissions) {
            if (!hasPermission(context, permission)) {
                hasPermission =  false;
            }
        }
        return hasPermission;
    }

    public static ArrayList<PermissionGroupInfo> getPermissionGroups(final Activity activity, ArrayList<String> requestedPermissions) {
        ArrayList<PermissionGroupInfo> permissionGroups = new ArrayList<>();
        try {
            PackageManager pm = activity.getPackageManager();
            for (String permission : requestedPermissions) {
                PermissionInfo pi = pm.getPermissionInfo(permission, PackageManager.GET_PERMISSIONS);
                PermissionGroupInfo pgi = pm.getPermissionGroupInfo(pi.group, PackageManager.GET_PERMISSIONS);
                String label = activity.getResources().getString(pgi.labelRes);
                boolean hasDuplicatedGroup = false;
                for (PermissionGroupInfo permissionGroup : permissionGroups) {
                    String haslabel = activity.getResources().getString(permissionGroup.labelRes);
                    if (haslabel != null && haslabel.equals(label)) {
                        hasDuplicatedGroup = true;
                        break;
                    }
                }
                if(!hasDuplicatedGroup) {
                    permissionGroups.add(pgi);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return permissionGroups;
    }

    public static String getPermissionLabel(String permission) {
        PackageManager pm = AppManager.getInstance().getAppContext().getPackageManager();

        String label = "";

        PermissionInfo pi = null;
        try {
            pi = pm.getPermissionInfo(permission, PackageManager.GET_PERMISSIONS);
            PermissionGroupInfo pgi = pm.getPermissionGroupInfo(pi.group, PackageManager.GET_PERMISSIONS);
            label = AppManager.getInstance().getAppContext().getResources().getString(pgi.labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return label;
    }

    public static void startPermissionSettingActivity(Activity activity) {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        try {
            activity.startActivityForResult(intent,
                    BaseRequestPermissionsActivity.ACTIVITY_FLAG_REQUEST_PERMISSION_SETTING_RESULT_CODE);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }
}
