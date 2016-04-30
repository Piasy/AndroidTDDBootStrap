/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Piasy
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

package com.github.piasy.app;

import android.view.MotionEvent;
import com.bugtags.library.Bugtags;
import com.github.piasy.app.features.splash.SplashActivity;
import com.github.piasy.base.android.BaseActivity;

/**
 * Created by Piasy{github.com/Piasy} on 16/4/13.
 */
public abstract class BootstrapActivity extends BaseActivity {
    @Override
    protected void onResume() {
        super.onResume();
        if (isTrack()) {
            Bugtags.onResume(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isTrack()) {
            Bugtags.onPause(this);
        }
    }

    @Override
    public boolean dispatchTouchEvent(final MotionEvent ev) {
        if (isTrack()) {
            Bugtags.onDispatchTouchEvent(this, ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean isTrack() {
        return "release".equals(BuildConfig.BUILD_TYPE) && !(this instanceof SplashActivity);
    }
}