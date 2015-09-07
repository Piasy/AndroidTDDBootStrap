package com.github.piasy.model.entities;

import com.github.piasy.common.android.provider.GsonProvider;
import com.github.piasy.common.android.utils.tests.BaseThreeTenBPTest;
import com.github.piasy.model.MockProvider;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import junit.framework.Assert;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/11.
 */
public class GithubUserSearchResultTest extends BaseThreeTenBPTest {

    private Gson mGson;

    @Before
    public void setUp() {
        initThreeTenABP();
        mGson = GsonProvider.provideGson();
    }

    @Test
    public void testSerialize() {
        GithubUserSearchResult converted =
                mGson.fromJson(MockProvider.provideSimplifiedGithubUserSearchResultStr(),
                        new TypeToken<GithubUserSearchResult>() {
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
