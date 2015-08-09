package com.piasy.model.entities;

import com.google.gson.annotations.Expose;

import java.util.List;

import retrofit.converter.ConversionException;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/9.
 */
public class GithubAPIError extends ConversionException {

    @Expose
    private String message;

    @Expose
    private String documentation_url;

    @Expose
    private List<GithubError> errors;

    public GithubAPIError(String message) {
        super(message);
    }

    public GithubAPIError(String message, Throwable throwable) {
        super(message, throwable);
    }

    public GithubAPIError(Throwable throwable) {
        super(throwable);
    }

    public List<GithubError> getErrors() {
        return errors;
    }

    public void setErrors(List<GithubError> errors) {
        this.errors = errors;
    }

    public static class GithubError {

        @Expose
        private String resource;

        @Expose
        private String field;

        @Expose
        private String code;

        public String getResource() {
            return resource;
        }

        public void setResource(String resource) {
            this.resource = resource;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDocumentation_url() {
        return documentation_url;
    }

    public void setDocumentation_url(String documentation_url) {
        this.documentation_url = documentation_url;
    }
}
