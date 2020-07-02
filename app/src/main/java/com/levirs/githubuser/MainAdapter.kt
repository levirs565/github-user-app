package com.levirs.githubuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.levirs.githubuser.model.User
import kotlinx.android.synthetic.main.item_user.view.*

class MainAdapter: RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    var list = arrayOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            inflater.inflate(R.layout.item_user, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgAvatar = itemView.img_avatar
        private val tvName = itemView.tv_name
        private val tvUserName = itemView.tv_user_name
        private val tvCompany = itemView.tv_company

        fun bind(user: User) {
            imgAvatar.load(user.avatar)
            tvName.text = user.name
            tvUserName.text = user.userName
            tvCompany.text = user.company
        }
    }
}