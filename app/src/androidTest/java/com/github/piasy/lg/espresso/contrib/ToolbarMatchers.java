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

package com.github.piasy.lg.espresso.contrib;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;

/**
 * Created by Piasy{github.com/Piasy} on 15/9/29.
 *
 * espresso matcher for {@link android.support.v7.widget.Toolbar} and {@link
 * android.widget.Toolbar}
 */
public final class ToolbarMatchers {

    private ToolbarMatchers() {
        // no instance
    }

    /**
     * Creates a {@link ViewInteraction} for {@link android.support.v7.widget.Toolbar}, assuming
     * that there is only one Toolbar in the view hierarchy.
     *
     * @return the selected {@link android.support.v7.widget.Toolbar}.
     */
    public static ViewInteraction onSupportToolbar() {
        return onView(isAssignableFrom(android.support.v7.widget.Toolbar.class));
    }

    /**
     * Creates a {@link ViewInteraction} for {@link android.widget.Toolbar}, assuming
     * that there is only one Toolbar in the view hierarchy.
     *
     * @return the selected {@link android.widget.Toolbar}.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static ViewInteraction onToolbar() {
        return onView(isAssignableFrom(android.widget.Toolbar.class));
    }

    /**
     * Returns a matcher that matches the {@link android.support.v7.widget.Toolbar} that has a
     * title
     * matching the textMatcher.
     *
     * @param textMatcher to match the title of Toolbar.
     */
    public static Matcher<View> withSupportToolbarTitle(final Matcher<String> textMatcher) {
        return new BoundedMatcher<View, android.support.v7.widget.Toolbar>(
                android.support.v7.widget.Toolbar.class) {
            @Override
            public boolean matchesSafely(final android.support.v7.widget.Toolbar toolbar) {
                return textMatcher.matches(toolbar.getTitle().toString());
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("with Toolbar title: ");
                textMatcher.describeTo(description);
            }
        };
    }

    /**
     * Returns a matcher that matches the {@link android.support.v7.widget.Toolbar} that has a
     * title
     * matching the textMatcher.
     *
     * @param textMatcher to match the title of Toolbar.
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Matcher<View> withToolbarTitle(final Matcher<String> textMatcher) {
        return new BoundedMatcher<View, android.widget.Toolbar>(android.widget.Toolbar.class) {
            @Override
            public boolean matchesSafely(final android.widget.Toolbar toolbar) {
                return textMatcher.matches(toolbar.getTitle().toString());
            }

            @Override
            public void describeTo(final Description description) {
                description.appendText("with Toolbar title: ");
                textMatcher.describeTo(description);
            }
        };
    }
}
