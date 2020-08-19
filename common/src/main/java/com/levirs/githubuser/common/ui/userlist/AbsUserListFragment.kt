package com.levirs.githubuser.common.ui.userlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.levirs.githubuser.common.R
import com.levirs.githubuser.common.model.DataState
import com.levirs.githubuser.common.model.User
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import kotlinx.android.synthetic.main.fragment_user_list.*

/**
 * A simple [Fragment] subclass.
 */
abstract class AbsUserListFragment : Fragment(), UserListAdapter.OnItemClickListener {
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
        adapter = UserListAdapter(this)
        rv_user.layoutManager = LinearLayoutManager(context)
        rv_user.adapter = adapter
        rv_user.itemAnimator = SlideInLeftAnimator()
        root_view.setReloadAction {
            reloadList()
        }
    }

    fun updateListState(state: DataState<List<User>>) {
        root_view.updateViewState(state)
        adapter.updateUserList(state.data ?: emptyList())
    }

    fun setListContentName(name: String) {
        root_view.setContentName(name)
    }

    fun setGravity(gravity: Int) {
        root_view.setGravity(gravity)
    }

    abstract fun reloadList()
}
