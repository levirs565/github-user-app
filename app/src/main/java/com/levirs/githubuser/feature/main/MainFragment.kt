package com.levirs.githubuser.feature.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.levirs.githubuser.core.ui.userlist.UserListFragment

class MainFragment: UserListFragment() {
    private val mViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.userList.observe(viewLifecycleOwner, Observer {
            if (it != null)
                setUserList(it)
        })
    }
}