package com.f2m.dailytasks.activities.adapters;

import android.support.annotation.DrawableRes;

import com.f2m.dailytasks.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sev_user on 10/6/2017.
 */

public class EventTypeOptions {

    private static final String TEXT_TYPE_DEFAULT = "Default";
    private static final String TEXT_TYPE_IMPORTANT = "Important";
    private static final String TEXT_TYPE_BIRTHDAY = "Birthday";
    private static final String TEXT_TYPE_CLEAN_UP = "Clean up";

    private static final int ICON_TYPE_DEFAULT = R.drawable.ic_default;
    private static final int ICON_TYPE_IMPORTANT = R.drawable.ic_important;
    private static final int ICON_TYPE_BIRTHDAY = R.drawable.ic_birthday;
    private static final int ICON_TYPE_CLEAN_UP = R.drawable.ic_clean_up;

    public static final EventTypeOptions TYPE_DEFAULT = new EventTypeOptions(TEXT_TYPE_DEFAULT, ICON_TYPE_DEFAULT);
    public static final EventTypeOptions TYPE_IMPORTANT = new EventTypeOptions(TEXT_TYPE_IMPORTANT, ICON_TYPE_IMPORTANT);
    public static final EventTypeOptions TYPE_BIRTHDAY = new EventTypeOptions(TEXT_TYPE_BIRTHDAY, ICON_TYPE_BIRTHDAY);
    public static final EventTypeOptions TYPE_CLEAN_UP = new EventTypeOptions(TEXT_TYPE_CLEAN_UP, ICON_TYPE_CLEAN_UP);

    public static final List<EventTypeOptions> AllOptions;
    static {
        AllOptions = new ArrayList<>();
        AllOptions.add(TYPE_DEFAULT);
        AllOptions.add(TYPE_IMPORTANT);
        AllOptions.add(TYPE_BIRTHDAY);
        AllOptions.add(TYPE_CLEAN_UP);
    }

    private String mTitle;
    private int mIcon = 0;

    private EventTypeOptions(String text, @DrawableRes int icon){
        mTitle = text;
        mIcon = icon;
    }

    public String getTitle() {
        return mTitle;
    }

    public int getIcon() {
        return mIcon;
    }
}
