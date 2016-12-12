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

package com.github.piasy.bootstrap;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.answers.CustomEvent;
import com.github.piasy.bootstrap.base.android.BaseActivity;
import com.github.piasy.bootstrap.features.splash.SplashActivity;
import org.threeten.bp.Duration;
import org.threeten.bp.ZonedDateTime;

/**
 * Created by Piasy{github.com/Piasy} on 16/4/13.
 */
public abstract class BootstrapActivity<C> extends BaseActivity<C> {
    private ZonedDateTime mResume;
    private ZonedDateTime mPause;

    @Override
    protected void onResume() {
        super.onResume();
        if (isTrack()) {
            mResume = ZonedDateTime.now();
            Answers.getInstance()
                    .logCustom(new CustomEvent("Activity onResume")
                            .putCustomAttribute("Activity", this.getClass().getName())
                            .putCustomAttribute("Time", currentTime(mResume)));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isTrack()) {
            mPause = ZonedDateTime.now();
            Answers.getInstance()
                    .logCustom(new CustomEvent("Activity onPause")
                            .putCustomAttribute("Activity", this.getClass().getName())
                            .putCustomAttribute("Time", currentTime(mPause))
                            .putCustomAttribute("Duration",
                                    Duration.between(mResume, mPause).toMinutes()));
        }
    }

    private boolean isTrack() {
        return "release".equals(BuildConfig.BUILD_TYPE) && !(this instanceof SplashActivity);
    }

    private String currentTime(final ZonedDateTime dateTime) {
        return String.format("%d-%d-%d %d:%d:%d", dateTime.getYear(), dateTime.getMonthValue(),
                dateTime.getDayOfMonth(), dateTime.getHour(), dateTime.getMinute(),
                dateTime.getSecond());
    }
}