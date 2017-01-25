/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Piasy
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

package com.github.piasy.octostars.features.trending;

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