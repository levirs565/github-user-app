package com.levirs.githubuser.core.ui.userlist

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.levirs.githubuser.feature.detail.DetailActivity
import com.levirs.githubuser.R
import com.levirs.githubuser.core.model.User
import kotlinx.android.synthetic.main.item_user.view.*

class UserListAdapter: RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    private var mUserList = arrayListOf<User>()

    fun addUserList(list: List<User>) {
        mUserList.addAll(list)
        notifyItemRangeInserted(0, list.size)
    }

    fun clearUserList() {
        val oldSize = mUserList.size
        mUserList.clear()
        notifyItemRangeRemoved(0, oldSize)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            inflater.inflate(
                R.layout.item_user,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = mUserList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mUserList[position], position == mUserList.size -1)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgAvatar = itemView.img_avatar
        private val tvUserName = itemView.tv_user_name

        fun bind(user: User, isLast: Boolean) {
            itemView.updateLayoutParams<RecyclerView.LayoutParams> {
                val bottomMargin = if (isLast)
                    itemView.context.resources.getDimension(R.dimen.user_card_margin).toInt()
                else 0
                updateMargins(
                    bottom = bottomMargin
                )
            }

            imgAvatar.load(user.avatar)
            tvUserName.text = user.userName

            itemView.setOnClickListener {
                val detailIntent = Intent(it.context, DetailActivity::class.java)
                detailIntent.putExtra(DetailActivity.EXTRA_USER, user)
                it.context.startActivity(detailIntent)
            }
        }
    }
}