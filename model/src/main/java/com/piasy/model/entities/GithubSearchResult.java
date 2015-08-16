package com.piasy.model.entities;

import android.support.annotation.NonNull;
import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
public class GithubSearchResult<D> {
    @Expose
    private Integer total_count;

    @Expose
    private Boolean incomplete_results;

    @NonNull
    @Expose
    private List<D> items;

    public static <T> GithubSearchResult<T> from(GithubSearchResult<T> result) {
        GithubSearchResult<T> copy = new GithubSearchResult<>();
        copy.total_count = result.total_count;
        copy.incomplete_results = result.incomplete_results;
        copy.items = new ArrayList<>(result.items);
        return copy;
    }

    public Integer getTotal_count() {
        return total_count;
    }

    public void setTotal_count(Integer total_count) {
        this.total_count = total_count;
    }

    public Boolean getIncomplete_results() {
        return incomplete_results == null ? false : incomplete_results;
    }

    public void setIncomplete_results(Boolean incomplete_results) {
        this.incomplete_results = incomplete_results;
    }

    @NonNull
    public List<D> getItems() {
        return items;
    }

    public void setItems(@NonNull List<D> items) {
        this.items = items;
    }
}
