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

import android.content.Intent;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import butterknife.ButterKnife;
import com.github.piasy.model.entities.GithubUserSearchResult;
import com.github.piasy.model.rest.github.GithubAPI;
import com.github.piasy.template.MockProvider;
import com.github.piasy.template.R;
import com.github.piasy.template.app.BaseActivityTest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.joanzapata.iconify.widget.IconTextView;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import rx.Observable;
import rx.Subscriber;

import static android.os.SystemClock.sleep;
import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.github.piasy.template.espresso.contrib.ToolbarMatchers.onSupportToolbar;
import static com.github.piasy.template.espresso.contrib.ToolbarMatchers.withSupportToolbarTitle;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Matchers.anyString;

/**
 * Created by Piasy{github.com/Piasy} on 15/9/19.
 */
@RunWith(AndroidJUnit4.class)
@SuppressWarnings("PMD.JUnitAssertionsShouldIncludeMessage")
public class SplashActivityTest extends BaseActivityTest {
    @Rule
    public ActivityTestRule<SplashActivity> mActivityTestRule =
            new ActivityTestRule<>(SplashActivity.class, true, false);
    @Inject
    GithubAPI mGithubAPI;
    @Inject
    Gson mGson;
    private GithubUserSearchResult mSingleResult;

    @Before
    public void setUp() {
        setMockAppComponent().inject(this);

        mSingleResult = mGson.fromJson(MockProvider.provideSimplifiedGithubUserSearchResultStr(),
                new TypeToken<GithubUserSearchResult>() {}.getType());
    }

    @Test
    public void testNormalLaunch() {
        willReturn(Observable.create(new Observable.OnSubscribe<GithubUserSearchResult>() {
            @Override
            public void call(final Subscriber<? super GithubUserSearchResult> subscriber) {
                subscriber.onNext(mSingleResult);
                subscriber.onCompleted();
            }
        })/*.delay(10, TimeUnit.SECONDS)*/).given(mGithubAPI)
                .searchGithubUsers(anyString(), anyString(), anyString());

        final SplashActivity activity = mActivityTestRule.launchActivity(new Intent());
        onView(withId(R.id.mTvMessage)).check(matches(withText(R.string.splash)));

        waitForGithubSearchFragmentVisible(activity);
        onSupportToolbar().check(
                matches(allOf(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
                        withSupportToolbarTitle(is(activity.getString(R.string.search))))));

        final Toolbar toolbar = ButterKnife.findById(activity, R.id.mToolBar);
        final MenuItem searchItem = toolbar.getMenu().findItem(R.id.mActionSearch);
        assertTrue(searchItem.getActionView() instanceof SearchView);
        assertFalse(searchItem.isActionViewExpanded());
        assertEquals(activity.getString(R.string.search), searchItem.getTitle());

        onView(withId(R.id.mActionSearch)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText("piasy"),
                pressKey(KeyEvent.KEYCODE_ENTER));
        closeSoftKeyboard();
        sleep(TimeUnit.MILLISECONDS.toMillis(2000));

        // then, boilerplate code to get, cast view, then get property, then assert it...
        final RecyclerView recyclerView = ButterKnife.findById(activity, R.id.mRvSearchResult);
        assertEquals(1, recyclerView.getChildCount());
        final CardView cardView = (CardView) recyclerView.getChildAt(0);
        final LinearLayout linearLayout = (LinearLayout) cardView.getChildAt(0);
        final IconTextView tvUsername = (IconTextView) linearLayout.getChildAt(1);
        // after setText into IconTextView, the content will be parsed as icon, the original text
        // won't exist anymore.
        assertTrue(tvUsername.getText().toString().endsWith(mSingleResult.items().get(0).login()));
    }

    // split into two test case will cause the second executed cast caught:
    // "android.support.test.espresso.AppNotIdleException: Looped for 5517 iterations over 60
    // SECONDS. The following Idle Conditions failed ."
    /*@Test
    public void testClickAndSearch() {
        Log.d("SplashActivityTest", "testClickAndSearch");
        // given
        willReturn(Observable.create(subscriber -> {
            subscriber.onNext(mSingleResult);
            subscriber.onCompleted();
        })*//*.delay(10, TimeUnit.SECONDS)*//*).given(mGithubAPI)
                .searchGithubUsers(anyString(), anyString(), anyString());

        // when
        SplashActivity activity = mActivityTestRule.launchActivity(new Intent());
        waitForGithubSearchFragmentVisible(activity);

        onView(withId(R.id.mActionSearch)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText("piasy"),
                pressKey(KeyEvent.KEYCODE_ENTER));
        sleep(TimeUnit.MILLISECONDS.toMillis(2000));

        // then, boilerplate code to get, cast view, then get property, then assert it...
        RecyclerView recyclerView = ButterKnife.findById(activity, R.id.mRvSearchResult);
        assertEquals(1, recyclerView.getChildCount());
        CardView cardView = (CardView) recyclerView.getChildAt(0);
        LinearLayout linearLayout = (LinearLayout) cardView.getChildAt(0);
        IconTextView tvUsername = (IconTextView) linearLayout.getChildAt(1);
        // after setText into IconTextView, the content will be parsed as icon, the original text
        // won't exist anymore.
        assertTrue(tvUsername.getText().toString().endsWith(mSingleResult.items().get(0).login()));
    }*/

    // pmd error?
    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private void waitForGithubSearchFragmentVisible(final AppCompatActivity activity) {
        while (!isGithubSearchFragmentVisible(activity)) {
            sleep(TimeUnit.MILLISECONDS.toMillis(100));
        }
    }

    private boolean isGithubSearchFragmentVisible(final AppCompatActivity activity) {
        final Fragment fragment =
                activity.getSupportFragmentManager().findFragmentByTag("GithubSearchFragment");
        return fragment != null && fragment.isVisible();
    }

    /*private Matcher<View> atRecyclerViewPosition(final int position) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                if (item.getParent() instanceof RecyclerView &&
                        ((RecyclerView) item.getParent()).getChildAt(position) == item) {
                    return true;
                }
                return false;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("atRecyclerViewPosition");
            }
        };
    }

    private Matcher<View> hasChildrenCount(final int count) {
        return new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View view) {
                return view instanceof RecyclerView &&
                        ((RecyclerView) view).getChildCount() == count;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("hasChildrenCount");
            }
        };
    }*/
}
