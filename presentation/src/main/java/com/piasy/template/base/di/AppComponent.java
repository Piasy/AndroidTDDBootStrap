package com.piasy.template.base.di;

import android.app.Application;
import android.content.res.Resources;
import com.google.gson.Gson;
import com.piasy.common.android.utils.AndroidUtilsModule;
import com.piasy.common.android.utils.model.ThreeTenABPDelegate;
import com.piasy.common.android.utils.net.RxUtil;
import com.piasy.common.android.utils.screen.ScreenUtil;
import com.piasy.common.android.utils.ui.ToastUtil;
import com.piasy.common.utils.EmailUtil;
import com.piasy.common.utils.UtilsModule;
import com.piasy.model.db.StorIOSQLiteDelegate;
import com.piasy.model.db.di.DBModule;
import com.piasy.model.rest.RestModule;
import com.piasy.template.TemplateApp;
import com.piasy.template.base.BaseActivity;
import com.piasy.template.base.BaseService;
import dagger.Component;
import de.greenrobot.event.EventBus;
import javax.inject.Singleton;
import retrofit.RestAdapter;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
@Singleton
@Component(
        modules = {
                AppModule.class,

                UtilsModule.class, AndroidUtilsModule.class,

                DBModule.class, RestModule.class
        })
public interface AppComponent {

    /**
     * Only @Inject annotated members of parameter type and its super type could be injected,
     * the subtypes' member could not.
     * ref: http://stackoverflow.com/a/29956910/3077508
     */
    void inject(TemplateApp application);

    void inject(BaseActivity activity);

    void inject(BaseService service);

    Application application();

    Resources resources();

    ScreenUtil screenUtil();

    EmailUtil emailUtil();

    ToastUtil toastUtil();

    RxUtil.RxErrorProcessor rxErrorProcessor();

    StorIOSQLiteDelegate storIOSQLite();

    ThreeTenABPDelegate threeTenABPDelegate();

    Gson gson();

    EventBus eventBus();

    RestAdapter restAdapter();
}
