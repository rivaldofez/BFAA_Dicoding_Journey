package com.rivaldofez.cubihub

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.rivaldofez.cubihub.adapter.FollowAdapter
import com.rivaldofez.cubihub.adapter.UsersAdapter
import com.rivaldofez.cubihub.databinding.FragmentFollowBinding
import com.rivaldofez.cubihub.model.User
import com.rivaldofez.cubihub.viewmodel.FollowViewModel
import com.rivaldofez.cubihub.viewmodel.SearchUserViewModel
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowFragment() : Fragment() {
    companion object {
        private val TAG = "FollowersFragment"
        private val extra_username = "username"
        private val extra_option = "option"
    }
    var username:String? = null
    var option:String? = null
    val layoutManager = LinearLayoutManager(activity)
    private lateinit var binding: FragmentFollowBinding
    private lateinit var followAdapter : FollowAdapter
    private lateinit var followViewModel: FollowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followViewModel = ViewModelProvider(activity as AppCompatActivity,ViewModelProvider.NewInstanceFactory()).get(FollowViewModel::class.java)

        if(savedInstanceState != null){
            val savedUsername = savedInstanceState.getString(extra_username)
            val savedOption = savedInstanceState.getString(extra_option)
            followViewModel.setFollowUser(savedUsername!!, savedOption!!)
        }else{
            followViewModel.setFollowUser(username!!, option!!)
        }

        followAdapter = FollowAdapter(activity!!)
        binding.rvFollowers.layoutManager = layoutManager
        binding.rvFollowers.adapter = followAdapter

        followViewModel.getFollowUser().observe(activity as AppCompatActivity, {followItems ->
            if(followItems!=null){
                followAdapter.setFollows(followItems)
                showLoading(false)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        followViewModel.getFollowUser().observe(activity as AppCompatActivity, {followItems ->
            if(followItems!=null){
                followAdapter.setFollows(followItems)
                showLoading(false)
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(extra_username, username)
        outState.putString(extra_option, option)
    }

}