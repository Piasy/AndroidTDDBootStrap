/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Piasy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.piasy.common.android.utils.screen;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Build;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import java.util.List;

/**
 * Created by Piasy{github.com/Piasy} on 2015/4/17.
 *
 * Screen related utility class.
 */
public class ScreenUtil {
    private final Context mContext;
    private final Canvas mCanvas;

    /**
     * Create instance with the given app {@link Context}.
     *
     * @param context the given app {@link Context}.
     */
    public ScreenUtil(@NonNull final Context context) {
        mContext = context;
        mCanvas = new Canvas();
    }

    /**
     * Get the screen orientation. {@link android.content.res.Configuration.ORIENTATION_PORTRAIT}
     * or {@link android.content.res.Configuration.ORIENTATION_LANDSCAPE}.
     *
     * @return the int value of screen orientation.
     */
    public int getScreenOrientation() {
        return mContext.getResources().getConfiguration().orientation;
    }

    /**
     * Convert the dip value into px value.
     *
     * @param dipValue the given dip value.
     * @return the corresponding px value.
     */
    public int dip2px(final int dipValue) {
        final float reSize = mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * reSize + 0.5);
    }

    /**
     * Convert the px value into dip value.
     *
     * @param pxValue the given px value.
     * @return the corresponding dip value.
     */
    public int px2dip(final int pxValue) {
        final float reSize = mContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / reSize + 0.5);
    }

    /**
     * Convert the sp value into px value.
     *
     * @param spValue the given sp value.
     * @return the corresponding px value.
     */
    public float sp2px(final int spValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue,
                mContext.getResources().getDisplayMetrics());
    }

    /**
     * Get the screen width of the given {@link Activity}.
     *
     * @param activity the {@link Activity} to measure.
     * @return the Activity's screen width.
     */
    public int getScreenWidth(@NonNull final Activity activity) {
        final Display display = activity.getWindowManager().getDefaultDisplay();
        final DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.widthPixels;
    }

    /**
     * Get the screen width of the given {@link Activity}.
     *
     * @param activity the {@link Activity} to measure.
     * @return the Activity's screen height.
     */
    public int getScreenHeight(@NonNull final Activity activity) {
        final Display display = activity.getWindowManager().getDefaultDisplay();
        final DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.heightPixels;
    }

    /**
     * Draw a {@link View} into a {@link Bitmap}, the caller should recycle the Bitmap at the
     * proper time.
     *
     * @param view the {@link View} to be drawn.
     * @return the {@link Bitmap} contains the {@link View}'s content.
     */
    public Bitmap view2Bitmap(@NonNull final View view) {
        final Bitmap b =
                Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        synchronized (this) {
            mCanvas.setBitmap(b);
            mCanvas.translate(-view.getScrollX(), -view.getScrollY());
            view.draw(mCanvas);
        }
        return b;
    }

    /**
     * Check whether the app is on foreground.
     *
     * @param context the app {@link Context} to be checked.
     * @return {@code true} if is on foreground.
     */
    public boolean isAppOnForeground(@NonNull final Context context) {
        final String packageName = context.getPackageName();
        final ActivityManager activityManager =
                ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        final List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        // app on stack top
        return !tasksInfo.isEmpty() &&
                packageName.equals(tasksInfo.get(0).topActivity.getPackageName());
    }

    /**
     * Check whether the {@link Activity} is on foreground.
     *
     * @param context the app {@link Context} to be check.
     * @param activityName the name of the {@link Activity} to be checked.
     * @return {@code true} if is on foreground.
     */
    public boolean isActivityOnForeground(@NonNull final Context context,
            @NonNull final String activityName) {
        final ActivityManager activityManager =
                ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        final List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        // app on stack top
        return !tasksInfo.isEmpty() &&
                tasksInfo.get(0).topActivity.getClassName().endsWith(activityName);
    }

    /**
     * Check whether the device screen is on (is in Interactive mode).
     *
     * @return {@code true} if the device screen is on.
     */
    public boolean isScreenOn() {
        final PowerManager powerManager =
                (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH ?
                powerManager.isInteractive() : powerManager.isScreenOn();
    }

    /**
     * Get the app status bar height.
     *
     * @return the app status bar height.
     */
    public int getStatusBarHeight() {
        final int resourceId =
                mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return resourceId > 0 ? mContext.getResources().getDimensionPixelSize(resourceId) : 0;
    }
}
