package com.f2m.dailytasks.activities.adapters;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sev_user on 10/5/2017.
 */

public class RepeatOptions {

    private static final String TEXT_OPTION_NO_REPEAT = "No Repeat";
    private static final String TEXT_OPTION_REPEAT_DAILY = "Daily";
    private static final String TEXT_OPTION_REPEAT_WEEKLY = "Weekly";
    private static final String TEXT_OPTION_REPEAT_MONTHLY = "Monthly";
    private static final String TEXT_OPTION_REPEAT_YEARLY = "Yearly";

    public static final RepeatOptions NO_REPEAT = new RepeatOptions(TEXT_OPTION_NO_REPEAT);
    public static final RepeatOptions REPEAT_DAILY = new RepeatOptions(TEXT_OPTION_REPEAT_DAILY);
    public static final RepeatOptions REPEAT_WEEKLY = new RepeatOptions(TEXT_OPTION_REPEAT_WEEKLY);
    public static final RepeatOptions REPEAT_MONTHLY = new RepeatOptions(TEXT_OPTION_REPEAT_MONTHLY);
    public static final RepeatOptions REPEAT_YEARLY = new RepeatOptions(TEXT_OPTION_REPEAT_YEARLY);

    public static final List<RepeatOptions> AllOptions;
    public static final List<RepeatOptions> DefaultOptions;

    static {
        AllOptions = new ArrayList<>();
        AllOptions.add(REPEAT_DAILY);
        AllOptions.add(REPEAT_WEEKLY);
        AllOptions.add(REPEAT_MONTHLY);
        AllOptions.add(REPEAT_YEARLY);
        AllOptions.add(NO_REPEAT);

        DefaultOptions = new ArrayList<>();
        DefaultOptions.add(NO_REPEAT);
    }

    private String mTitle;

    private RepeatOptions(String text){
        mTitle = text;
    }

    public String getTitle() {
        return mTitle;
    }
}
