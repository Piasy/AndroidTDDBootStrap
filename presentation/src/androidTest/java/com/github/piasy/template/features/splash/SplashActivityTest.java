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

package com.github.piasy.template.features.splash;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Intent;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import com.github.piasy.template.R;
import com.github.piasy.template.features.user.GithubUserActivity;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Piasy{github.com/Piasy} on 15/9/19.
 */
@RunWith(AndroidJUnit4.class)
public class SplashActivityTest {
    @Rule
    public ActivityTestRule<SplashActivity> mSplashActivityActivityTestRule =
            new ActivityTestRule<>(SplashActivity.class, true, false);

    @Before
    public void setUp() {
        Intents.init();
        Intents.intending(IntentMatchers.hasComponent(GithubUserActivity.class.getName())).respondWith(
                new Instrumentation.ActivityResult(Activity.RESULT_OK, new Intent()));
    }

    @Test
    public void testLaunch() {
        SplashActivity activity = mSplashActivityActivityTestRule.launchActivity(new Intent());
        onView(withId(R.id.mTvMessage)).check(matches(withText(R.string.splash)));

        Intents.intended(IntentMatchers.hasComponent(
                new ComponentName(activity, GithubUserActivity.class)));
    }
}
