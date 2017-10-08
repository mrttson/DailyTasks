package com.f2m.dailytasks.fragments;

import android.support.v4.app.Fragment;
import com.f2m.common.utils.CommonUltil;


public class FragmentsManager {

    private static final String TAG = CommonUltil.buildTag(FragmentsManager.class);

    private static FragmentsManager mInstance = null;

    public static FragmentsManager getInstance() {
        if (mInstance == null) {
            mInstance = new FragmentsManager();
        }
        return mInstance;
    }

    public enum FrType {
        FRG_LIST_TASK,
    }

    public Fragment getFragment(FrType type) {
        Fragment ft = null;

        switch (type) {
            case FRG_LIST_TASK:
                ft = new ListTasksFragment();
                break;
        }
        return ft;
    }
}
