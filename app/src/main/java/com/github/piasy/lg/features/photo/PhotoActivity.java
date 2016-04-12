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

package com.github.piasy.lg.features.photo;

import android.os.Bundle;
import com.github.piasy.base.android.BaseActivity;
import com.github.piasy.base.di.HasComponent;
import com.github.piasy.lg.LGApp;
import com.github.piasy.lg.features.photo.di.PhotoComponent;
import com.github.piasy.lg.features.photo.di.PhotoModule;
import com.yatatsu.autobundle.AutoBundleField;

/**
 * Created by Piasy{github.com/Piasy} on 4/10/16.
 */
public class PhotoActivity extends BaseActivity implements HasComponent<PhotoComponent> {

    @AutoBundleField
    String mPhotoUrl;

    private PhotoComponent mPhotoComponent;

    @Override
    protected boolean hasArgs() {
        return true;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .add(android.R.id.content,
                        PhotoFragmentAutoBundle.createFragmentBuilder(mPhotoUrl).build()).commit();
    }

    @Override
    protected void initializeInjector() {
        mPhotoComponent = LGApp.get().appComponent().plus(getActivityModule(), new PhotoModule());
    }

    @Override
    public PhotoComponent getComponent() {
        return mPhotoComponent;
    }
}
