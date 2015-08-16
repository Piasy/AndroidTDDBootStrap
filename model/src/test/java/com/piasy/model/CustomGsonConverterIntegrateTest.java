package com.piasy.model;

import com.google.gson.Gson;
import com.piasy.common.android.utils.tests.BaseThreeTenBPTest;
import com.piasy.common.android.utils.net.CustomGsonConverter;
import com.piasy.common.android.utils.provider.GsonProvider;
import com.piasy.model.entities.GithubUser;
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

/**
 * Created by Piasy{github.com/Piasy} on 15/8/9.
 */
public class CustomGsonConverterIntegrateTest extends BaseThreeTenBPTest {

    private Gson mGson;
    private CustomGsonConverter mCustomGsonConverter;

    @Before
    public void setUp() {
        initThreeTenABP();
        mGson = GsonProvider.provideGson();
        mCustomGsonConverter = new CustomGsonConverter(mGson);
    }

    @Test
    public void testFromBodyAPIError() {
        TypedInput typedInput = new TypedInput() {
            @Override
            public String mimeType() {
                return "UTF-8";
            }

            @Override
            public long length() {
                return 0;
            }

            @Override
            public InputStream in() throws IOException {
                return new ByteArrayInputStream(MockProvider.provideGithubAPIErrorStr().getBytes());
            }
        };

        try {
            mCustomGsonConverter.fromBody(typedInput, GithubUser.class);
            Assert.assertTrue(false);
        } catch (ConversionException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testFromBodyNonAPIError() {
        GithubUser user = mGson.fromJson(MockProvider.provideGithubUserStr(), GithubUser.class);
        TypedInput typedInput = new TypedInput() {
            @Override
            public String mimeType() {
                return "UTF-8";
            }

            @Override
            public long length() {
                return 0;
            }

            @Override
            public InputStream in() throws IOException {
                return new ByteArrayInputStream(MockProvider.provideGithubUserStr().getBytes());
            }
        };

        try {
            Object converted = mCustomGsonConverter.fromBody(typedInput, GithubUser.class);
            Assert.assertTrue(converted instanceof GithubUser);
            GithubUser convertedUser = (GithubUser) converted;
            JSONAssert.assertEquals(mGson.toJson(user), mGson.toJson(convertedUser), false);
        } catch (ConversionException | JSONException e) {
            Assert.assertTrue(false);
        }
    }
}
