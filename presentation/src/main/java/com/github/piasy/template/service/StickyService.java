package com.github.piasy.template.service;

import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import com.github.piasy.template.TemplateApp;
import com.github.piasy.template.base.BaseService;
import timber.log.Timber;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/26.
 */
public class StickyService extends BaseService {

    private static int sRequestId = 1;

    private StickyHandler mStickyHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        Timber.i("AutoBoot StickyService#onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.i("AutoBoot StickyService#onDestroy");
        Intent restart = new Intent(TemplateApp.getInstance(), StickyService.class);
        TemplateApp.getInstance().startService(restart);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Timber.i("AutoBoot StickyService#onStartCommand startId: " + startId);
        HandlerThread handlerThread = new HandlerThread("StickyHandlerThread");
        handlerThread.start();
        mStickyHandler = new StickyHandler(handlerThread.getLooper());
        return START_STICKY;
    }

    private static class StickyHandler extends Handler {
        StickyHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
