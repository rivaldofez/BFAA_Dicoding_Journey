package com.rivaldofez.cubihub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.rivaldofez.cubihub.adapter.DetailPagerAdapter
import com.rivaldofez.cubihub.databinding.ActivityUserDetailBinding
import com.rivaldofez.cubihub.model.DetailUser
import com.rivaldofez.cubihub.model.User
import com.rivaldofez.cubihub.viewmodel.DetailUserViewModel
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class UserDetailActivity : AppCompatActivity() {
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }

    private lateinit var username: String
    private lateinit var binding:ActivityUserDetailBinding
    private lateinit var detailUserViewModel: DetailUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()

        detailUserViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailUserViewModel::class.java)

        detailUserViewModel.setDetailUser(username, this@UserDetailActivity)
        detailUserViewModel.getDetailUser().observe(this,{ detailUser ->
            if (detailUser != null){
                setUserView(detailUser)
                showLoading(false)
            }
        })

        val detailPagerAdapter = DetailPagerAdapter(this)
        detailPagerAdapter.username = username

        binding.viewPager.adapter = detailPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager, ) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])

        }.attach()
    }

    private fun initView() {
        username = intent.getStringExtra("Key")!!
    }

    private fun setUserView(detailUser : DetailUser){
        binding.tvFullname.text = detailUser.name
        binding.tvLocation.text = detailUser.location
        binding.tvRepository.text = detailUser.public_repos.toString()
        binding.tvFollower.text = detailUser.followers.toString()
        binding.tvFollowing.text = detailUser.following.toString()
        binding.tvUsername.text = detailUser.login

        Glide.with(this).load(detailUser.avatar_url).into(binding.imgContent)
    }

    private fun fetchFromAPI(){
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$username"
        client.addHeader("Authorization", "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")
        client.addHeader("User-Agent", "request")

        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray?
            ) {
                binding.progressBar.visibility = View.INVISIBLE

                val result = String(responseBody!!)
                try {
                    val item = JSONObject(result)
                    binding.tvFullname.text = item.getString("name")
                    binding.tvUsername.text = item.getString("login")
                    binding.tvFollower.text = item.getInt("followers").toString()
                    binding.tvLocation.text = item.getString("location")
                    binding.tvFollowing.text = item.getInt("following").toString()
                    binding.tvRepository.text = item.getInt("public_repos").toString()
                    Glide.with(applicationContext).load(item.getString("avatar_url")).into(binding.imgContent)
                    Log.d("UserDetailTest", result)
                } catch (e: Exception) {
                    Toast.makeText(this@UserDetailActivity, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }
            override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    responseBody: ByteArray?,
                    error: Throwable?
            ) {
                binding.progressBar.visibility = View.INVISIBLE
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error!!.message}"
                }
                Log.d("UserDetailTest",errorMessage)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}