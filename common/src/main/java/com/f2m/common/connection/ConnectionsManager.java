package com.f2m.common.connection;

import android.util.Log;

import com.f2m.common.utils.CommonUltil;
/**
 * Created by sev_user on 10/17/2016.
 */
public class ConnectionsManager {

    private static final  String TAG = CommonUltil.buildTag(ConnectionsManager.class);

    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";

    public static final String PROPERTY_USER_AGENT = "User-Agent";
    public static final String PROPERTY_CHARSET = "charset";

    public static final String USER_AGENT_MOZILLA_50 = "Mozilla/5.0";
    public static final String CHARSET_UTF8 = "UTF-8";

    private static ConnectionsManager mInstance = new ConnectionsManager();
    private ConnectionsManager() {}

    public static ConnectionsManager getInstance() {
        return mInstance;
    }

    public void getTextResultFromURL(String url, IOnTextResultCallback callback, int flag) {
        Log.d(TAG, "getTextResultFromURL");
        new TaskHttpGet(url, callback, flag).execute();
    }

    public void downloadFileFromURL(String url, IOnFileRequestCallback callback, int flag) {
        Log.d(TAG, "downloadFileFromURL");
        new TaskDownloadFile(url, callback, flag).execute();
    }
}
