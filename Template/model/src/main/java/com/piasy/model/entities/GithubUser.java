package com.piasy.model.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.piasy.model.dao.TemplateDB;
import com.promegu.xlog.base.XLog;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
@XLog
@Table(databaseName = TemplateDB.NAME, tableName = "GithubUsers")
public class GithubUser extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    private Long id;

    @Column(name = "username")
    @Expose
    private String login;

    @Column
    @Expose
    @SerializedName("id")
    private Long uid;

    @Column
    @Expose
    private String avatar_url;

    @Column
    @Expose
    private String gravatar_id;

    @Column
    @Expose
    private String url;

    @Column
    @Expose
    private String html_url;

    @Column
    @Expose
    private String followers_url;

    @Column
    @Expose
    private String subscriptions_url;

    @Column
    @Expose
    private String repos_url;

    @Column
    @Expose
    private String type;

    @Column
    @Expose
    private Float score;

    public void copy(GithubUser that) {
        this.login = that.login;
        this.uid = that.uid;
        this.avatar_url = that.avatar_url;
        this.gravatar_id = that.gravatar_id;
        this.url = that.url;
        this.html_url = that.html_url;
        this.followers_url = that.followers_url;
        this.subscriptions_url = that.subscriptions_url;
        this.repos_url = that.repos_url;
        this.type = that.type;
        this.score = that.score;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (((Object) this).getClass() != obj.getClass()) {
            return false;
        }
        final GithubUser other = (GithubUser) obj;

        if (this.uid == null) {
            return other.uid == null;
        }

        return (other.uid != null) && this.uid.equals(other.uid);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.uid == null) ? 0 : this.uid.hashCode());
        return result;
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public interface GithubUserType {
        String USER = "User";
        String ORGANIZATION = "Organization";
    }
}
