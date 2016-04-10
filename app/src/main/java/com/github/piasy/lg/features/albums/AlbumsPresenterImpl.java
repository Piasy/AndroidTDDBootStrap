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

package com.github.piasy.lg.features.albums;

import com.github.piasy.base.mvp.NullObjRxBasePresenter;
import com.github.piasy.lg.features.albums.mvp.AlbumsPresenter;
import com.github.piasy.lg.features.albums.mvp.AlbumsView;
import com.github.piasy.model.errors.RxErrorProcessor;
import com.github.piasy.model.ligui.LGAlbum;
import com.github.piasy.model.ligui.LGApi;
import com.github.piasy.model.ligui.LGMeta;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Piasy{github.com/Piasy} on 3/6/16.
 */
public class AlbumsPresenterImpl extends NullObjRxBasePresenter<AlbumsView>
        implements AlbumsPresenter {
    private final LGApi mLGApi;
    private final RxErrorProcessor mRxErrorProcessor;

    public AlbumsPresenterImpl(final LGApi lgApi, final RxErrorProcessor rxErrorProcessor) {
        super();
        mLGApi = lgApi;
        mRxErrorProcessor = rxErrorProcessor;
    }

    @Override
    public void loadAlbums() {
        addSubscription(mLGApi.meta().map(new Func1<LGMeta, List<String>>() {
            @Override
            public List<String> call(LGMeta lgMeta) {
                return lgMeta.parts();
            }
        }).flatMap(new Func1<List<String>, Observable<String>>() {
            @Override
            public Observable<String> call(List<String> parts) {
                return Observable.from(parts);
            }
        }).flatMap(new Func1<String, Observable<List<LGAlbum>>>() {
            @Override
            public Observable<List<LGAlbum>> call(String onePart) {
                return mLGApi.onePart(onePart);
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<List<LGAlbum>>() {
            @Override
            public void call(List<LGAlbum> albums) {
                getView().showAlbums(albums);
            }
        }, mRxErrorProcessor));
    }
}
