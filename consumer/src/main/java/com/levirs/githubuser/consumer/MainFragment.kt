package com.levirs.githubuser.consumer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.levirs.githubuser.common.model.DataState
import com.levirs.githubuser.common.model.User
import com.levirs.githubuser.common.ui.userlist.AbsUserListFragment

class MainFragment: AbsUserListFragment() {
    private val mViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateListState(DataState(null, null))
        mViewModel.favoriteList.observe(viewLifecycleOwner, Observer {
            updateListState(DataState(it, null))
        })
    }

    override fun reloadList() {
        mViewModel.update()
    }

    override fun onItemClick(user: User) {
        // do nothing
    }
}