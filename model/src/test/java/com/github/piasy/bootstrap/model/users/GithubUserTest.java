package com.github.piasy.bootstrap.model.users;

import com.github.piasy.bootstrap.base.model.provider.GsonProviderExposure;
import com.github.piasy.bootstrap.testbase.rules.ThreeTenBPRule;
import com.google.gson.Gson;
import junit.framework.Assert;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

/**
 * Created by piasy on 15/8/11.
 */
public class GithubUserTest {

    @Rule
    public ThreeTenBPRule mThreeTenBPRule = ThreeTenBPRule.junitTest();

    private Gson mGson;

    @Before
    public void setUp() {
        mGson = GsonProviderExposure.exposeGson();
    }

    @Test
    public void testAutoParcelAutoGson() {
        final String mock = provideGithubUserStr();
        final GithubUser converted = mGson.fromJson(mock, GithubUser.class);
        Assert.assertNotNull(converted);
        try {
            JSONAssert.assertEquals(provideSimplifiedGithubUserStr(),
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

    private String provideGithubUserStr() {
        return "{\"login\":\"Piasy\",\"id\":3098704,\"avatar_url\":\"https://avatars" +
               ".githubusercontent.com/u/3098704?v=3\",\"gravatar_id\":\"\"," +
               "\"url\":\"https://api.github.com/users/Piasy\",\"html_url\":\"https://github" +
               ".com/Piasy\",\"followers_url\":\"https://api.github.com/users/Piasy/followers\"," +
               "\"following_url\":\"https://api.github.com/users/Piasy/following{/other_user}\"," +
               "\"gists_url\":\"https://api.github.com/users/Piasy/gists{/gist_id}\"," +
               "\"starred_url\":\"https://api.github.com/users/Piasy/starred{/owner}{/repo}\"," +
               "\"subscriptions_url\":\"https://api.github.com/users/Piasy/subscriptions\"," +
               "\"organizations_url\":\"https://api.github.com/users/Piasy/orgs\"," +
               "\"repos_url\":\"https://api.github.com/users/Piasy/repos\"," +
               "\"events_url\":\"https://api.github.com/users/Piasy/events{/privacy}\"," +
               "\"received_events_url\":\"https://api.github.com/users/Piasy/received_events\"," +
               "\"type\":\"User\",\"site_admin\":false,\"name\":\"Xu Jianlin\"," +
               "\"company\":\"YOLO\",\"blog\":\"http://piasy.github.io/\"," +
               "\"location\":\"Beijing, China\",\"email\":\"xz4215@gmail.com\"," +
               "\"hireable\":true,\"bio\":null,\"public_repos\":21,\"public_gists\":3," +
               "\"followers\":3,\"following\":25,\"created_at\":\"2012-12-21T14:23:30Z\"," +
               "\"updated_at\":\"2015-07-23T07:27:29Z\"}";
    }

    private String provideSimplifiedGithubUserStr() {
        return "{\"login\":\"Piasy\",\"avatar_url\":\"https://avatars.githubusercontent" +
               ".com/u/3098704?v=3\",\"type\":\"User\",\"created_at\":\"2012-12-21T14:23:30Z\"}";
    }
}
