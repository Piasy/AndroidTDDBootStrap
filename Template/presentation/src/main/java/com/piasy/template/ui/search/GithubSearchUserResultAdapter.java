package com.piasy.template.ui.search;

import com.facebook.drawee.view.SimpleDraweeView;
import com.piasy.model.entities.GithubUser;
import com.piasy.template.R;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Piasy{github.com/Piasy} on 15/8/3.
 */
public class GithubSearchUserResultAdapter
        extends RecyclerView.Adapter<GithubSearchUserResultAdapter.GithubSearchResultVH> {

    private final List<GithubUser> mGithubUsers = new ArrayList<>();

    public void addUsers(@NonNull List<GithubUser> users) {
        for (int i = 0; i < users.size(); i++) {
            int index = mGithubUsers.indexOf(users.get(i));
            if (index != -1) {
                mGithubUsers.set(index, users.get(i));
            } else {
                mGithubUsers.add(users.get(i));
            }
        }

        if (!users.isEmpty()) {
            notifyDataSetChanged();
        }
    }

    @Override
    public GithubSearchResultVH onCreateViewHolder(ViewGroup parent, int type) {
        return new GithubSearchResultVH(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.ui_github_search_user_result_item, parent, false));
    }

    @Override
    public void onBindViewHolder(GithubSearchResultVH vh, int position) {
        GithubUser user = mGithubUsers.get(position);
        vh.mIvAvatar.setImageURI(Uri.parse(user.getAvatar_url()));
        vh.mTvUsername.setText(user.getLogin());
        vh.mTvScore.setText(String.valueOf(user.getScore()));
        if (GithubUser.GithubUserType.ORGANIZATION.equals(user.getType())) {
            vh.mIvUserType.setImageResource(R.drawable.ic_github_user_type_org);
        } else if (GithubUser.GithubUserType.USER.equals(user.getType())) {
            vh.mIvUserType.setImageResource(R.drawable.ic_github_user_type_user);
        }
    }

    @Override
    public int getItemCount() {
        return mGithubUsers.size();
    }

    static class GithubSearchResultVH extends RecyclerView.ViewHolder {

        @Bind(R.id.iv_avatar)
        SimpleDraweeView mIvAvatar;
        @Bind(R.id.iv_user_type)
        ImageView mIvUserType;
        @Bind(R.id.tv_username)
        TextView mTvUsername;
        @Bind(R.id.tv_score)
        TextView mTvScore;

        public GithubSearchResultVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
