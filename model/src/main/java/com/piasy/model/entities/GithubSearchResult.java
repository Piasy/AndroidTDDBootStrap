package com.piasy.model.entities;

import com.google.gson.annotations.Expose;
import java.util.List;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
public class GithubSearchResult<D> {
    @Expose
    private Integer total_count;

    @Expose
    private Boolean incomplete_results;

    @Expose
    private List<D> items;

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

    public List<D> getItems() {
        return items;
    }

    public void setItems(List<D> items) {
        this.items = items;
    }
}
