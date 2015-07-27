package com.piasy.model.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.promegu.xlog.base.XLog;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
@XLog
public class GithubUser {

    @Expose
    private String login;

    @Expose
    @SerializedName("id")
    private Long uid;

    @Expose
    private String avatar_url;

    @Expose
    private String gravatar_id;

    @Expose
    private String url;

    @Expose
    private String html_url;

    @Expose
    private String followers_url;

    @Expose
    private String subscriptions_url;

    @Expose
    private String repos_url;

    @Expose
    private String type;
    @Expose
    private Float score;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getGravatar_id() {
        return gravatar_id;
    }

    public void setGravatar_id(String gravatar_id) {
        this.gravatar_id = gravatar_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getFollowers_url() {
        return followers_url;
    }

    public void setFollowers_url(String followers_url) {
        this.followers_url = followers_url;
    }

    public String getSubscriptions_url() {
        return subscriptions_url;
    }

    public void setSubscriptions_url(String subscriptions_url) {
        this.subscriptions_url = subscriptions_url;
    }

    public String getRepos_url() {
        return repos_url;
    }

    public void setRepos_url(String repos_url) {
        this.repos_url = repos_url;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public interface GithubUserType {
        String USER = "User";
        String ORGANIZATION = "Organization";
    }
}
