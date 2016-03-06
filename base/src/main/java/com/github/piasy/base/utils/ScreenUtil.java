/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Piasy
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

package com.github.piasy.base.utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Display;

/**
 * Created by Piasy{github.com/Piasy} on 2015/4/17.
 *
 * Screen related utility class.
 */
public class ScreenUtil {
    private final Context mContext;

    /**
     * Create instance with the given app {@link Context}.
     *
     * @param context the given app {@link Context}.
     */
    public ScreenUtil(@NonNull final Context context) {
        mContext = context;
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
}
