package com.f2m.common.connection;

import android.content.Context;
import android.util.Log;

import com.f2m.common.utils.Constants;
import org.json.JSONObject;
import java.io.InputStream;

/**
 * Created by sev_user on 7/25/2016.
 */
public class JSONFactory {

    private static final String TAG = Constants.F2M_DEBUG_PREFIX + JSONFactory.class.getName();

    private static JSONFactory mInstance = new JSONFactory();
    private JSONFactory() {}

    public static JSONFactory getInstance() {
        return mInstance;
    }

    public JSONObject parseFromString(String json) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (Exception e) {
            Log.d(TAG, e.getClass().getName() + " : " + e.getMessage());
        }

        return jsonObject;
    }

    public JSONObject parseFromFile(Context context, String filePath) {
        JSONObject jsonObject = null;
        try {
            InputStream is = context.getAssets().open(filePath);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonObject = new JSONObject(new String(buffer, "UTF-8"));
        } catch (Exception e) {
            Log.d(TAG, e.getClass().getName() + " : " + e.getMessage());
        }

        return jsonObject;
    }
}
