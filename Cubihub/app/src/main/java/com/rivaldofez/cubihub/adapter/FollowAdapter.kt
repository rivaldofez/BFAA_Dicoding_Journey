package com.rivaldofez.cubihub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rivaldofez.cubihub.databinding.ItemUserBinding
import com.rivaldofez.cubihub.model.User

class FollowAdapter (val context: Context): RecyclerView.Adapter<FollowAdapter.FollowViewHolder>() {
    private val follows : MutableList<User> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FollowViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FollowViewHolder(binding)
    }

    fun setFollows(data: MutableList<User>){
        follows.clear()
        follows.addAll(data)
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: FollowViewHolder, position: Int) {
        holder.bindModel(follows[position])
    }

    override fun getItemCount(): Int {
        return follows.size
    }

    inner class FollowViewHolder(private val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindModel(user: User){
            with(binding){
                binding.tvFullname.text = user.login
                binding.tvUsername.text = user.type
                binding.tvLocation.text = user.html_url
                Glide.with(context).load(user.avatar_url).into(binding.imgAvatar)
            }
        }
    }
}