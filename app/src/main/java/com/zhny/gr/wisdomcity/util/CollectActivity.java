package com.zhny.gr.wisdomcity.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by czm on 2017/4/27.
 */

public class CollectActivity {

    private static List<Activity> activitys = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activitys.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activitys.remove(activity);
        activity.finish();
    }

    public static void clearAllActivity() {
        for (Activity activity : activitys) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

}
