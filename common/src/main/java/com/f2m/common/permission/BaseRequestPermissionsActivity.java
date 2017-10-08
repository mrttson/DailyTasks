package com.f2m.common.permission;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.f2m.common.utils.CommonUltil;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by sev_user on 10/15/2016.
 */
public abstract class BaseRequestPermissionsActivity extends Activity {

    private final String TAG = CommonUltil.buildTag(BaseRequestPermissionsActivity.class);

    public static final int ACTIVITY_FLAG_REQUEST_PERMISSION_SETTING_RESULT_CODE = 199;
    public static final int GET_WRITE_SETTINGS_PERMISSION = 299;
    public static final int GET_DRAW_OVERLAYS_PERMISSION = 399;

    protected abstract String[] getRequiredPermissions();
    private Intent mPreviousActivityIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        mPreviousActivityIntent = (Intent) getIntent().getExtras().get(PermissionsManager.KEY_PREVIOUS_ACTIVITY_INTENT);

        requestPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (permissions != null && permissions.length > 0 && isAllGranted(permissions, grantResults)) {
            Log.d(TAG, "startPreviousActivity() - permission is granted");
            startPreviousActivity();
        } else {
            Log.d(TAG, "int requestCode, String permissions[], int[] grantResults " + requestCode + " / " /*+ permissions.toString() + " / " + grantResults*/);
            finishAffinity();
        }
    }

    private void startPreviousActivity() {
        finish();
        mPreviousActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        mPreviousActivityIntent.putExtra(PermissionsManager.KEY_PERMISSION_ACTIVITY_INTENT, true);
        try {
            startActivity(mPreviousActivityIntent);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }

    }

    private boolean isAllGranted(String permissions[], int[] grantResult) {
        for (int i = 0; i < permissions.length; i++) {
            if (grantResult[i] != PackageManager.PERMISSION_GRANTED
                    && isPermissionRequired(permissions[i])) {
                return false;
            }
        }
        return true;
    }

    private boolean isPermissionRequired(String p) {
        return Arrays.asList(getRequiredPermissions()).contains(p);
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            final ArrayList<String> unsatisfiedPermissions = new ArrayList<>();
            for (String permission : getRequiredPermissions()) {
                if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    unsatisfiedPermissions.add(permission);
                }
            }
            if (unsatisfiedPermissions.size() != 0) {
                requestPermissions(
                        unsatisfiedPermissions.toArray(new String[unsatisfiedPermissions.size()]),
                        PermissionsManager.PERMISSIONS_REQUEST_ALL_PERMISSIONS);
            }
        } else {
            startPreviousActivity();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (ACTIVITY_FLAG_REQUEST_PERMISSION_SETTING_RESULT_CODE == requestCode) {
            if (getRequiredPermissions() == null) {
                return;
            }

            if (PermissionsUtil.hasPermissions(this, getRequiredPermissions())) {
                startPreviousActivity();
            } else {
                requestPermissions();
            }
        } else if (GET_WRITE_SETTINGS_PERMISSION == requestCode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.System.canWrite(this)) {
                startPreviousActivity();
            } else {
                finishAffinity();
            }
        } else if (GET_DRAW_OVERLAYS_PERMISSION == requestCode) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
                startPreviousActivity();
            } else {
                finishAffinity();
            }
        }
    }
}

