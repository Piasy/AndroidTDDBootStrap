package com.piasy.common.android.utils.net;

import com.google.gson.Gson;
import com.piasy.common.android.MockProvider;
import com.piasy.common.android.utils.provider.GsonProvider;
import junit.framework.Assert;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/11.
 */
public class GithubAPIErrorTest {

    private Gson mGson;

    @Before
    public void setUp() {
        mGson = GsonProvider.provideGson();
    }

    @Test
    public void testSerialize() {
        GithubAPIError converted =
                mGson.fromJson(MockProvider.provideGithubAPIErrorStr(), GithubAPIError.class);
        try {
            JSONAssert.assertEquals(MockProvider.provideGithubAPIErrorStr(),
                    mGson.toJson(converted), false);
            Assert.assertTrue(true);
        } catch (JSONException e) {
            Assert.assertTrue(false);
        }
    }
}
