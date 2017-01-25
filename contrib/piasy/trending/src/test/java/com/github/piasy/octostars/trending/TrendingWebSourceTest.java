package com.github.piasy.octostars.trending;

import com.github.piasy.bootstrap.testbase.TestUtil;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import okhttp3.OkHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by Piasy{github.com/Piasy} on 23/01/2017.
 */
public class TrendingWebSourceTest {
    @Rule
    public MockitoRule mMockitoRule = MockitoJUnit.rule();

    @Mock
    OkHttpClient mOkHttpClient;

    @Test
    public void parseTrending() throws Exception {
        List<String> expected = Arrays.asList(
                "aa112901/remusic",
                "harjot-oberai/MusicDNA",
                "youlookwhat/CloudReader",
                "mancj/SlideUp-Android",
                "Tencent/tinker",
                "ReactiveX/RxJava",
                "NYTimes/Store",
                "Blankj/AndroidUtilCode",
                "spring-projects/spring-boot",
                "LuckSiege/PictureSelector",
                "square/retrofit",
                "garretyoder/Colorful",
                "alibaba/ARouter",
                "square/okhttp",
                "google/guava",
                "GrenderG/Toasty",
                "Meituan-Dianping/walle",
                "elastic/elasticsearch",
                "daniel-stoneuk/material-about-library",
                "bumptech/glide",
                "PhilJay/MPAndroidChart",
                "spring-projects/spring-framework",
                "iluwatar/java-design-patterns",
                "JakeWharton/butterknife",
                "JetradarMobile/desertplaceholder"
        );

        File html = new File(TestUtil.projectRoot()
                             + "contrib/piasy/trending/src/test/assets/https___github"
                             + ".com_trending_java_since=monthly.html");
        Document document = Jsoup.parse(html, "utf-8");

        TrendingWebSource webSource = new TrendingWebSource(mOkHttpClient);
        List<String> trending = webSource.parseTrending(document);

        assertThat(trending)
                .containsExactlyElementsIn(expected)
                .inOrder();
    }
}