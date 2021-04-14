package com.rivaldofez.cubihub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rivaldofez.cubihub.R
import com.rivaldofez.cubihub.databinding.ItemUserBinding
import com.rivaldofez.cubihub.listener.OnItemClickListener
import com.rivaldofez.cubihub.model.User
import de.hdodenhof.circleimageview.CircleImageView

class UsersAdapter(val context: Context): RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {
    private val users : MutableList<User> = mutableListOf()
    private var onSelectedListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bindModel(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun setUsers(data: List<User>){
        users.clear()
        users.addAll(data)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindModel(user: User){
            with(binding){
                binding.tvFullname.text = user.fullname
                binding.tvUsername.text = user.username
                binding.tvLocation.text = user.location
                binding.tvRepository.text = user.num_repository.toString()
                binding.tvFollower.text = user.num_follower.toString()
                binding.tvLikes.text = user.num_likes.toString()

                val imageResource = context.resources.getIdentifier(user.avatar,null, context.packageName)
                Glide.with(context).load(imageResource).into(binding.imgAvatar)

            }
        }

        init {
            with(binding){
                imgAvatar.setOnClickListener{
                    onSelectedListener?.onItemClick(it, users[layoutPosition])
                }

                tvFullname.setOnClickListener {
                    onSelectedListener?.onItemClick(it, users[layoutPosition])
                }

                tvLikes.setOnClickListener {
                    onSelectedListener?.onShowToast(it, layoutPosition)
                }

                tvFollower.setOnClickListener {
                    onSelectedListener?.onShowToast(it, layoutPosition)
                }
            }
        }
    }

    fun setOnClickItemListener(onClickItemListener:OnItemClickListener){
        this.onSelectedListener = onClickItemListener
    }
}

