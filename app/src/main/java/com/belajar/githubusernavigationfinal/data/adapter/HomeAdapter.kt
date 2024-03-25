package com.belajar.githubusernavigationfinal.data.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.belajar.githubusernavigationfinal.R
import com.belajar.githubusernavigationfinal.data.entity.UserEntity
import com.belajar.githubusernavigationfinal.databinding.LayoutHomeBinding
import com.belajar.githubusernavigationfinal.ui.DetailActivity
import com.bumptech.glide.Glide

class HomeAdapter(private val onClickFavorite: (UserEntity) -> Unit): ListAdapter<UserEntity, HomeAdapter.ViewHolder>(DIFF_CALLBACK) {


//    private val getList = ArrayList<ItemsItem>()


    class ViewHolder(val binding: LayoutHomeBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bindItem(item: ItemsItem) {
//            binding.tvName.text = item.login
//            Glide.with(binding.root.context)
//                .load(item.avatarUrl)
//                .into(binding.cvAvatar)
//            binding.cardOpen.setOnClickListener {
//                val moveIntent = Intent(Intent.ACTION_VIEW, Uri.parse(item.htmlUrl))
//                itemView.context.startActivity(moveIntent)
//            }
//        }
        fun bindItem(user: UserEntity) {
            binding.tvName.text = user.login
            Glide.with(binding.root.context)
                .load(user.avatarUrl)
                .into(binding.cvAvatar)
            binding.cardOpen.setOnClickListener {
                val moveIntent = Intent(Intent.ACTION_VIEW, Uri.parse(user.htmlUrl))
                itemView.context.startActivity(moveIntent)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val viewHolder = getItem(position)

        holder.bindItem(viewHolder)
        holder.itemView.setOnClickListener{
            val moveIntent = Intent(holder.itemView.context, DetailActivity::class.java)
            moveIntent.putExtra("id", viewHolder.login)
            holder.itemView.context.startActivity(moveIntent)
        }
            if (viewHolder.favorite) {
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
            onClickFavorite(viewHolder)
        }

    }
//    fun setUserList(newList: List<ItemsItem>) {
//        getList.clear()
//        getList.addAll(newList)
//    }

//    override fun getItemCount(): Int = getList.size

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