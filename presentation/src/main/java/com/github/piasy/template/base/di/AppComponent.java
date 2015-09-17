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

package com.github.piasy.template.base.di;

import android.app.Application;
import android.content.res.Resources;
import com.github.piasy.common.android.provider.ProviderModule;
import com.github.piasy.common.android.utils.AndroidUtilsModule;
import com.github.piasy.common.android.utils.model.ThreeTenABPDelegate;
import com.github.piasy.common.android.utils.net.RxUtil;
import com.github.piasy.common.android.utils.roms.MiUIUtil;
import com.github.piasy.common.android.utils.screen.ScreenUtil;
import com.github.piasy.common.android.utils.ui.ToastUtil;
import com.github.piasy.common.utils.EmailUtil;
import com.github.piasy.common.utils.UtilsModule;
import com.github.piasy.model.db.StorIOSQLiteDelegate;
import com.github.piasy.model.db.di.DBModule;
import com.github.piasy.template.TemplateApp;
import com.github.piasy.template.base.BaseActivity;
import com.github.piasy.template.base.BaseService;
import com.google.gson.Gson;
import dagger.Component;
import de.greenrobot.event.EventBus;
import javax.inject.Singleton;
import retrofit.RestAdapter;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 *
 * DI component abstraction for Application scope.
 * Application scope is {@link Singleton} scope, sub component like Activity scope could have
 * wilder
 * scope, like {@link com.github.piasy.common.di.PerActivity}.
 */
@SuppressWarnings("PMD.TooManyMethods")
@Singleton
@Component(
        modules = {
                AppModule.class,

                UtilsModule.class, AndroidUtilsModule.class,

                DBModule.class, ProviderModule.class
        })
public interface AppComponent {

    /**
     * Only @Inject annotated members of parameter type and its super type could be injected,
     * the subtypes' member could not.
     * ref: http://stackoverflow.com/a/29956910/3077508
     *
     * @param application {@link TemplateApp} object to inject dependency.
     */
    void inject(TemplateApp application);

    /**
     * Inject dependency into {@link BaseActivity}'s field.
     * NOTE!!! subclasses' instance could be passed in, but subclasses' dependency won't be
     * injected
     *
     * @param activity {@link BaseActivity} or subclasses instance
     */
    void inject(BaseActivity activity);

    /**
     * Inject dependency into {@link BaseService}'s field.
     * NOTE!!! subclasses' instance could be passed in, but subclasses' dependency won't be
     * injected
     *
     * @param service {@link BaseService} or subclasses instance
     */
    void inject(BaseService service);

    /**
     * exposed {@link Application} object.
     * @return exposed {@link Application} object.
     */
    Application application();

    /**
     * exposed {@link Resources} object.
     * @return exposed {@link Resources} object.
     */
    Resources resources();

    /**
     * exposed {@link ScreenUtil} object.
     * @return exposed {@link ScreenUtil} object.
     */
    ScreenUtil screenUtil();

    /**
     * exposed {@link EmailUtil} object.
     * @return exposed {@link EmailUtil} object.
     */
    EmailUtil emailUtil();

    /**
     * exposed {@link ToastUtil} object.
     * @return exposed {@link ToastUtil} object.
     */
    ToastUtil toastUtil();

    /**
     * exposed {@link MiUIUtil} object.
     * @return exposed {@link MiUIUtil} object.
     */
    MiUIUtil miUIUtil();

    /**
     * exposed {@link RxUtil.RxErrorProcessor} object.
     * @return exposed {@link RxUtil.RxErrorProcessor} object.
     */
    RxUtil.RxErrorProcessor rxErrorProcessor();

    /**
     * exposed {@link StorIOSQLiteDelegate} object.
     * @return exposed {@link StorIOSQLiteDelegate} object.
     */
    StorIOSQLiteDelegate storIOSQLite();

    /**
     * exposed {@link ThreeTenABPDelegate} object.
     * @return exposed {@link ThreeTenABPDelegate} object.
     */
    ThreeTenABPDelegate threeTenABPDelegate();

    /**
     * exposed {@link Gson} object.
     * @return exposed {@link Gson} object.
     */
    Gson gson();

    /**
     * exposed {@link EventBus} object.
     * @return exposed {@link EventBus} object.
     */
    EventBus eventBus();

    /**
     * exposed {@link RestAdapter} object.
     * @return exposed {@link RestAdapter} object.
     */
    RestAdapter restAdapter();
}
