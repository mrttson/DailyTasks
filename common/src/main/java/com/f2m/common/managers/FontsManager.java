package com.f2m.common.managers;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;

/**
 * Created by sev_user on 10/17/2016.
 */
public class FontsManager {

    public static final String FONT_MECHANII = "MECHANII.ttf";
    public static final String FONT_SEAGULL = "Seagull.ttf";

    private Context mContext;
    private AssetManager mAssetManager;

    public FontsManager(Context context){
        mContext = context;
        mAssetManager = mContext.getAssets();
    }

    public Typeface createFromAsset(String fontPath) {
        return Typeface.createFromAsset(mAssetManager, fontPath);
    }

}
