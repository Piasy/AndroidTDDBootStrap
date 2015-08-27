package com.github.piasy.template.ui.search;

import android.os.Bundle;
import com.github.piasy.common.di.HasComponent;
import com.github.piasy.template.TemplateApp;
import com.github.piasy.template.base.BaseActivity;
import com.github.piasy.template.ui.search.di.DaggerGithubSearchComponent;
import com.github.piasy.template.ui.search.di.GithubSearchComponent;
import timber.log.Timber;

public class GithubSearchActivity extends BaseActivity
        implements HasComponent<GithubSearchComponent> {

    GithubSearchComponent mGithubSearchComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.i("AutoBoot: GithubSearchActivity, " + TemplateApp.getInstance().getStartFrom());
        /*if (TemplateApp.getInstance().getStartFrom() == TemplateApp.StartFrom.UNDEFINED) {
            TemplateApp.getInstance().setStartFrom(TemplateApp.StartFrom.HOME);
            TemplateApp.getInstance().startService(new Intent(this, StickyService.class));
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent alarm = new Intent();
            alarm.setAction("com.github.piasy.STICKY_ALARM");
            PendingIntent pendingIntent =
                    PendingIntent.getBroadcast(TemplateApp.getInstance(), 0, alarm,
                            PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 5 * 1000,
                    5 * 1000, pendingIntent);
            Timber.i("AutoBoot alarm set");
        }*/
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content, new GithubSearchFragment())
                .commit();
    }

    @Override
    protected void initializeInjector() {
        mGithubSearchComponent = DaggerGithubSearchComponent.builder()
                .appComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
        mGithubSearchComponent.inject(this);
    }

    @Override
    public GithubSearchComponent getComponent() {
        return mGithubSearchComponent;
    }
}
