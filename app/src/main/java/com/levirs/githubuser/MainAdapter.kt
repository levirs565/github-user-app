package com.levirs.githubuser

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
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
        holder.bind(list[position], position == list.size -1)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgAvatar = itemView.img_avatar
        private val tvName = itemView.tv_name
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
            tvName.text = user.name
            tvUserName.text = user.userName

            itemView.setOnClickListener {
                val detailIntent = Intent(it.context, DetailActivity::class.java)
                detailIntent.putExtra(DetailActivity.EXTRA_USER, user)
                it.context.startActivity(detailIntent)
            }
        }
    }
}