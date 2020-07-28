package com.levirs.githubuser.feature.main

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.levirs.githubuser.R
import com.levirs.githubuser.core.ui.userlist.UserListFragment

class MainFragment: UserListFragment() {
    private var mListener: Listener? = null
    private val mViewModel: MainViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.userList.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                setUserList(it)
                showView()
            } else {
                showLoading()
            }
        })

        mViewModel.isError.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
                showError(getString(R.string.user_list), it)
            }
        })
    }

    override fun reloadList() {
        mListener?.onReloadList()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mListener = context as Listener
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    interface Listener {
        fun onReloadList()
    }
}