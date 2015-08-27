package com.github.piasy.template.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import com.github.piasy.template.TemplateApp;
import com.github.piasy.template.service.StickyService;
import timber.log.Timber;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/26.
 */
public class BootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(@NonNull Context context, @NonNull Intent intent) {
        Timber.i("AutoBoot BootCompleteReceiver " + context.toString() + ", " + intent.toString());
        Timber.i("AutoBoot: before boot complete, " + TemplateApp.getInstance().getStartFrom());
        TemplateApp.getInstance().setStartFrom(TemplateApp.StartFrom.BOOT);
        Timber.i("AutoBoot: after boot complete, " + TemplateApp.getInstance().getStartFrom());
        // application will be dead soon if not start service/activity
        TemplateApp.getInstance().startService(
                new Intent(TemplateApp.getInstance(), StickyService.class));
    }
}
