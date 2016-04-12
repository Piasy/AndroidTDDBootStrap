/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Piasy
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

package com.github.piasy.lg.glide;

import android.os.Environment;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.github.piasy.lg.BuildConfig;
import java.io.File;

/**
 * Created by Piasy{github.com/Piasy} on 16/4/12.
 */
public class LgCacheFactory extends DiskLruCacheFactory {

    private static final int DISK_CACHE_SIZE = DiskCache.Factory.DEFAULT_DISK_CACHE_SIZE * 8;

    public LgCacheFactory() {
        super(new CacheDirectoryGetter() {
            @Override
            public File getCacheDirectory() {
                final File cacheDirectory =
                        new File(Environment.getExternalStorageDirectory() + File.separator +
                                BuildConfig.APPLICATION_ID);
                if (!cacheDirectory.exists() && !cacheDirectory.mkdir()) {
                    return null;
                }
                return cacheDirectory;
            }
        }, DISK_CACHE_SIZE);
    }
}
