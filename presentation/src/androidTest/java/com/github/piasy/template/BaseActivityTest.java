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

package com.github.piasy.template;

import android.app.Instrumentation;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.github.piasy.common.android.utils.AndroidUtilsModule;
import com.github.piasy.model.entities.GithubUser;
import com.github.piasy.model.entities.GithubUserSearchResult;
import com.github.piasy.model.rest.github.GithubAPI;
import com.github.piasy.model.rest.github.MockGithubAPIModule;
import com.github.piasy.template.app.TemplateApp;
import com.github.piasy.template.app.di.AppModule;
import com.github.piasy.template.features.user.GithubUserActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import rx.Observable;

import static android.os.SystemClock.sleep;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.anyString;

/**
 * Created by Piasy{github.com/Piasy} on 15/9/26.
 */
@RunWith(AndroidJUnit4.class)
public class BaseActivityTest {
    @Rule
    public ActivityTestRule<GithubUserActivity> mActivityTestRule =
            new ActivityTestRule<>(GithubUserActivity.class, true, false);

    @Inject
    GithubAPI mGithubAPI;
    @Inject
    Gson mGson;

    private List<GithubUser> mSingleUserList;
    private GithubUserSearchResult mSingleResult;
    private GithubUser mSingleUser;

    @Before
    public void setUp() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        TemplateApp app = (TemplateApp) instrumentation.getTargetContext().getApplicationContext();
        MockAppComponent component = DaggerMockAppComponent.builder()
                .appModule(new AppModule(app))
                .androidUtilsModule(new AndroidUtilsModule(app))
                .githubAPIModule(new MockGithubAPIModule())
                .build();
        app.setComponent(component);
        component.inject(this);

        mSingleResult = mGson.fromJson(MockProvider.provideSimplifiedGithubUserSearchResultStr(),
                new TypeToken<GithubUserSearchResult>() {}.getType());
        mSingleUser = mGson.fromJson(MockProvider.provideGithubUserStr(), GithubUser.class);
        mSingleUserList = new ArrayList<>();
        mSingleUserList.add(mSingleUser);
    }

    @Test
    public void testNormalLaunch() {
        // given
        willReturn(Observable.create(subscriber -> {
            subscriber.onNext(mSingleResult);
            subscriber.onCompleted();
        })).given(mGithubAPI).searchGithubUsers(anyString(), anyString(), anyString());
        willReturn(Observable.create(subscriber -> {
            subscriber.onNext(mSingleUser);
            subscriber.onCompleted();
        })).given(mGithubAPI).getGithubUser(anyString());

        mActivityTestRule.launchActivity(new Intent());
        sleep(TimeUnit.SECONDS.toMillis(5));
        assertTrue(true);
    }
}
