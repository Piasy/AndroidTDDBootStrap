package com.piasy.template.di;

import com.piasy.model.rest.RestModule;
import com.piasy.template.TemplateApp;
import com.piasy.template.base.BaseActivity;
import com.piasy.template.base.BaseService;

import android.app.Application;
import android.content.res.Resources;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
@Singleton
@Component(
        modules = {
                AppModule.class,
                RestModule.class
        })
public interface AppComponent {

    /**
     * Only @Inject annotated members of parameter type and its super type could be injected,
     * the subtypes' member could not.
     * ref: http://stackoverflow.com/a/29956910/3077508
     * */
    void inject(TemplateApp application);
    void inject(BaseActivity activity);
    void inject(BaseService service);

    Application application();

    Resources resources();
}
