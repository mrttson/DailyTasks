package com.f2m.common.imageloader;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.f2m.common.utils.CommonUltil;


/**
 * Created by sev_user on 8/22/2016.
 */
public class SquaredImageView extends ImageView {

    private static final String TAG = CommonUltil.buildTag(SquaredImageView.class);

    public SquaredImageView(Context context) {
        super(context);
    }

    public SquaredImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        if (width < height)
            setMeasuredDimension(width, width);
        else setMeasuredDimension(height, height);

    }
}
