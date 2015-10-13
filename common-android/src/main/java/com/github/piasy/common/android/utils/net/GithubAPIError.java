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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/9.
 */
public class GithubAPIError extends Throwable {

    @Expose
    @SerializedName("message")
    private String mErrorMessage;

    @Expose
    @SerializedName("documentation_url")
    private String mDocumentationUrl;

    @Expose
    @SerializedName("errors")
    private List<GithubError> mErrors;

    /**
     * Get Error message for this API error.
     *
     * @return message for this API error.
     */
    @NonNull
    public String getErrorMessage() {
        return mErrorMessage;
    }

    /**
     * Get Error documentation_url for this API error.
     *
     * @return documentation_url for this API error.
     */
    @Nullable
    public String getDocumentationUrl() {
        return mDocumentationUrl;
    }

    /**
     * Get Error errors list for this API error.
     *
     * @return errors list for this API error.
     */
    @Nullable
    public List<GithubError> getErrors() {
        return mErrors;
    }

    /**
     * The Github Error within the {@link GithubAPIError}.
     */
    public static class GithubError {

        @Expose
        @SerializedName("resource")
        private String mResource;

        @Expose
        @SerializedName("field")
        private String mField;

        @Expose
        @SerializedName("code")
        private String mCode;

        /**
         * get resource of this Error
         *
         * @return resource of this Error
         */
        public String getResource() {
            return mResource;
        }

        /**
         * get field of this Error
         *
         * @return field of this Error
         */
        public String getField() {
            return mField;
        }

        /**
         * get code of this Error
         *
         * @return code of this Error
         */
        public String getCode() {
            return mCode;
        }
    }
}
