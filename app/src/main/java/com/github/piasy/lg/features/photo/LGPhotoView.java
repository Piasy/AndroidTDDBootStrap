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

import android.content.Context;
import android.util.AttributeSet;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.github.piasy.base.utils.RxUtil;
import com.github.piasy.lg.glide.LgFetchTarget;
import java.io.File;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by Piasy{github.com/Piasy} on 16/4/13.
 */
public class LGPhotoView extends SubsamplingScaleImageView {

    private Subscription mFetchSubscription;
    private SimpleTarget<File> mFetchTarget;

    public LGPhotoView(final Context context, final AttributeSet attr) {
        super(context, attr);
    }

    public LGPhotoView(final Context context) {
        this(context, null);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancelFetch();
        resetScaleAndCenter();
    }

    private void cancelFetch() {
        if (mFetchSubscription != null && !mFetchSubscription.isUnsubscribed()) {
            mFetchSubscription.unsubscribe();
        }
        if (mFetchTarget != null) {
            Glide.clear(mFetchTarget);
        }
    }

    public void loadPhoto(final String url) {
        cancelFetch();
        resetScaleAndCenter();
        setVisibility(INVISIBLE);
        mFetchSubscription = fetch(url).subscribe(new Action1<File>() {
            @Override
            public void call(final File photo) {
                setImage(ImageSource.uri(photo.getAbsolutePath()));
                setVisibility(VISIBLE);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(final Throwable throwable) {
                // TODO: 16/4/13
                RxUtil.OnErrorLogger.call(throwable);
            }
        });
    }

    private Observable<File> fetch(final String photoUrl) {
        return Observable.create(new Observable.OnSubscribe<File>() {
            @Override
            public void call(final Subscriber<? super File> subscriber) {
                mFetchTarget = new LgFetchTarget(subscriber);
                Glide.with(getContext()).load(photoUrl).downloadOnly(mFetchTarget);
            }
        });
    }
}
