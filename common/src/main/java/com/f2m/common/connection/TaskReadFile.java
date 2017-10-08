package com.f2m.common.connection;

import android.os.AsyncTask;
import android.util.Log;

import com.f2m.common.utils.CommonUltil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by sev_user on 10/17/2016.
 */
public class TaskReadFile extends AsyncTask<Void, Void, Void> {

    private final String TAG = CommonUltil.buildTag(TaskReadFile.class);

    private String mFilePath;
    private IOnTextResultCallback mCallback;
    private int mRequestFlag;

    private StringBuilder sb = new StringBuilder();
    private String mResult = null;

    public TaskReadFile(String filePath, IOnTextResultCallback callback, int flag) {
        mFilePath = filePath;
        mCallback = callback;
        mRequestFlag = flag;
    }

    @Override
    protected Void doInBackground(Void... params) {

        File file = new File(mFilePath);
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                sb.append(line.trim());
            }
            br.close();
            mResult = sb.toString();
        } catch (Exception e) {
            Log.d(TAG, e.getClass().toString() + " : " + e.getMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        mCallback.onResult(mResult, mRequestFlag);
    }
}
