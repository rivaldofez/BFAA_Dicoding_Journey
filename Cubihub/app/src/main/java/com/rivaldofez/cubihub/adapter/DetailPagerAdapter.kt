package com.rivaldofez.cubihub.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rivaldofez.cubihub.FollowersFragment
import com.rivaldofez.cubihub.FollowingFragment

class DetailPagerAdapter (activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var username:String? = null

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null

        when (position) {
            0 -> {
                val fragmentFollower = FollowersFragment()
                fragmentFollower.username = username
                fragment = fragmentFollower
            }
            1 -> {
                val fragmentFollowing = FollowingFragment()
                fragmentFollowing.username = username
                fragment = fragmentFollowing
            }
        }
        return fragment as Fragment
    }
}