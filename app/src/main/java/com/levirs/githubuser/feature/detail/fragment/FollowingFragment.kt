package com.levirs.githubuser.feature.detail.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.levirs.githubuser.R
import com.levirs.githubuser.core.ui.userlist.UserListFragment
import com.levirs.githubuser.feature.detail.DetailViewModel

class FollowingFragment: UserListFragment() {
    private val mViewModel: DetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.followingList.observe(viewLifecycleOwner, Observer {
            if (it.error == null) {
                val isLoading = it.data == null
                showLoading(isLoading)
                if (!isLoading)
                    setUserList(it.data!!)
            } else showError(getString(R.string.user_follower_list), it.error!!)
        })
    }

    override fun reloadList() {
        mViewModel.loadFollowingList()
    }
}