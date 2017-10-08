package com.f2m.dailytasks.customize;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by sev_user on 10/2/2017.
 */

public class TextViewCapFirstLetter extends android.support.v7.widget.AppCompatTextView {
    public TextViewCapFirstLetter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        if(text != null && text.length() > 0)
            text = StringUtils.capitalize(text.toString());
        super.setText(text, type);
    }
}
