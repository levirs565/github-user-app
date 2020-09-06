package com.levirs.githubuser.feature.userlist

import android.app.ActivityOptions
import android.content.Intent
import android.view.View
import com.levirs.githubuser.R
import com.levirs.githubuser.common.model.User
import com.levirs.githubuser.common.ui.userlist.AbsUserListFragment
import com.levirs.githubuser.feature.detail.DetailActivity

abstract class UserListFragment: AbsUserListFragment() {
    override fun onItemClick(view: View, user: User) {
        val avatarView = view.findViewById<View>(R.id.img_avatar)
        val avatarTransitionName = avatarView.transitionName
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_USER, user)
        intent.putExtra(DetailActivity.EXTRA_AVATAR_TRANSITION_NAME, avatarTransitionName)
        val options = ActivityOptions
            .makeSceneTransitionAnimation(
                requireActivity(),
                avatarView, avatarTransitionName
            )
        startActivity(intent, options.toBundle())
    }
}