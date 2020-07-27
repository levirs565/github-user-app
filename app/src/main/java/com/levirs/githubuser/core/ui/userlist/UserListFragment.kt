package com.levirs.githubuser.core.ui.userlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.levirs.githubuser.R
import com.levirs.githubuser.core.model.User
import kotlinx.android.synthetic.main.context_error.*
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
        btn_reload.setOnClickListener { reloadList() }
    }

    fun setUserList(list: List<User>) {
        adapter.setUserList(list)
    }

    fun showLoading(show: Boolean) {
        rv_user.visibility = if (show) View.GONE else View.VISIBLE
        progressBar.visibility = if (show) View.VISIBLE else View.GONE
        layout_err.visibility = View.GONE
    }

    fun showError(listName: String, errMessage: String) {
        rv_user.visibility = View.GONE
        progressBar.visibility = View.GONE
        tv_err_message.text = getString(R.string.message_cannot_load, listName, errMessage)
        layout_err.visibility = View.VISIBLE
    }

    abstract fun reloadList()
}
