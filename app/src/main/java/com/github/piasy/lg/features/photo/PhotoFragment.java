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

import android.content.res.Resources;
import android.view.View;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.github.piasy.base.android.BaseMvpFragment;
import com.github.piasy.lg.R;
import com.github.piasy.lg.features.photo.di.PhotoComponent;
import com.github.piasy.lg.features.photo.mvp.PhotoPresenter;
import com.github.piasy.lg.features.photo.mvp.PhotoView;
import com.yatatsu.autobundle.AutoBundleField;
import java.io.File;
import javax.inject.Inject;

import static butterknife.ButterKnife.findById;

/**
 * Created by Piasy{github.com/Piasy} on 4/10/16.
 */
public class PhotoFragment extends BaseMvpFragment<PhotoView, PhotoPresenter, PhotoComponent>
        implements PhotoView {

    @Inject
    Resources mResources;
    @AutoBundleField
    String mPhotoUrl;

    private SubsamplingScaleImageView mPhotoView;

    @Override
    protected int getLayoutRes() {
        return R.layout.photo_fragment;
    }

    @Override
    protected boolean hasArgs() {
        return true;
    }

    @Override
    protected void inject() {
        getComponent().inject(this);
    }

    @Override
    protected void bindView(final View rootView) {
        mPhotoView = findById(rootView, R.id.mPhotoView);
    }

    @Override
    protected void startBusiness() {
        super.startBusiness();
        mPresenter.loadPhoto(mPhotoUrl);
    }

    @Override
    public void showPhoto(final File photo) {
        mPhotoView.setImage(ImageSource.uri(photo.getAbsolutePath()));
    }

    @Override
    public void loadPhotoFail() {

    }
}
