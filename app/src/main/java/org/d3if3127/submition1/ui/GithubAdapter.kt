package org.d3if3127.submition1.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import org.d3if3127.submition1.data.response.ItemsItem
import org.d3if3127.submition1.databinding.ItemUserBinding
import org.d3if3127.submition1.ui.detail.DetailUserActivity


class GithubAdapter : ListAdapter<ItemsItem, GithubAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val moveWithObjectIntent = Intent(context, DetailUserActivity::class.java)
            moveWithObjectIntent.putExtra(DetailUserActivity.GITHUB_USERNAME, user.login)
            moveWithObjectIntent.putExtra(FollowFragment.ARG_USERNAME, user.login)
            context.startActivity(moveWithObjectIntent)
        }
    }
    class MyViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem) {
            binding.name.text = "${user.login}"
            Glide.with(binding.root.context)
                .load("${user.avatarUrl}")
                .transform(CircleCrop())
                .into(binding.avatar)
        }

    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
