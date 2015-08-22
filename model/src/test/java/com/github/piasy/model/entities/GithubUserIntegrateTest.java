package com.github.piasy.model.entities;

import com.github.piasy.common.android.utils.model.ThreeTenABPDelegate;
import com.github.piasy.common.android.utils.provider.GsonProvider;
import com.github.piasy.common.android.utils.tests.BaseThreeTenBPTest;
import com.github.piasy.model.MockProvider;
import com.google.gson.Gson;
import junit.framework.Assert;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import static org.mockito.Mockito.mock;

/**
 * Created by piasy on 15/8/11.
 */
public class GithubUserIntegrateTest extends BaseThreeTenBPTest {

    private Gson mGson;

    @Before
    public void setUp() {
        initThreeTenABP();
        mGson = GsonProvider.provideGson(mock(ThreeTenABPDelegate.class));
    }

    @Test
    public void testAutoParcelAutoGson() {
        String mock = MockProvider.provideGithubUserStr();
        GithubUser converted = mGson.fromJson(mock, GithubUser.class);
        Assert.assertNotNull(converted);
        try {
            JSONAssert.assertEquals(MockProvider.provideSimplifiedGithubUserStr(),
                    mGson.toJson(converted), false);
        } catch (JSONException e) {
            Assert.assertTrue(false);
        }

        GithubUser built = GithubUser.builder()
                .id(converted.id())
                .login(converted.login())
                .avatar_url(converted.avatar_url())
                .type(converted.type())
                .email(converted.email())
                .followers(converted.followers())
                .following(converted.following())
                .created_at(converted.created_at())
                .build();
        Assert.assertEquals(converted, built);

        GithubUser from = GithubUser.from(built);
        Assert.assertEquals(built, from);
    }
}
