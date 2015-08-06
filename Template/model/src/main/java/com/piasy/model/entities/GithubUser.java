package com.piasy.model.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.piasy.model.dao.TemplateDB;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.Date;

/**
 * Created by Piasy{github.com/Piasy} on 15/7/23.
 */
@Table(databaseName = TemplateDB.NAME, tableName = "GithubUsers")
public class GithubUser extends BaseModel {

    @Column
    @PrimaryKey(autoincrement = true)
    private long id;

    @Column(name = "username")
    @Expose
    private String login;

    @Column
    @Expose
    @SerializedName("id")
    private long uid;

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
    private String name;

    @Column
    @Expose
    private String company;

    @Column
    @Expose
    private String blog;

    @Column
    @Expose
    private String location;

    @Column
    @Expose
    private String email;

    @Column
    @Expose
    private int public_repos;

    @Column
    @Expose
    private int public_gists;

    @Column
    @Expose
    private int followers;

    @Column
    @Expose
    private int following;

    @Column
    @Expose
    private Date created_at;

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
        this.name = that.name;
        this.company = that.company;
        this.blog = that.blog;
        this.location = that.location;
        this.email = that.email;
        this.public_repos = that.public_repos;
        this.public_gists = that.public_gists;
        this.followers = that.followers;
        this.following = that.following;
        this.created_at = that.created_at;
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

        if (this.uid == 0) {
            return other.uid == 0;
        }

        return (other.uid != 0) && this.uid == other.uid;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.uid == 0) ? 0 : Long.valueOf(this.uid).hashCode());
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

    public long getUid() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPublic_repos() {
        return public_repos;
    }

    public void setPublic_repos(int public_repos) {
        this.public_repos = public_repos;
    }

    public int getPublic_gists() {
        return public_gists;
    }

    public void setPublic_gists(int public_gists) {
        this.public_gists = public_gists;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public interface GithubUserType {
        String USER = "User";
        String ORGANIZATION = "Organization";
    }
}
