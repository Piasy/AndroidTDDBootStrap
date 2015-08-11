package com.piasy.common.android.utils.net;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import org.apache.commons.io.IOUtils;

import android.text.TextUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.MimeUtil;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

/**
 * Retrofit's GsonConverter

 * Check if it is an api error when get the Response.
 */

public class CustomGsonConverter implements Converter {

    private final Gson mGson;

    private String mEncoding;

    /**
     * Create an instance using the supplied {@link Gson} object for conversion. Encoding to JSON
     * and
     * decoding from JSON (when no charset is specified by a header) will use UTF-8.
     */
    public CustomGsonConverter(Gson gson) {
        this(gson, "UTF-8");
    }

    /**
     * Create an instance using the supplied {@link Gson} object for conversion. Encoding to JSON
     * and
     * decoding from JSON (when no charset is specified by a header) will use the specified
     * mEncoding.
     */
    public CustomGsonConverter(Gson gson, String encoding) {
        this.mGson = gson;
        this.mEncoding = encoding;
    }

    @Override
    public Object fromBody(TypedInput body, Type type) throws ConversionException {
        String charset = mEncoding;
        if (body.mimeType() != null) {
            charset = MimeUtil.parseCharset(body.mimeType(), mEncoding);
        }
        String inputStr;
        try {
            StringWriter writer = new StringWriter();
            IOUtils.copy(body.in(), writer, charset);
            inputStr = writer.toString();
            IOUtils.closeQuietly(body.in());
            try {
                if (inputStr != null) {
                    // is an api error ?
                    GithubAPIError apiError = mGson.fromJson(inputStr, GithubAPIError.class);
                    if (apiError != null && !TextUtils.isEmpty(apiError.getMessage())) {
                        throw apiError;
                    }
                }
            } catch (JsonSyntaxException e) {
                // not an api error
            }

            return mGson.fromJson(inputStr, type);
        } catch (IOException | JsonParseException e) {
            throw new ConversionException(e);
        }
    }

    @Override
    public TypedOutput toBody(Object object) {
        try {
            return new JsonTypedOutput(mGson.toJson(object).getBytes(mEncoding), mEncoding);
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    private static class JsonTypedOutput implements TypedOutput {

        private final byte[] jsonBytes;

        private final String mimeType;

        JsonTypedOutput(byte[] jsonBytes, String encode) {
            this.jsonBytes = jsonBytes;
            this.mimeType = "application/json; charset=" + encode;
        }

        @Override
        public String fileName() {
            return null;
        }

        @Override
        public String mimeType() {
            return mimeType;
        }

        @Override
        public long length() {
            return jsonBytes.length;
        }

        @Override
        public void writeTo(OutputStream out) throws IOException {
            out.write(jsonBytes);
        }
    }
}

