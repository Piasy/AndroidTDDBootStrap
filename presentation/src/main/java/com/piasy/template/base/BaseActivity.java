package com.piasy.template.base;

import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import com.piasy.common.android.utils.screen.ScreenUtil;
import com.piasy.template.base.di.ActivityModule;
import com.piasy.template.base.di.AppComponent;
import com.piasy.template.base.di.IApplication;
import java.lang.ref.WeakReference;
import javax.inject.Inject;
import net.steamcrafted.loadtoast.LoadToast;

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
    @Inject
    protected ScreenUtil mScreenUtil;

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

    protected abstract void initializeInjector();

    @Override
    protected void onStop() {
        super.onStop();
        stopProgress(true);
    }

    public void stopProgress(final boolean success) {
        mHandler.postDelayed(() -> {
            if (success) {
                mHandler.sendEmptyMessage(MSG_WHAT_STOP_PROGRESS_SUCCESS);
            } else {
                mHandler.sendEmptyMessage(MSG_WHAT_STOP_PROGRESS_ERROR);
            }
        }, 500);
    }

    /**
     * Get an Activity module for dependency injection.
     *
     * @return {@link ActivityModule}
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    public void showProgress() {
        showProgress("Loading");
    }

    public void showProgress(final String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        mHandler.post(() -> {
            Message msg = new Message();
            msg.what = MSG_WHAT_START_PROGRESS;
            Bundle bundle = new Bundle();
            bundle.putString("message", message);
            msg.setData(bundle);
            mHandler.sendMessage(msg);
        });
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

        private LoadToast mLoadToast;

        public MemorySafeHandler(BaseActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mActivity.get() != null && mActivity.get().mScreenUtil != null) {
                switch (msg.what) {
                    case MSG_WHAT_START_PROGRESS:
                        if (isLoadToastShowing) {
                            return;
                        }
                        String message = msg.getData().getString("message");
                        if (mLoadToast == null) {
                            mLoadToast = new LoadToast(mActivity.get()).setText(message)
                                    .setTextColor(0x555555)
                                    .setTranslationY(mActivity.get().mScreenUtil.getScreenHeight(
                                            mActivity.get()) / 2);
                        }
                        mLoadToast.show();
                        isLoadToastShowing = true;
                        break;
                    case MSG_WHAT_STOP_PROGRESS_ERROR:
                        if (mLoadToast != null && isLoadToastShowing) {
                            mLoadToast.error();
                            mLoadToast = null;
                            isLoadToastShowing = false;
                        }
                        break;
                    case MSG_WHAT_STOP_PROGRESS_SUCCESS:
                        if (mLoadToast != null && isLoadToastShowing) {
                            mLoadToast.success();
                            mLoadToast = null;
                            isLoadToastShowing = false;
                        }
                        break;
                }
            }
        }
    }
}
