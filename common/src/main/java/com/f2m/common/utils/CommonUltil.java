package com.f2m.common.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.f2m.common.managers.AppManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by sev_user on 8/20/2016.
 */
public class CommonUltil {

    private static final String TAG = buildTag(CommonUltil.class);

    public static int convertDPtoPX(Context context,int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static String buildTag(Class c) {
        return (Constants.F2M_DEBUG_PREFIX + c.getName());
    }

    public static void updateListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static void toastString(String str) {
        Toast.makeText(AppManager.getInstance().getAppContext(), str, Toast.LENGTH_SHORT).show();
    }

    public static void copyAssetsToSDCard(Context context) {
        AssetManager assetManager = context.getAssets();

        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        for(String filename : files) {
//            Log.d(TAG, "Copying file: " + filename);
            InputStream in;
            OutputStream out;
            File fOut = new File(AppManager.getInstance().getAppDataLocation() + "/" + filename);
            if (fOut.exists() && fOut.isFile())
                fOut.delete();
            try {
                // Create parent folder if not exist
                if (!fOut.getParentFile().exists())
                    fOut.getParentFile().mkdirs();

                in = assetManager.open(filename);
                out = new FileOutputStream(fOut);

                // Copy InputStream to OutputStream
                byte[] buffer = new byte[1024];
                int read;
                while((read = in.read(buffer)) != -1){
                    out.write(buffer, 0, read);
                }
                in.close();
                out.flush();
                out.close();
            } catch(Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

    public static String getStringFromFile (String filePath) {
        String ret = null;
        try {
            File fl = new File(filePath);
            FileInputStream is = new FileInputStream(fl);
            ret = convertStreamToString(is);
            is.close();
        } catch (Exception e) {
            Log.d(TAG, "Exception ["+e.getClass()+"] occurs on [getStringFromFile]: " + e.getMessage());
        }
        return ret;
    }

    public static void copyFile(File src, File dst) throws IOException {
        Log.d(TAG, "copyFile");
        if (!src.exists()) {
            Log.d(TAG, "src is not exist");
            return;
        }

        if (!dst.exists()) {
            dst.getParentFile().mkdirs();
            dst.createNewFile();
        }


        InputStream in = new FileInputStream(src);
        try {
            OutputStream out = new FileOutputStream(dst);
            try {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }

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
