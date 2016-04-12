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

import android.preference.PreferenceManager;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import com.f2prateek.rx.preferences.RxSharedPreferences;
import com.github.piasy.base.model.provider.GsonProviderExposure;
import com.github.piasy.base.test.BaseThreeTenBPAndroidTest;
import com.github.piasy.base.test.MockProvider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Piasy{github.com/Piasy} on 16/4/12.
 */
@SuppressWarnings("unchecked")
@RunWith(AndroidJUnit4.class)
public class LGDataManagerImplTest extends BaseThreeTenBPAndroidTest {
    private RxSharedPreferences mRxSharedPreferences;
    private LGApi mLGApi;
    private Gson mGson;

    private LGMeta mLGMetaV1;
    private LGMeta mLGMetaV2;
    private List<LGAlbum> mAlbumsV1;
    private List<LGAlbum> mAlbumsV2;

    @Before
    public void setUp() {
        initThreeTenABP(InstrumentationRegistry.getContext());
        mRxSharedPreferences = RxSharedPreferences.create(
                PreferenceManager.getDefaultSharedPreferences(
                        InstrumentationRegistry.getContext()));
        mLGApi = mock(LGApi.class);
        mGson = GsonProviderExposure.exposeGson();

        mLGMetaV1 = mGson.fromJson(MockProvider.provideLGMetaV1(), LGMeta.class);
        mLGMetaV2 = mGson.fromJson(MockProvider.provideLGMetaV2(), LGMeta.class);
        mAlbumsV1 = mGson.fromJson(MockProvider.provideLGAlbumsV1(),
                new TypeToken<List<LGAlbum>>() {}.getType());
        mAlbumsV2 = mGson.fromJson(MockProvider.provideLGAlbumsV2(),
                new TypeToken<List<LGAlbum>>() {}.getType());
    }

    @Test
    public void testAlbums() {
        final LGDataManager lgDataManager =
                new LGDataManagerImpl(mLGApi, mRxSharedPreferences, mGson);

        when(mLGApi.meta()).thenReturn(Observable.just(mLGMetaV1), Observable.just(mLGMetaV2));
        when(mLGApi.onePart(anyString())).thenReturn(Observable.just(mAlbumsV1),
                Observable.just(mAlbumsV2));

        TestSubscriber<List<LGAlbum>> subscriber = new TestSubscriber<>();
        lgDataManager.albums().subscribe(subscriber);
        subscriber.awaitTerminalEvent();

        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertReceivedOnNext(Collections.singletonList(mAlbumsV1));

        subscriber = new TestSubscriber<>();
        lgDataManager.albums().subscribe(subscriber);
        subscriber.awaitTerminalEvent();

        subscriber.assertCompleted();
        subscriber.assertNoErrors();
        subscriber.assertReceivedOnNext(Collections.singletonList(mAlbumsV2));
    }
}
