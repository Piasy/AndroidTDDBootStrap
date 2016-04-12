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

package com.github.piasy.model.ligui;

import com.f2prateek.rx.preferences.Preference;
import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.Single;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Piasy{github.com/Piasy} on 4/10/16.
 */
public class LGDataManagerImpl implements LGDataManager {
    private final LGApi mLGApi;
    private final Preference<Integer> mLGVersion;
    private final Preference<String> mLGAlbums;
    private final Gson mGson;

    @Inject
    public LGDataManagerImpl(final LGApi lgApi, final RxSharedPreferences rxSharedPreferences,
            final Gson gson) {
        mLGApi = lgApi;
        mGson = gson;
        mLGVersion = rxSharedPreferences.getInteger(LGMeta.PREF_KEY_VERSION);
        mLGAlbums = rxSharedPreferences.getString(LGAlbum.PREF_KEY_ALBUMS, "[]");
    }

    @Override
    public Single<List<LGAlbum>> albums() {
        return mLGApi.meta()
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<LGMeta, Observable<List<LGAlbum>>>() {
                    @Override
                    public Observable<List<LGAlbum>> call(final LGMeta lgMeta) {
                        if (lgMeta.version() > readVersion()) {
                            return loadCloudAlbums(lgMeta);
                        } else {
                            return Observable.just(readAlbums());
                        }
                    }
                })
                .toSingle();
    }

    public Observable<List<LGAlbum>> loadCloudAlbums(final LGMeta lgMeta) {
        return Observable.from(lgMeta.parts())
                .flatMap(new Func1<String, Observable<List<LGAlbum>>>() {
                    @Override
                    public Observable<List<LGAlbum>> call(final String onePart) {
                        return mLGApi.onePart(onePart);
                    }
                })
                .toList()
                .map(new Func1<List<List<LGAlbum>>, List<LGAlbum>>() {
                    @Override
                    public List<LGAlbum> call(final List<List<LGAlbum>> albumLists) {
                        final List<LGAlbum> albums = new ArrayList<>();
                        final int size = albumLists.size();
                        for (int i = 0; i < size; i++) {
                            albums.addAll(albumLists.get(i));
                        }
                        return albums;
                    }
                })
                .doOnNext(new Action1<List<LGAlbum>>() {
                    @Override
                    public void call(final List<LGAlbum> albums) {
                        writeVersion(lgMeta.version());
                        writeAlbums(albums);
                    }
                });
    }

    private int readVersion() {
        return mLGVersion.get();
    }

    private void writeVersion(int version) {
        mLGVersion.set(version);
    }

    private List<LGAlbum> readAlbums() {
        return mGson.fromJson(mLGAlbums.get(), new TypeToken<List<LGAlbum>>() {}.getType());
    }

    private void writeAlbums(final List<LGAlbum> albums) {
        mLGAlbums.set(mGson.toJson(albums));
    }
}
