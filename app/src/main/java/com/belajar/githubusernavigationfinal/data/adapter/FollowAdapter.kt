package com.belajar.githubusernavigationfinal.data.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.belajar.githubusernavigationfinal.data.entity.UserEntity
import com.belajar.githubusernavigationfinal.data.response.ItemsItem
import com.belajar.githubusernavigationfinal.data.response.UserResponse
import com.belajar.githubusernavigationfinal.databinding.ItemUserListBinding
import com.belajar.githubusernavigationfinal.databinding.LayoutHomeBinding
import com.belajar.githubusernavigationfinal.ui.DetailActivity
import com.bumptech.glide.Glide

class FollowAdapter: ListAdapter<ItemsItem, FollowAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ItemsItem> =
            object : DiffUtil.ItemCallback<ItemsItem>() {
                override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                    return oldItem.login == newItem.login
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                    return oldItem == newItem
                }

            }
    }
    class ViewHolder(val binding: ItemUserListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(user: ItemsItem) {
            binding.tvName.text = user.login
            Glide.with(binding.root.context)
                .load(user.avatarUrl)
                .into(binding.cvAvatar)
            binding.cardView.setOnClickListener {
                val moveIntent = Intent(Intent.ACTION_VIEW, Uri.parse(user.htmlUrl))
                itemView.context.startActivity(moveIntent)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            ItemUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val viewHolderItem = getItem(position)
        holder.bindItem(viewHolderItem)
        holder.itemView.setOnClickListener {
            val moveIntent = Intent(holder.itemView.context, DetailActivity::class.java)
            moveIntent.putExtra("id", viewHolderItem.login)
            holder.itemView.context.startActivity(moveIntent)
        }
        holder.binding.ivFavorite.visibility = View.GONE
    }
}