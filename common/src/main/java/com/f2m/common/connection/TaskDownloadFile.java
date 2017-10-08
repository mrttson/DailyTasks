package com.f2m.common.connection;

import android.os.AsyncTask;
import android.util.Log;

import com.f2m.common.utils.CommonUltil;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by sev_user on 10/17/2016.
 */
public class TaskDownloadFile extends AsyncTask<Void, Void, Void> {

    private final String TAG = CommonUltil.buildTag(TaskDownloadFile.class);

    private String urlString;
    private InputStream isResult;
    private IOnFileRequestCallback mListener;
    private int mRequestFlag;

    private StringBuilder mResult = new StringBuilder();

    public TaskDownloadFile(String url, IOnFileRequestCallback listener, int flag) {
        urlString = url;
        mListener = listener;
        mRequestFlag = flag; // This variable using to determine what request is handling
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            URL mURL = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) mURL.openConnection();
            connection.connect();
            Log.d(TAG, "connection.getResponseCode return code [" +connection.getResponseCode()+ "]");
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                isResult = null;
            } else {
                isResult = connection.getInputStream();
            }
        } catch (Exception e) {
            Log.d(TAG, e.getClass().toString() + " : " + e.getMessage());
        } ;
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        mListener.onResult(isResult, mRequestFlag);
    }
}
