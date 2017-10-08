package com.f2m.common.base;

import android.app.Application;

import com.f2m.common.managers.AppManager;
import com.f2m.common.utils.CommonUltil;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;

/**
 * Created by sev_user on 10/15/2016.
 */
public abstract class BaseApplication extends Application{
    private static final String TAG = CommonUltil.buildTag(BaseApplication.class);

    public BaseApplication(){}

    @Override
    public void onCreate() {
        super.onCreate();

        // setup common application information
        AppManager.getInstance().setup(getApplicationContext());
        CommonUltil.copyAssetsToSDCard(getApplicationContext());
    }
}
