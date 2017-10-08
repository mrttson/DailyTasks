package com.f2m.dailytasks.activities.adapters;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sev_user on 10/4/2017.
 */

public class ReminderOptions {

    private static final String TEXT_OPTION_NO_REMIND = "No Remind";
    private static final String TEXT_OPTION_ON_TIME = "On Time";
    private static final String TEXT_OPTION_BEFORE_10_MINUTES = "Before 10 minutes";
    private static final String TEXT_OPTION_BEFORE_1_HOUR = "Before 1 hour";
    private static final String TEXT_OPTION_SPECIFY_TIME = "Specify time";

    public static final ReminderOptions OPTION_NO_REMIND = new ReminderOptions(TEXT_OPTION_NO_REMIND);
    public static final ReminderOptions OPTION_ON_TIME = new ReminderOptions(TEXT_OPTION_ON_TIME);
    public static final ReminderOptions OPTION_BEFORE_10_MINUTES = new ReminderOptions(TEXT_OPTION_BEFORE_10_MINUTES);
    public static final ReminderOptions OPTION_BEFORE_1_HOUR = new ReminderOptions(TEXT_OPTION_BEFORE_1_HOUR);
    //public static final ReminderOptions OPTION_SPECIFY_TIME = new ReminderOptions(TEXT_OPTION_SPECIFY_TIME);

    public static final List<ReminderOptions> AllOptions;
    public static final List<ReminderOptions> DefaultOptions;
    static {
        AllOptions = new ArrayList<>();
        AllOptions.add(OPTION_ON_TIME);
        AllOptions.add(OPTION_BEFORE_10_MINUTES);
        AllOptions.add(OPTION_BEFORE_1_HOUR);
        //AllOptions.add(OPTION_SPECIFY_TIME);
        AllOptions.add(OPTION_NO_REMIND);

        DefaultOptions = new ArrayList<>();
        DefaultOptions.add(OPTION_NO_REMIND);
    }

    private String mTitle;

    private ReminderOptions(String text) {
        mTitle = text;
    }

    public String getTitle() {
        return mTitle;
    }
}
