package com.f2m.common.permission;

import com.f2m.common.utils.CommonUltil;

/**
 * Created by sev_user on 10/15/2016.
 */
public class RequestPermissionActivity extends BaseRequestPermissionsActivity {

    private static final String TAG = CommonUltil.buildTag(RequestPermissionActivity.class);

    private static String[] mRequiredPermissions;

    @Override
    protected String[] getRequiredPermissions() {
        mRequiredPermissions = getIntent().
                getExtras().getStringArray(PermissionsManager.KEY_ARRAY_REQUIRED_PERMISSIONS);
        return mRequiredPermissions;
    }

    @Override
    public void finish() {
        super.finish();
        this.overridePendingTransition(0,0);
    }
}
