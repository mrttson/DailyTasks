package com.f2m.common.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.f2m.common.permission.PermissionsManager;
import com.f2m.common.utils.CommonUltil;

/**
 * Created by sev_user on 10/15/2016.
 */
public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = CommonUltil.buildTag(BaseActivity.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (PermissionsManager.startPermissionActivity(this)) {
                finish();
                return;
            }
        }
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (PermissionsManager.startPermissionActivity(this)) {
                finish();
                return;
            }
        }
    }
}
