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

package com.github.piasy.template.base;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import com.github.piasy.common.android.utils.screen.ScreenUtil;
import com.github.piasy.template.base.di.ActivityModule;
import com.github.piasy.template.base.di.AppComponent;
import com.github.piasy.template.base.di.IApplication;
import javax.inject.Inject;
import net.steamcrafted.loadtoast.LoadToast;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 *
 * Base Activity class.
 */
public abstract class BaseActivity extends FragmentActivity {

    private static final int MSG_WHAT_START_PROGRESS = 1000;
    private static final int MSG_WHAT_STOP_PROGRESS_SUCCESS = 1001;
    private static final int MSG_WHAT_STOP_PROGRESS_ERROR = 1002;
    @Inject
    protected Resources mResources;
    @Inject
    protected ScreenUtil mScreenUtil;
    private MemorySafeHandler mHandler;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        this.getApplicationComponent().inject(this);
        initializeInjector();
        mHandler = new MemorySafeHandler(new LoadToast(this).setTextColor(0x555555)
                .setTranslationY(mScreenUtil.getScreenHeight(this) / 2));
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopProgress(true);
    }

    /**
     * Get the Main Application component for dependency injection.
     *
     * @return {@link AppComponent}
     */
    protected AppComponent getApplicationComponent() {
        return ((IApplication) getApplication()).component();
    }

    /**
     * Initialize dependency injector.
     */
    protected abstract void initializeInjector();

    /**
     * Get an Activity module for dependency injection.
     *
     * @return {@link ActivityModule}
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    /**
     * Show progress feedback with default text hint.
     */
    public void showProgress() {
        showProgress("Loading");
    }

    /**
     * Show progress feedback with the specified text hint.
     *
     * @param message the specified text hint.
     */
    public void showProgress(final String message) {
        if (!TextUtils.isEmpty(message) && mHandler != null) {
            mHandler.post(() -> {
                final Message msg = new Message();
                msg.what = MSG_WHAT_START_PROGRESS;
                final Bundle bundle = new Bundle();
                bundle.putString("message", message);
                msg.setData(bundle);
                mHandler.sendMessage(msg);
            });
        }
    }

    /**
     * Stop the progress feedback.
     *
     * @param success whether the process succeeded or not.
     */
    public void stopProgress(final boolean success) {
        if (mHandler != null) {
            mHandler.postDelayed(() -> {
                if (success) {
                    mHandler.sendEmptyMessage(MSG_WHAT_STOP_PROGRESS_SUCCESS);
                } else {
                    mHandler.sendEmptyMessage(MSG_WHAT_STOP_PROGRESS_ERROR);
                }
            }, 500);
        }
    }

    /**
     * Instances of static inner classes do not hold an implicit
     * reference to their outer class.
     *
     * http://www.androiddesignpatterns.com/2013/01/inner-class-handler-memory-leak.html
     */
    private static class MemorySafeHandler extends Handler {
        private final LoadToast mLoadToast;
        private boolean mIsLoadToastShowing;

        public MemorySafeHandler(final LoadToast loadToast) {
            super();
            mLoadToast = loadToast;
        }

        @Override
        public void handleMessage(@NonNull final Message msg) {
            switch (msg.what) {
                case MSG_WHAT_START_PROGRESS:
                    if (mIsLoadToastShowing) {
                        return;
                    }
                    final String message = msg.getData().getString("message");
                    mLoadToast.setText(message);
                    mLoadToast.show();
                    mIsLoadToastShowing = true;
                    break;
                case MSG_WHAT_STOP_PROGRESS_ERROR:
                    if (mIsLoadToastShowing) {
                        mLoadToast.error();
                        mIsLoadToastShowing = false;
                    }
                    break;
                case MSG_WHAT_STOP_PROGRESS_SUCCESS:
                    if (mIsLoadToastShowing) {
                        mLoadToast.success();
                        mIsLoadToastShowing = false;
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
