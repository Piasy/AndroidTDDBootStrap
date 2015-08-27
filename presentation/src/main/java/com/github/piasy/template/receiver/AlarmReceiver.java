package com.github.piasy.template.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import com.github.piasy.template.TemplateApp;
import timber.log.Timber;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/27.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wl.acquire();
        Timber.i("AutoBoot AlarmReceiver" + context.toString() + ", " + intent.toString());
        Timber.i("AutoBoot AlarmReceiver, " + TemplateApp.getInstance().getStartFrom());
        wl.release();
    }
}
