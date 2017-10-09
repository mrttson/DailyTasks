package com.f2m.common.utils;

import android.util.Base64;

/**
 * Created by sev_user on 10/9/2017.
 */

public class EncodeUtil {
    public static String encodeBase64(String input, int flag) {
        return Base64.encodeToString(input.getBytes(), flag);
    }

    public static String encodeBase64(String input) {
        return encodeBase64(input, Base64.DEFAULT);
    }

    public static String decodeBase64(String input, int flag) {
        return new String(Base64.decode(input, flag));
    }

    public static String decodeBase64(String input) {
        return decodeBase64(input, Base64.DEFAULT);
    }
}
