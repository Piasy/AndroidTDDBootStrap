package com.github.piasy.gh.model.users;

import com.github.piasy.base.model.provider.GsonProviderExposure;
import com.github.piasy.base.test.BaseThreeTenBPTest;
import com.github.piasy.base.test.MockProvider;
import com.google.gson.Gson;
import junit.framework.Assert;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

/**
 * Created by piasy on 15/8/11.
 */
public class GithubUserTest extends BaseThreeTenBPTest {

    private Gson mGson;

    @Before
    public void setUp() {
        initThreeTenBP();
        mGson = GsonProviderExposure.exposeGson();
    }

    @Test
    public void testAutoParcelAutoGson() {
        final String mock = MockProvider.provideGithubUserStr();
        final GithubUser converted = mGson.fromJson(mock, GithubUser.class);
        Assert.assertNotNull(converted);
        try {
            JSONAssert.assertEquals(MockProvider.provideSimplifiedGithubUserStr(),
                    mGson.toJson(converted), false);
        } catch (JSONException e) {
            Assert.assertTrue(false);
        }

        final GithubUser built = GithubUser.builder()
                .id(converted.id())
                .login(converted.login())
                .avatar_url(converted.avatar_url())
                .type(converted.type())
                .created_at(converted.created_at())
                .build();
        Assert.assertEquals(converted, built);
    }
}
