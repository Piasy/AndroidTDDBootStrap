package com.github.piasy.template.ui.search;

import android.content.res.Resources;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.piasy.common.Constants;
import com.github.piasy.common.utils.EmailUtil;
import com.github.piasy.model.entities.GithubUser;
import com.github.piasy.template.R;
import com.joanzapata.iconify.widget.IconTextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/3.
 */
public class GithubSearchUserResultAdapter
        extends RecyclerView.Adapter<GithubSearchUserResultAdapter.GithubSearchResultVH> {

    private final List<GithubUser> mGithubUsers = new ArrayList<>();
    private final Resources mResources;
    private final EmailUtil mEmailUtil;

    public interface Action {
        void userDetail(GithubUser user);
    }

    private final Action mAction;

    public GithubSearchUserResultAdapter(Resources resources, EmailUtil emailUtil, Action mAction) {
        mResources = resources;
        mEmailUtil = emailUtil;
        this.mAction = mAction;
    }

    public void addUsers(@NonNull List<GithubUser> users) {
        mGithubUsers.clear();
        mGithubUsers.addAll(users);
        notifyDataSetChanged();
    }

    @Override
    public GithubSearchResultVH onCreateViewHolder(ViewGroup parent, int type) {
        return new GithubSearchResultVH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ui_github_search_user_result_item, parent, false));
    }

    @Override
    public void onBindViewHolder(GithubSearchResultVH vh, int position) {
        GithubUser user = mGithubUsers.get(position);
        vh.mIvAvatar.setImageURI(Uri.parse(user.avatar_url()));

        if (GithubUser.GithubUserType.ORGANIZATION.equals(user.type())) {
            vh.mIvUserType.setText(Constants.ICONIFY_ICONS_ORG);
        } else if (GithubUser.GithubUserType.USER.equals(user.type())) {
            vh.mIvUserType.setText(Constants.ICONIFY_ICONS_USER);
        }

        vh.mTvUsername.setText(user.login());
        if (!TextUtils.isEmpty(user.email()) && mEmailUtil.isValidEmail(user.email())) {
            vh.mTvEmail.setText(user.email());
            vh.mTvEmail.setVisibility(View.VISIBLE);
        } else {
            vh.mTvEmail.setVisibility(View.GONE);
        }

        vh.mTvFollowers.setText(
                String.format(mResources.getString(R.string.github_user_followers_formatter),
                        user.followers()));
        vh.mTvFollowing.setText(
                String.format(mResources.getString(R.string.github_user_following_formatter),
                        user.following()));

        vh.mCardView.setOnClickListener(v -> mAction.userDetail(user));
    }

    @Override
    public int getItemCount() {
        return mGithubUsers.size();
    }

    static class GithubSearchResultVH extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_avatar)
        SimpleDraweeView mIvAvatar;
        @Bind(R.id.icon_tv_user_type)
        IconTextView mIvUserType;
        @Bind(R.id.tv_username)
        TextView mTvUsername;
        @Bind(R.id.tv_email)
        TextView mTvEmail;
        @Bind(R.id.tv_followers)
        TextView mTvFollowers;
        @Bind(R.id.tv_following)
        TextView mTvFollowing;

        @Bind(R.id.card_view)
        CardView mCardView;

        public GithubSearchResultVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
