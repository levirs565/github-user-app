package com.levirs.githubuser.common.ui.userlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.levirs.githubuser.common.R
import com.levirs.githubuser.common.model.User
import kotlinx.android.synthetic.main.item_user.view.*

class UserListAdapter(
    private val mClickListener: OnItemClickListener
): RecyclerView.Adapter<UserListAdapter.ViewHolder>() {
    private var mUserList: List<User> = emptyList()

    fun updateUserList(list: List<User>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallback(mUserList, list))
        mUserList = list
        diffResult.dispatchUpdatesTo(this)
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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
                mClickListener.onItemClick(user)
            }
        }
    }

    inner class DiffCallback(
        val old: List<User>,
        val new: List<User>
    ): DiffUtil.Callback() {
        override fun getOldListSize(): Int = old.size

        override fun getNewListSize(): Int = new.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return old[oldItemPosition].id == new[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return old[oldItemPosition] == new[newItemPosition]
        }

    }

    interface OnItemClickListener {
        fun onItemClick(user: User)
    }
}