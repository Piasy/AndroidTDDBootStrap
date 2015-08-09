package com.piasy.common.android.utils.screen;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;

import java.util.List;

/**
 * Created by guyacong on 2015/4/17.
 */
public class ScreenUtil {
    private final Context mContext;
    private final Canvas mCanvas;

    public ScreenUtil(@NonNull Context context) {
        mContext = context;
        mCanvas = new Canvas();
    }

    public int getScreenOrientation() {
        return mContext.getResources().getConfiguration().orientation;
    }

    public int dip2px(int dipValue) {
        float reSize = mContext.getResources().getDisplayMetrics().density;
        return (int) ((dipValue * reSize) + 0.5);
    }

    public int px2dip(int pxValue) {
        float reSize = mContext.getResources().getDisplayMetrics().density;
        return (int) ((pxValue / reSize) + 0.5);
    }

    public float sp2px(int spValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue,
                mContext.getResources().getDisplayMetrics());
    }

    public int getScreenWidth(@NonNull Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.widthPixels;
    }

    public int getScreenHeight(@NonNull Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.heightPixels;
    }

    public synchronized Bitmap getBitmapFromView(View view) {
        Bitmap b = Bitmap.createBitmap(
                view.getWidth(), view.getHeight(),
                Bitmap.Config.ARGB_8888);
        mCanvas.setBitmap(b);
        mCanvas.translate(-view.getScrollX(), -view.getScrollY());
        view.draw(mCanvas);
        return b;
    }

    public boolean isAppOnForeground(Context context) {
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

    public boolean isActivityOnForeground(Context context, String activityName) {
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

    public boolean isScreenOn() {
        PowerManager powerManager = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        return powerManager.isScreenOn();
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
