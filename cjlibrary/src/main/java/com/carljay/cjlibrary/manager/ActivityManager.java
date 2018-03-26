package com.carljay.cjlibrary.manager;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 豪杰 on 2016/12/19.
 */

public class ActivityManager {
    private static List<Activity> mActivities = new ArrayList<>();

    public static void addActivity(Activity activity){
        mActivities.add(activity);
    }
    public static void removeActivity(Activity activity){
        mActivities.remove(activity);
    }
    public static Activity getTopActivity(){
        return mActivities.get(mActivities.size() - 1);
    }
    public static void exitAllActivity(){
        for (Activity a :mActivities) {
            a.finish();
        }
    }
}
