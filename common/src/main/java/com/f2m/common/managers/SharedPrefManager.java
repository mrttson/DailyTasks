package com.f2m.common.managers;

import android.content.Context;
import android.content.SharedPreferences;
import com.f2m.common.utils.CommonUltil;
import com.f2m.common.utils.EncodeUtil;

/**
 * Created by sev_user on 9/29/2017.
 */

public class SharedPrefManager {

    public static final String SHARED_PREF_KEY_APP = "com.f2m.Dailytasks";
    public static final String SHARED_PREF_KEY_SAVED_USER_AVATAR = "com.f2m.Dailytasks.savedavatar";

    private Context mContext;
    private boolean isEnableEncode = false;

    public SharedPrefManager(Context context, boolean isEnableEncode) {
        mContext = context;
        this.isEnableEncode = isEnableEncode;
    }

    public String getSharedPrefName() {
        return SHARED_PREF_KEY_APP;
    }
    private SharedPreferences getSharedPref() {
        return mContext.getSharedPreferences(getSharedPrefName(), Context.MODE_PRIVATE);
    }

    private  SharedPreferences.Editor getSharedPrefEditor() {
        return getSharedPref().edit();
    }

    private void putEncodedString(String key, String encoded) {
        SharedPreferences.Editor editor = getSharedPrefEditor();
        editor.putString(key, encoded);
        editor.apply();
    }

    public void putInt(String key, int value) {
        if (isEnableEncode) {
            String sValue = Integer.toString(value);
            String encoded = EncodeUtil.encodeBase64(sValue);
            putEncodedString(key, encoded);
        } else {
            SharedPreferences.Editor editor = getSharedPrefEditor();
            editor.putInt(key, value);
            editor.apply();
        }
    }

    public int getInt(String key, int defValue) {
        if (isEnableEncode) {
            String value = getSharedPref().getString(key, null);
            if (value == null)
                return defValue;

            try {
                return Integer.parseInt(EncodeUtil.encodeBase64(value));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return defValue;
            }
        } else {
            return getSharedPref().getInt(key, defValue);
        }
    }

    public void putString(String key, String value) {
        if (isEnableEncode) {
            String encoded = EncodeUtil.encodeBase64(value);
            putEncodedString(key, encoded);
        } else {
            SharedPreferences.Editor editor = getSharedPrefEditor();
            editor.putString(key, value);
            editor.apply();
        }
    }

    public String getString(String key, String defValue) {
        String value = getSharedPref().getString(key, null);
        if (value == null)
            return defValue;

        if (isEnableEncode)
            return EncodeUtil.decodeBase64(value);

        return value;
    }

    public void putBoolean(String key, boolean value) {
        if(isEnableEncode) {
            String sValue = Boolean.toString(value);
            String encoded = EncodeUtil.encodeBase64(sValue);
            putEncodedString(key, encoded);
        } else {
            SharedPreferences.Editor editor = getSharedPrefEditor();
            editor.putBoolean(key, value);
            editor.apply();
        }
    }

    public boolean getBoolean(String key, boolean defValue) {
        if (isEnableEncode) {
            String value = getSharedPref().getString(key, null);
            if (value == null
                    || (!value.toLowerCase().equals("true") && !value.toLowerCase().equals("false")))
                return defValue;
            return Boolean.parseBoolean(value);
        } else {
            return getSharedPref().getBoolean(key, defValue);
        }
    }
}
