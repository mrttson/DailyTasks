package com.f2m.common.managers;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.f2m.common.utils.CommonUltil;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import net.sqlcipher.database.SQLiteDatabase;

import java.io.File;

/**
 * Created by sev_user on 10/15/2016.
 */
public class AppManager {

    private static final String TAG = CommonUltil.buildTag(AppManager.class);

    private static AppManager mInstance = new AppManager();
    private FontsManager mFontsManager;
    private SoundManager mSoundManager;
    private SharedPrefManager mSharedPrefManager;
    private Context mAppContext = null;

    private AppManager(){}

    public void setup(Context context){
        mAppContext = context;
        mFontsManager = new FontsManager(mAppContext);
        mSoundManager = new SoundManager(mAppContext);
        mSharedPrefManager = new SharedPrefManager(mAppContext, true);
        setupPicasso();
        setupSQLCipher();
    }

    private void setupSQLCipher() {
        Log.d(TAG, "setupSQLCipher");
        SQLiteDatabase.loadLibs(mAppContext);
    }

    private void setupPicasso() {
        // setup for lazy loading image with picasso
        Picasso.Builder builder = new Picasso.Builder(mAppContext);
        builder.downloader(new OkHttpDownloader(mAppContext,Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);
    }

    public static AppManager getInstance() {
        return mInstance;
    }

    public Context getAppContext() {
        return mAppContext;
    }

    public String getPackageName() {
        return mAppContext.getPackageName();
    }

    public String getAppDataLocation() {
        String location = Environment.getExternalStorageDirectory() + "/";

        // add '.' before package name to hide folder from FileManager and Gallery
        location += "." + getPackageName();
        return location;
    }

    public FontsManager getFontsManager() {return mFontsManager;}
    public SoundManager getSoundManager() {return mSoundManager;}
    public SharedPrefManager getSharedPrefManager() {return mSharedPrefManager;}

    public native String getApplicationEncryptKey();
    public native String getApplicationFirebaseKey();
    public native String getApplicationSQLiteEncryptKey();

    static {
        System.loadLibrary("Application");
    }
}
