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

package com.github.piasy.common.android;

import com.github.piasy.common.android.provider.GsonProviderExposure;
import com.github.piasy.common.android.utils.tests.BaseThreeTenBPTest;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import retrofit.http.GET;
import retrofit.mock.MockRetrofit;
import retrofit.mock.NetworkBehavior;
import retrofit.mock.RxJavaBehaviorAdapter;
import rx.Observable;
import rx.Subscriber;
import rx.observers.TestSubscriber;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Piasy{github.com/Piasy} on 15/10/14.
 */
public class RetrofitIntegrateTest extends BaseThreeTenBPTest {
    static class Person {
        @SerializedName("name")
        private String mName;

        @SerializedName("age")
        private int mAge;
    }

    interface PersonService {
        @GET("/")
        Observable<Person> getPerson();
    }

    private final NetworkBehavior mNetworkBehavior = NetworkBehavior.create(new Random(2847));
    private PersonService mPersonService;
    private Person mMockPerson;
    private Gson mGson;

    @Before
    public void setUp() {
        initThreeTenBP();
        mGson = GsonProviderExposure.exposeGson();
        mMockPerson = mGson.fromJson(MockProvider.providePersonStr(), Person.class);
    }

    @Test
    public void testObservableSuccessAfterDelay() {
        PersonService mockService = new PersonService() {
            @Override
            public Observable<Person> getPerson() {
                return Observable.just(mMockPerson);
            }
        };

        NetworkBehavior.Adapter<?> adapter = RxJavaBehaviorAdapter.create();
        MockRetrofit mockRetrofit = new MockRetrofit(mNetworkBehavior, adapter);
        mPersonService = mockRetrofit.create(PersonService.class, mockService);

        mNetworkBehavior.setDelay(100, MILLISECONDS);
        mNetworkBehavior.setVariancePercent(0);
        mNetworkBehavior.setFailurePercent(0);

        Observable<Person> personObservable = mPersonService.getPerson();
        TestSubscriber<Person> testSubscriber = new TestSubscriber<>();
        personObservable.subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();

        testSubscriber.assertCompleted();
        testSubscriber.assertNoErrors();
        List<Person> persons = testSubscriber.getOnNextEvents();
        Assert.assertEquals(1, persons.size());
        Assert.assertEquals(mMockPerson, persons.get(0));
    }

    @Rule
    public final MockWebServer mMockWebServer = new MockWebServer();

    @Test
    public void testNetworkReturnErrorMessage() throws InterruptedException {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(mMockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mPersonService = retrofit.create(PersonService.class);

        // if not set response code > 300, onError won't be invoked, instead, onNext will be
        // invoked with null
        mMockWebServer.enqueue(new MockResponse().setBody(MockProvider.provideGithubAPIErrorStr()));
        final CountDownLatch latch = new CountDownLatch(1);

        mPersonService.getPerson().subscribe(new Subscriber<Person>() {
            @Override
            public void onCompleted() {
                latch.countDown();
            }

            @Override
            public void onError(Throwable e) {
                latch.countDown();
            }

            @Override
            public void onNext(Person person) {
                latch.countDown();
            }
        });
        latch.await(1, SECONDS);
    }

    @Test
    public void testNetworkReturn404() throws InterruptedException {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(mMockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mPersonService = retrofit.create(PersonService.class);

        // if set response code > 300, onError will be invoked
        mMockWebServer.enqueue(new MockResponse().setStatus("HTTP/1.1 404 Not Found"));
        final CountDownLatch latch = new CountDownLatch(1);

        mPersonService.getPerson().subscribe(new Subscriber<Person>() {
            @Override
            public void onCompleted() {
                latch.countDown();
            }

            @Override
            public void onError(Throwable e) {
                latch.countDown();
            }

            @Override
            public void onNext(Person person) {
                latch.countDown();
            }
        });
        latch.await(1, SECONDS);
    }
}
