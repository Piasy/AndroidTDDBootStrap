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

import android.support.annotation.Nullable;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.disk.DiskStorageCache;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.request.ImageRequest;
import com.github.piasy.base.mvp.NullObjRxBasePresenter;
import com.github.piasy.base.utils.RxUtil;
import com.github.piasy.lg.features.photo.mvp.PhotoPresenter;
import com.github.piasy.lg.features.photo.mvp.PhotoView;
import java.io.File;
import javax.inject.Inject;
import javax.inject.Named;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Piasy{github.com/Piasy} on 4/10/16.
 */
public class PhotoPresenterImpl extends NullObjRxBasePresenter<PhotoView>
        implements PhotoPresenter {
    private final ImagePipeline mImagePipeline;
    private final DefaultCacheKeyFactory mCacheKeyFactory;
    private final DiskStorageCache mMainDiskCache;
    private final DiskStorageCache mSmallDiskCache;

    @Inject
    public PhotoPresenterImpl(final ImagePipeline imagePipeline,
            final DefaultCacheKeyFactory cacheKeyFactory,
            @Named("MainDiskCache") final DiskStorageCache mainDiskCache,
            @Named("SmallDiskCache") final DiskStorageCache smallDiskCache) {
        super();
        mImagePipeline = imagePipeline;
        mCacheKeyFactory = cacheKeyFactory;
        mMainDiskCache = mainDiskCache;
        mSmallDiskCache = smallDiskCache;
    }

    @Override
    public void loadPhoto(final String url) {
        addSubscription(Observable.just(url)
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, ImageRequest>() {
                    @Override
                    public ImageRequest call(final String photoUrl) {
                        return ImageRequest.fromUri(photoUrl);
                    }
                })
                .flatMap(fetch())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<File>() {
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

    private Func1<ImageRequest, Observable<File>> fetch() {
        return new Func1<ImageRequest, Observable<File>>() {
            @Override
            public Observable<File> call(final ImageRequest request) {
                return Observable.create(new Observable.OnSubscribe<File>() {
                    @Override
                    public void call(final Subscriber<? super File> subscriber) {
                        final DataSource<Void> dataSource =
                                mImagePipeline.prefetchToDiskCache(request,
                                        PhotoPresenterImpl.this);
                        dataSource.subscribe(new BaseDataSubscriber<Void>() {
                            @Override
                            protected void onNewResultImpl(final DataSource<Void> dataSource) {
                                final File cacheFile = getCacheFile(request);
                                if (cacheFile == null) {
                                    subscriber.onError(new UnknownError("cache file is null"));
                                } else {
                                    subscriber.onNext(cacheFile);
                                }
                            }

                            @Override
                            protected void onFailureImpl(final DataSource<Void> dataSource) {
                                subscriber.onError(new UnknownError("fetch image fail"));
                            }
                        }, CallerThreadExecutor.getInstance());
                    }
                });
            }
        };
    }

    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    @Nullable
    private File getCacheFile(final ImageRequest request) {
        final CacheKey cacheKey = mCacheKeyFactory.getEncodedCacheKey(request);
        File cacheFile = null;
        if (mMainDiskCache.hasKey(cacheKey)) {
            cacheFile = ((FileBinaryResource) mMainDiskCache.getResource(cacheKey)).getFile();
        } else if (mSmallDiskCache.hasKey(cacheKey)) {
            cacheFile = ((FileBinaryResource) mSmallDiskCache.getResource(cacheKey)).getFile();
        }
        return cacheFile;
    }
}