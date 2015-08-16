package com.piasy.model.entities;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.piasy.common.android.utils.provider.GsonProvider;
import com.piasy.common.android.utils.tests.BaseThreeTenBPTest;
import com.piasy.model.MockProvider;
import junit.framework.Assert;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/11.
 */
public class GithubSearchResultTest extends BaseThreeTenBPTest {

    private Gson mGson;

    @Before
    public void setUp() {
        initThreeTenABP();
        mGson = GsonProvider.provideGson();
    }

    @Test
    public void testSerialize() {
        GithubSearchResult<GithubUser> converted =
                mGson.fromJson(MockProvider.provideSimplifiedGithubUserSearchResultStr(),
                        new TypeToken<GithubSearchResult<GithubUser>>() {
                        }.getType());
        try {
            JSONAssert.assertEquals(MockProvider.provideSimplifiedGithubUserSearchResultStr(),
                    mGson.toJson(converted), false);
            Assert.assertTrue(true);
        } catch (JSONException e) {
            Assert.assertTrue(false);
        }
    }
}
