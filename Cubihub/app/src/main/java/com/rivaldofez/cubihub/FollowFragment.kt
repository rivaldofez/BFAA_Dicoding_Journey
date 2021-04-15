package com.rivaldofez.cubihub

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rivaldofez.cubihub.adapter.FollowAdapter
import com.rivaldofez.cubihub.adapter.UsersAdapter
import com.rivaldofez.cubihub.databinding.FragmentFollowBinding
import com.rivaldofez.cubihub.databinding.FragmentUsersBinding
import com.rivaldofez.cubihub.viewmodel.FollowViewModel
import com.rivaldofez.cubihub.viewmodel.SearchUserViewModel

class FollowFragment() : Fragment() {
    companion object {
        private val TAG = "FollowersFragment"
    }
    val layoutManager = LinearLayoutManager(activity)
    var username:String? = null
    var option:String? = null
    private lateinit var followAdapter : FollowAdapter
    private lateinit var binding: FragmentFollowBinding
    private lateinit var followerViewModel: FollowViewModel
    private lateinit var followingViewModel: FollowViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(savedInstanceState!=null){
            username = savedInstanceState.getString("username")
            option = savedInstanceState.getString("option")
        }

        followAdapter = FollowAdapter(requireActivity())
        binding.rvFollowers.layoutManager = layoutManager
        binding.rvFollowers.adapter = followAdapter

        if(option!! == "followers"){
            followerViewModel = ViewModelProvider(activity as AppCompatActivity, ViewModelProvider.NewInstanceFactory()).get(
                option!!,FollowViewModel::class.java)
            followerViewModel.setFollowUser(username!!,option!!)

            followerViewModel.getFollowUser().observe(viewLifecycleOwner, {followItems ->
                if(followerViewModel.errorState){
                    //error
                }else{
                    if(followItems != null && followItems.size!=0){
                        followAdapter.setFollows(followItems)
                    }else{
                        //not found
                    }
                }
            })
        }else{
            followingViewModel = ViewModelProvider(activity as AppCompatActivity, ViewModelProvider.NewInstanceFactory()).get(
                option!!,FollowViewModel::class.java)
            followingViewModel.setFollowUser(username!!,option!!)

            followingViewModel.getFollowUser().observe(viewLifecycleOwner, {followItems ->
                if(followingViewModel.errorState){
                    //error
                }else{
                    if(followItems != null && followItems.size!=0){
                        followAdapter.setFollows(followItems)
                    }else{
                        //not found
                    }
                }
            })
        }
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
        outState.putString("username", username)
        outState.putString("option", option)
    }

}