package com.piasy.template.base;

import com.piasy.common.android.utils.screen.ScreenUtil;
import com.piasy.template.base.di.ActivityModule;
import com.piasy.template.base.di.AppComponent;
import com.piasy.template.base.di.IApplication;

import net.steamcrafted.loadtoast.LoadToast;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;

import java.lang.ref.WeakReference;

import javax.inject.Inject;


/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
public abstract class BaseActivity extends FragmentActivity {

    private static final int MSG_WHAT_START_PROGRESS = 1000;
    private static final int MSG_WHAT_STOP_PROGRESS_SUCCESS = 1001;
    private static final int MSG_WHAT_STOP_PROGRESS_ERROR = 1002;
    protected final MemorySafeHandler mHandler = new MemorySafeHandler(this);
    @Inject
    protected Resources mResources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getApplicationComponent().inject(this);
        initializeInjector();
        super.onCreate(savedInstanceState);
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
     * Get an Activity module for dependency injection.
     *
     * @return {@link ActivityModule}
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    protected abstract void initializeInjector();

    @Override
    protected void onStop() {
        super.onStop();
        stopProgress(true);
    }

    public void stopProgress(final boolean success) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (success) {
                    mHandler.sendEmptyMessage(MSG_WHAT_STOP_PROGRESS_SUCCESS);
                } else {
                    mHandler.sendEmptyMessage(MSG_WHAT_STOP_PROGRESS_ERROR);
                }
            }
        }, 500);
    }

    public void showProgress(final String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = MSG_WHAT_START_PROGRESS;
                Bundle bundle = new Bundle();
                bundle.putString("message", message);
                msg.setData(bundle);
                mHandler.sendMessage(msg);
            }
        });
    }

    public void showProgress() {
        showProgress("Loading");
    }

    /**
     * Instances of static inner classes do not hold an implicit
     * reference to their outer class.
     *
     * http://www.androiddesignpatterns.com/2013/01/inner-class-handler-memory-leak.html
     */
    private static class MemorySafeHandler extends Handler {

        private final WeakReference<BaseActivity> mActivity;

        private boolean isLoadToastShowing = false;

        private LoadToast loadToast;

        public MemorySafeHandler(BaseActivity activity) {
            mActivity = new WeakReference<BaseActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            BaseActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case MSG_WHAT_START_PROGRESS:
                        if (isLoadToastShowing) {
                            return;
                        }
                        String message = msg.getData().getString("message");
                        if (loadToast == null) {
                            loadToast = new LoadToast(activity)
                                    .setText(message)
                                    .setTextColor(0x555555)
                                    .setTranslationY(ScreenUtil.getMiddleAppY(activity));
                        }
                        loadToast.show();
                        isLoadToastShowing = true;
                        break;
                    case MSG_WHAT_STOP_PROGRESS_ERROR:
                        if (loadToast != null && isLoadToastShowing) {
                            loadToast.error();
                            loadToast = null;
                            isLoadToastShowing = false;
                        }
                        break;
                    case MSG_WHAT_STOP_PROGRESS_SUCCESS:
                        if (loadToast != null && isLoadToastShowing) {
                            loadToast.success();
                            loadToast = null;
                            isLoadToastShowing = false;
                        }
                        break;
                }
            }
        }
    }

}
