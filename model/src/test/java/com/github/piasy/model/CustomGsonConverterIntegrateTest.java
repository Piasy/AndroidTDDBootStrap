package com.github.piasy.model;

import com.github.piasy.common.android.provider.ProviderModule;
import com.github.piasy.common.android.utils.model.ThreeTenABPDelegate;
import com.github.piasy.common.android.utils.net.CustomGsonConverter;
import com.github.piasy.common.android.utils.tests.BaseThreeTenBPTest;
import com.github.piasy.model.entities.GithubUser;
import com.google.gson.Gson;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import junit.framework.Assert;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import retrofit.converter.ConversionException;
import retrofit.mime.TypedInput;

import static org.mockito.Mockito.mock;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/9.
 */
public class CustomGsonConverterIntegrateTest extends BaseThreeTenBPTest {

    public static final String UTF_8 = "UTF-8";
    private Gson mGson;
    private CustomGsonConverter mCustomGsonConverter;

    @Before
    public void setUp() {
        initThreeTenABP();
        mGson = new ProviderModule().provideGson(mock(ThreeTenABPDelegate.class));
        mCustomGsonConverter = new CustomGsonConverter(mGson);
    }

    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    @Test
    public void testFromBodyAPIError() {
        final TypedInput typedInput = new TypedInput() {
            @Override
            public String mimeType() {
                return UTF_8;
            }

            @Override
            public long length() {
                return 0;
            }

            @Override
            public InputStream in() throws IOException {
                return new ByteArrayInputStream(
                        MockProvider.provideGithubAPIErrorStr().getBytes(UTF_8));
            }
        };

        try {
            mCustomGsonConverter.fromBody(typedInput, GithubUser.class);
            Assert.assertTrue(false);
        } catch (ConversionException e) {
            Assert.assertTrue(true);
        }
    }

    @SuppressWarnings("PMD.DataflowAnomalyAnalysis")
    @Test
    public void testFromBodyNonAPIError() {
        final GithubUser user =
                mGson.fromJson(MockProvider.provideGithubUserStr(), GithubUser.class);
        final TypedInput typedInput = new TypedInput() {
            @Override
            public String mimeType() {
                return UTF_8;
            }

            @Override
            public long length() {
                return 0;
            }

            @Override
            public InputStream in() throws IOException {
                return new ByteArrayInputStream(
                        MockProvider.provideGithubUserStr().getBytes(UTF_8));
            }
        };

        try {
            final Object converted = mCustomGsonConverter.fromBody(typedInput, GithubUser.class);
            Assert.assertTrue(converted instanceof GithubUser);
            final GithubUser convertedUser = (GithubUser) converted;
            JSONAssert.assertEquals(mGson.toJson(user), mGson.toJson(convertedUser), false);
        } catch (ConversionException | JSONException e) {
            Assert.assertTrue(false);
        }
    }
}
