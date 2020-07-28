package com.levirs.githubuser.core.ui.userlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.levirs.githubuser.R
import com.levirs.githubuser.core.model.User
import kotlinx.android.synthetic.main.fragment_user_list.*

/**
 * A simple [Fragment] subclass.
 */
abstract class UserListFragment : Fragment() {
    private lateinit var adapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = UserListAdapter()
        rv_user.layoutManager = LinearLayoutManager(context)
        rv_user.adapter = adapter
        root_view.setReloadAction {
            reloadList()
        }
    }

    fun setUserList(list: List<User>) {
        adapter.setUserList(list)
    }

    fun showView() {
        root_view.showView()
    }

    fun showLoading() {
        root_view.showLoading()
    }

    fun showError(listName: String, errMessage: String) {
        root_view.showError(listName, errMessage)
    }

    fun setGravity(gravity: Int) {
        root_view.setGravity(gravity)
    }

    abstract fun reloadList()
}
