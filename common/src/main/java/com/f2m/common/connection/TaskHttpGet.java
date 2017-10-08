package com.f2m.common.connection;

import android.os.AsyncTask;
import android.util.Log;

import com.f2m.common.utils.CommonUltil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sev_user on 10/17/2016.
 */
public class TaskHttpGet extends AsyncTask<Void, Void, Void> {

    private final String TAG = CommonUltil.buildTag(TaskHttpGet.class);

    private String urlString;
    private IOnTextResultCallback mCallback;
    private int mFlag;
    private int mResponseCode;
    private InputStream is;
    private URL mURL;
    private HttpURLConnection conn;

    private StringBuilder sb = new StringBuilder();
    private BufferedReader reader;
    private String line;
    private String mTextResult = null;

    public TaskHttpGet(String url, IOnTextResultCallback callback, int flag) {
        Log.d(TAG, "TaskHttpGet with URL: " + url);
        urlString = url;
        mCallback = callback;
        mFlag = flag; // This variable using to determine what request is handling
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            mURL = new URL(urlString);
            conn = (HttpURLConnection) mURL.openConnection();
            conn.setRequestMethod(ConnectionsManager.METHOD_GET);
            conn.setRequestProperty(ConnectionsManager.PROPERTY_USER_AGENT, ConnectionsManager.USER_AGENT_MOZILLA_50);

            mResponseCode = conn.getResponseCode();
            Log.d(TAG, "ResponseCode: " + conn.getResponseCode());
            if (mResponseCode == HttpURLConnection.HTTP_OK) {
                is = conn.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is));
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                mTextResult = sb.toString();
            }
        } catch (Exception e) {
            Log.d(TAG, e.getClass().toString() + " : " + e.getMessage());
        };
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        mCallback.onResult(mTextResult, mFlag);
    }
}
