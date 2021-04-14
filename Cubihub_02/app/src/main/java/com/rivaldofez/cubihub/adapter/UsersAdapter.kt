package com.rivaldofez.cubihub.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rivaldofez.cubihub.R
import com.rivaldofez.cubihub.listener.OnItemClickListener
import com.rivaldofez.cubihub.model.User
import de.hdodenhof.circleimageview.CircleImageView

class UsersAdapter(val context: Context): RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {
    private val users : MutableList<User> = mutableListOf()
    private var onSelectedListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(LayoutInflater.from(context).inflate(R.layout.item_user, parent, false))
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

    inner class UserViewHolder(item: View): RecyclerView.ViewHolder(item) {
        val tvFullname : TextView = item.findViewById(R.id.tv_fullname)
        val tvUsername : TextView = item.findViewById(R.id.tv_username)
        val tvLocation : TextView = item.findViewById(R.id.tv_location)
        val tvRepository : TextView = item.findViewById(R.id.tv_repository)
        val tvFollower : TextView = item.findViewById(R.id.tv_follower)
        val tvLikes : TextView = item.findViewById(R.id.tv_likes)
        val imgAvatar : CircleImageView = item.findViewById(R.id.img_avatar)

        fun bindModel(user: User){
            tvFullname.text = user.fullname
            tvUsername.text = user.username
            tvLocation.text = user.location
            tvRepository.text = user.num_repository.toString()
            tvFollower.text = user.num_follower.toString()
            tvLikes.text = user.num_likes.toString()

            val imageResource = context.resources.getIdentifier(user.avatar,null, context.packageName)
            Glide.with(context).load(imageResource).into(imgAvatar)
        }

        init {
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

    fun setOnClickItemListener(onClickItemListener:OnItemClickListener){
        this.onSelectedListener = onClickItemListener
    }
}