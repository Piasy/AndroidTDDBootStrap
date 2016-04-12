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
import android.graphics.drawable.Drawable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.github.piasy.base.mvp.NullObjRxBasePresenter;
import com.github.piasy.base.utils.RxUtil;
import com.github.piasy.lg.features.photo.mvp.PhotoPresenter;
import com.github.piasy.lg.features.photo.mvp.PhotoView;
import java.io.File;
import javax.inject.Inject;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by Piasy{github.com/Piasy} on 4/10/16.
 */
public class PhotoPresenterImpl extends NullObjRxBasePresenter<PhotoView>
        implements PhotoPresenter {

    private final Context mContext;

    @Inject
    public PhotoPresenterImpl(final Context context) {
        super();
        mContext = context;
    }

    @Override
    public void loadPhoto(final String url) {
        addSubscription(fetch(url).subscribe(new Action1<File>() {
            @Override
            public void call(final File photo) {
                getView().showPhoto(photo);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(final Throwable throwable) {
                getView().loadPhotoFail();
                RxUtil.OnErrorLogger.call(throwable);
            }
        }));
    }

    private Observable<File> fetch(final String photoUrl) {
        return Observable.create(new Observable.OnSubscribe<File>() {
            @Override
            public void call(final Subscriber<? super File> subscriber) {
                Glide.with(mContext).load(photoUrl).downloadOnly(new SimpleTarget<File>() {
                    @Override
                    public void onLoadFailed(final Exception e, final Drawable errorDrawable) {
                        subscriber.onError(e);
                    }

                    @Override
                    public void onResourceReady(final File resource,
                            final GlideAnimation<? super File> glideAnimation) {
                        subscriber.onNext(resource);
                    }
                });
            }
        });
    }
}