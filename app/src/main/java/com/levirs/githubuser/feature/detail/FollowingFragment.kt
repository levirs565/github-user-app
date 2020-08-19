package com.levirs.githubuser.feature.detail

import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.levirs.githubuser.R
import com.levirs.githubuser.feature.detail.DetailViewModel
import com.levirs.githubuser.feature.userlist.UserListFragment

class FollowingFragment: UserListFragment() {
    private val mViewModel: DetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.followingList.observe(viewLifecycleOwner, Observer {
            updateListState(it)
        })
        setListContentName(getString(R.string.user_following_list))
        setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL)
    }

    override fun reloadList() {
        mViewModel.loadFollowingList()
    }
}