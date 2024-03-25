package com.belajar.githubusernavigationfinal.data.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.belajar.githubusernavigationfinal.R
import com.belajar.githubusernavigationfinal.data.entity.UserEntity
import com.belajar.githubusernavigationfinal.data.response.ItemsItem
import com.belajar.githubusernavigationfinal.databinding.ItemUserListBinding
import com.belajar.githubusernavigationfinal.ui.DetailActivity
import com.bumptech.glide.Glide

class UserAdapter(private val onClickFavorite: (UserEntity) -> Unit) : ListAdapter<UserEntity, UserAdapter.UserViewHolder>(DIFF_CALLBACK) {

    class UserViewHolder(val binding: ItemUserListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(user: UserEntity) {
            binding.tvName.text = user.login
            Glide.with(binding.root.context)
                .load(user.avatarUrl)
                .into(binding.cvAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            ItemUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

//    override fun getItemCount(): Int {
//        return getList.size
//    }

//    fun setUserList(newList: List<ItemsItem>) {
//        getList.clear()
//        getList.addAll(newList)
//    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val viewHolderItem = getItem(position)
        holder.bindItem(viewHolderItem)
        holder.itemView.setOnClickListener {
            val moveIntent = Intent(holder.itemView.context, DetailActivity::class.java)
            moveIntent.putExtra("id", viewHolderItem.login)
            holder.itemView.context.startActivity(moveIntent)
        }
        if (viewHolderItem.favorite) {
            holder.binding.ivFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    holder.binding.ivFavorite.context,
                    R.drawable.baseline_favorite_24
                )
            )

        } else {
            holder.binding.ivFavorite.setImageDrawable(
                ContextCompat.getDrawable(
                    holder.binding.ivFavorite.context,
                    R.drawable.baseline_favorite_border_24
                )
            )
        }
        holder.binding.ivFavorite.setOnClickListener {
            onClickFavorite(viewHolderItem)
        }
    }
    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<UserEntity> =
            object : DiffUtil.ItemCallback<UserEntity>() {
                override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                    return oldItem.login == newItem.login
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
                    return oldItem == newItem
                }
            }
    }

}