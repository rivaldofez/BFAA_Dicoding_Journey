package com.rivaldofez.cubihub

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.rivaldofez.cubihub.adapter.DetailPagerAdapter
import com.rivaldofez.cubihub.databinding.ActivityUserDetailBinding
import com.rivaldofez.cubihub.model.User
import java.lang.Exception

class UserDetailActivity : AppCompatActivity() {
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    lateinit var user:User;
    private lateinit var binding:ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        val sectionsPagerAdapter = DetailPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f

    }

    private fun initView() {
        user = intent.getParcelableExtra<User>("Key") as User

        binding.tvFullname.text = user.fullname
        binding.tvUsername.text = user.username
        binding.tvFollower.text = user.num_follower.toString()
        binding.tvFollowing.text = user.num_following.toString()
        binding.tvRepository.text = user.num_repository.toString()

        val imageResource = resources.getIdentifier(user.avatar,null, packageName)
        Glide.with(applicationContext).load(imageResource).into(binding.imgContent)

    }
}