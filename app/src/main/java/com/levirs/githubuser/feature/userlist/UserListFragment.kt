package com.levirs.githubuser.feature.userlist

import android.content.Intent
import com.levirs.githubuser.core.model.User
import com.levirs.githubuser.core.ui.userlist.AbsUserListFragment
import com.levirs.githubuser.feature.detail.DetailActivity

abstract class UserListFragment: AbsUserListFragment() {
    override fun onItemClick(user: User) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_USER, user)
        startActivity(intent)
    }
}