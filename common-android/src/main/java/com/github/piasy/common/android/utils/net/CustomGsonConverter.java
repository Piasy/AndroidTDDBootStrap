/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Piasy
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

package com.github.piasy.common.android.utils.net;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import org.apache.commons.io.IOUtils;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.MimeUtil;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;
import timber.log.Timber;

/**
 * Retrofit's GsonConverter
 *
 * Check if it is an api error when get the Response.
 */

public class CustomGsonConverter implements Converter {

    private final Gson mGson;
    private String mEncoding;

    /**
     * Create an instance using the supplied {@link Gson} object for conversion. Encoding to JSON
     * and decoding from JSON (when no charset is specified by a header) will use UTF-8.
     *
     * @param gson supplied {@link Gson} object for conversion.
     */
    public CustomGsonConverter(final Gson gson) {
        this(gson, "UTF-8");
    }

    /**
     * Create an instance using the supplied {@link Gson} object for conversion. Encoding to JSON
     * and decoding from JSON (when no charset is specified by a header) will use the specified
     * mEncoding.
     *
     * @param gson supplied {@link Gson} object for conversion.
     * @param encoding encoding used in conversion.
     */
    public CustomGsonConverter(final Gson gson, final String encoding) {
        this.mGson = gson;
        this.mEncoding = encoding;
    }

    @Override
    public Object fromBody(final TypedInput body, final Type type) throws ConversionException {
        final String charset = body.mimeType() == null ? mEncoding :
                MimeUtil.parseCharset(body.mimeType(), mEncoding);
        String inputStr;
        try {
            final StringWriter writer = new StringWriter();
            IOUtils.copy(body.in(), writer, charset);
            inputStr = writer.toString();
            IOUtils.closeQuietly(body.in());
            try {
                // is an api error ?
                final GithubAPIError apiError = mGson.fromJson(inputStr, GithubAPIError.class);
                if (apiError != null && !TextUtils.isEmpty(apiError.getMessage())) {
                    throw apiError;
                }
            } catch (JsonSyntaxException e) {
                // not an api error
                Timber.e("JsonSyntaxException at api from body: " + e.toString());
            }

            return mGson.fromJson(inputStr, type);
        } catch (IOException | JsonParseException e) {
            throw new ConversionException(e);
        }
    }

    @Override
    public TypedOutput toBody(final Object object) {
        try {
            return new JsonTypedOutput(mGson.toJson(object).getBytes(mEncoding), mEncoding);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    private static class JsonTypedOutput implements TypedOutput {

        private final byte[] mJsonBytes;

        private final String mMimeType;

        JsonTypedOutput(final byte[] jsonBytes, final String encode) {
            this.mJsonBytes = jsonBytes;
            this.mMimeType = "application/json; charset=" + encode;
        }

        @Override
        public String fileName() {
            return null;
        }

        @Override
        public String mimeType() {
            return mMimeType;
        }

        @Override
        public long length() {
            return mJsonBytes.length;
        }

        @Override
        public void writeTo(final OutputStream out) throws IOException {
            out.write(mJsonBytes);
        }
    }
}

