package com.rivaldofez.cubihub

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.rivaldofez.cubihub.adapter.UsersAdapter
import com.rivaldofez.cubihub.databinding.FragmentFollowBinding
import com.rivaldofez.cubihub.model.User
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowFragment() : Fragment() {
    companion object {
        private val TAG = "FollowersFragment"
    }

    var username:String? = null
    var option:String? = null
    val layoutManager = LinearLayoutManager(activity)
    val userDataSearch: MutableList<User> = ArrayList()
    private lateinit var binding: FragmentFollowBinding
    private lateinit var userAdapter : UsersAdapter

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
        fetchFromAPI()
        userAdapter = UsersAdapter(activity!!)
        binding.rvFollowers.layoutManager = layoutManager
        binding.rvFollowers.adapter = userAdapter
    }

    private fun fetchFromAPI(){
        val client = AsyncHttpClient()
        val url = "https://api.github.com/users/$username/$option"
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
                    val item_users = JSONArray(result)
                    userDataSearch.clear()
                    for(i in 0 until item_users.length()){
                        val item = item_users.getJSONObject(i)
                        val temp = User(
                                login = item.getString("login"),
                                type = item.getString("type"),
                                html_url = item.getString("html_url"),
                                avatar_url = item.getString("avatar_url"),
                                id = item.getInt("id"),
                        )
                        userDataSearch.add(temp)
                    }
                    userAdapter.setUsers(userDataSearch)
                    Log.d(TAG,userDataSearch[0].login)
                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
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
                Log.d(TAG,errorMessage)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        fetchFromAPI()
    }

}