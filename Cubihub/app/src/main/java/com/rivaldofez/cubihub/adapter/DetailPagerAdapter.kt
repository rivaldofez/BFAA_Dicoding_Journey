package com.rivaldofez.cubihub.adapter

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder
import com.rivaldofez.cubihub.FollowFragment

class DetailPagerAdapter (activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var username:String? = null

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null

        when (position) {
            0 -> {
                val fragmentFollower = FollowFragment()
                fragmentFollower.username = username
                fragmentFollower.option = "followers"
                fragment = fragmentFollower
                Log.d("teston","aman")
            }
            1 -> {
                val fragmentFollowing = FollowFragment()
                fragmentFollowing.username = username
                fragmentFollowing.option = "following"
                fragment = fragmentFollowing
                Log.d("teston", "aman2")
            }
        }
        return fragment!!
    }
}