package com.piasy.common.android.utils.screen;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.widget.TextView;

import java.util.List;

/**
 * Created by guyacong on 2015/4/17.
 */
public class ScreenUtil {
    protected static Context mContext = null;

    private ScreenUtil() {
        //no instance
    }

    public static void setContext(Context context) {
        mContext = context;
    }

    public static int getScreenOrientation() {
        return mContext.getResources().getConfiguration().orientation;
    }

    public static int dip2px(int dipValue) {
        float reSize = mContext.getResources().getDisplayMetrics().density;
        return (int) ((dipValue * reSize) + 0.5);
    }

    public static int px2dip(int pxValue) {
        float reSize = mContext.getResources().getDisplayMetrics().density;
        return (int) ((pxValue / reSize) + 0.5);
    }

    public static float sp2px(int spValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue,
                mContext.getResources().getDisplayMetrics());
    }

    public static int getScreenWidth(Activity activity) {
        if (activity != null) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            return metrics.widthPixels;
        }

        return 480;
    }

    public static int getScreenHeight(Activity activity) {
        if (activity != null) {
            Display display = activity.getWindowManager().getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            return metrics.heightPixels;
        }
        return 800;
    }

    public static Bitmap getBitmapFromView(View view) {

        Bitmap b = Bitmap.createBitmap(
                view.getWidth(), view.getHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        c.translate(-view.getScrollX(), -view.getScrollY());
        view.draw(c);
        return b;
    }

    public static boolean isAppOnForeground(Context context) {
        String packageName = context.getPackageName();
        ActivityManager activityManager = ((ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            // app on stack top
            if (packageName.equals(tasksInfo.get(0).topActivity
                    .getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean isActivityOnForeground(Context context, String activityName) {

        ActivityManager activityManager = ((ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE));
        List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            // app on stack top
            if (tasksInfo.get(0).topActivity.getClassName().endsWith(activityName)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isScreenOn(Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        return powerManager.isScreenOn();
    }


    public static int getActionBarHeight(Context context) {
        TypedValue tv = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            return TypedValue.complexToDimensionPixelSize(tv.data,
                    context.getResources().getDisplayMetrics());
        }
        return 0;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getMiddleAppY(Activity activity) {
        return (getScreenHeight(activity) - getActionBarHeight(activity) - getStatusBarHeight(activity)) / 2;
    }

    /**
     * using dp to constrain text to be shown on a TextView
     *
     * @param maxDp  max dp
     * @param suffix suffix appending to string if exceed maxDp
     * @return string be constrained, could be used by TextView.setText directly
     */
    public static String getProperTextForTv(TextView viewToSetText, String stringToBeSet, int maxDp, String suffix) {
        Paint textPaint = viewToSetText.getPaint();
        float scale = viewToSetText.getResources().getDisplayMetrics().density;
        float width = textPaint.measureText(stringToBeSet);
        int dp = (int) (width / scale + 0.5f);
        if (dp <= maxDp) {
            return stringToBeSet;
        }

        String ret = stringToBeSet.substring(0, (int) ((double) stringToBeSet.length() * maxDp / dp));
        width = textPaint.measureText(ret);
        dp = (int) (width / scale + 0.5f);
        while (dp > maxDp) {
            ret = ret.substring(0, ret.length() - 1);
            width = textPaint.measureText(ret);
            dp = (int) (width / scale + 0.5f);
        }
        return ret + suffix;
    }
}
