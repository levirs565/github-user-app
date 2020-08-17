package com.levirs.githubuser.feature.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.levirs.githubuser.R
import com.levirs.githubuser.core.model.DataState
import com.levirs.githubuser.core.ui.userlist.UserListFragment

class FavoriteFragment: UserListFragment() {
    private val mViewModel: FavoriteViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListContentName(getString(R.string.favorite_list))
        updateListState(DataState(null, null))

        mViewModel.userFavoriteList.observe(viewLifecycleOwner, Observer {
            updateListState(DataState(it, null))
        })
    }

    override fun reloadList() {
        // tidak ada yang dilakukan
    }
}