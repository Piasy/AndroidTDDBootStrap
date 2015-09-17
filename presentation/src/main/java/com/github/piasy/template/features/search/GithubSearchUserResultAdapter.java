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

package com.github.piasy.template.features.search;

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
 *
 * Recycler view adapter.
 */
public final class GithubSearchUserResultAdapter
        extends RecyclerView.Adapter<GithubSearchUserResultAdapter.GithubSearchResultVH> {

    private final List<GithubUser> mGithubUsers = new ArrayList<>();
    private final Resources mResources;
    private final EmailUtil mEmailUtil;
    private final Action mAction;

    /**
     * create adapter with needed dependencies.
     *
     * @param resources {@link Resources} to access resource.
     * @param emailUtil used to validate email address.
     * @param action used to perform action.
     */
    public GithubSearchUserResultAdapter(final Resources resources, final EmailUtil emailUtil,
            final Action action) {
        super();
        mResources = resources;
        mEmailUtil = emailUtil;
        this.mAction = action;
    }

    /**
     * add users
     *
     * @param users to be added.
     */
    public void addUsers(@NonNull final List<GithubUser> users) {
        mGithubUsers.clear();
        mGithubUsers.addAll(users);
        notifyDataSetChanged();
    }

    @Override
    public GithubSearchResultVH onCreateViewHolder(final ViewGroup parent, final int type) {
        return new GithubSearchResultVH(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ui_github_search_user_result_item, parent, false), mAction,
                mResources, mEmailUtil);
    }

    @Override
    public void onBindViewHolder(final GithubSearchResultVH vh, final int position) {
        vh.bind(mGithubUsers.get(position));
    }

    @Override
    public int getItemCount() {
        return mGithubUsers.size();
    }

    /**
     * actions to perform on a GithubUser card.
     */
    public interface Action {

        /**
         * view the detail info.
         *
         * @param user user to view detail info.
         */
        void userDetail(GithubUser user);
    }

    /**
     * View holder to hold the item view of RecyclerView.
     */
    static class GithubSearchResultVH extends RecyclerView.ViewHolder
            implements View.OnClickListener {

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

        private final Action mAction;
        private final Resources mResources;
        private final EmailUtil mEmailUtil;

        private GithubUser mGithubUser;

        /**
         * create view holder.
         *
         * @param itemView view to hold.
         * @param action to perform action.
         * @param resources to access resource.
         * @param emailUtil to validate email address.
         */
        GithubSearchResultVH(final View itemView, final Action action, final Resources resources,
                final EmailUtil emailUtil) {
            super(itemView);
            mAction = action;
            mResources = resources;
            mEmailUtil = emailUtil;
            ButterKnife.bind(this, itemView);
            mCardView.setOnClickListener(this);
        }

        /**
         * bind the GithubUser to the view.
         *
         * @param user GithubUser to bind.
         */
        void bind(final GithubUser user) {
            mGithubUser = user;
            mIvAvatar.setImageURI(Uri.parse(mGithubUser.avatar_url()));

            if (GithubUser.GITHUB_USER_TYPE_ORGANIZATION.equals(mGithubUser.type())) {
                mIvUserType.setText(Constants.ICONIFY_ICONS_ORG);
            } else if (GithubUser.GITHUB_USER_TYPE_USER.equals(mGithubUser.type())) {
                mIvUserType.setText(Constants.ICONIFY_ICONS_USER);
            }

            mTvUsername.setText(mGithubUser.login());
            if (!TextUtils.isEmpty(mGithubUser.email()) &&
                    mEmailUtil.isValidEmail(mGithubUser.email())) {
                mTvEmail.setText(mGithubUser.email());
                mTvEmail.setVisibility(View.VISIBLE);
            } else {
                mTvEmail.setVisibility(View.GONE);
            }

            mTvFollowers.setText(
                    String.format(mResources.getString(R.string.github_user_followers_formatter),
                            mGithubUser.followers()));
            mTvFollowing.setText(
                    String.format(mResources.getString(R.string.github_user_following_formatter),
                            mGithubUser.following()));
        }

        @Override
        public void onClick(@NonNull final View v) {
            mAction.userDetail(mGithubUser);
        }
    }
}
