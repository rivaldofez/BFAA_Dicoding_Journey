package com.rivaldofez.cubihub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rivaldofez.cubihub.databinding.FragmentFollowersBinding
import com.rivaldofez.cubihub.databinding.FragmentFollowingBinding

class FollowingFragment : Fragment() {
    var username:String? = null
    private lateinit var binding: FragmentFollowingBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvHello.text = username
    }

}