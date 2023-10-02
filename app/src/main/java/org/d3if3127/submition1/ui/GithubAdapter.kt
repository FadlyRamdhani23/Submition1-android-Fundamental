package org.d3if3127.submition1.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import org.d3if3127.submition1.database.entity.GithubEntity
import org.d3if3127.submition1.databinding.ItemUserBinding
import org.d3if3127.submition1.ui.detail.DetailUserActivity


class GithubAdapter (private val onBookmarkClick: (GithubEntity) -> Unit) : ListAdapter<GithubEntity, GithubAdapter.MyViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
        val ivBookmark = holder.binding.ivBookmark
        if (user.isBookmarked) {
            ivBookmark.setImageResource(android.R.drawable.btn_star_big_on)
        } else {
            ivBookmark.setImageResource(android.R.drawable.btn_star_big_off)
        }
        // Panggil aksi yang sesuai (misalnya, simpan atau hapus bookmark)

        ivBookmark.setOnClickListener {
            onBookmarkClick(user)
        }
    }

    class MyViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: GithubEntity) {
            binding.name.text = user.username
            Glide.with(binding.root.context)
                .load("${user.avatarUrl}")
                .transform(CircleCrop())
                .into(binding.avatar)
            itemView.setOnClickListener {
                val context = itemView.context
                val moveWithObjectIntent = Intent(context, DetailUserActivity::class.java)
                moveWithObjectIntent.putExtra(DetailUserActivity.GITHUB_USERNAME, user.username)
                moveWithObjectIntent.putExtra(FollowFragment.ARG_USERNAME, user.username)
                context.startActivity(moveWithObjectIntent)
            }
        }

    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GithubEntity>() {
            override fun areItemsTheSame(oldItem: GithubEntity, newItem: GithubEntity): Boolean {
                return oldItem.username == newItem.username // Assuming 'username' is the unique identifier for items.
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: GithubEntity, newItem: GithubEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}
