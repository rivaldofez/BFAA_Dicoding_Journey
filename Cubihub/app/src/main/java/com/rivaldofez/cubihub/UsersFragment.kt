package com.rivaldofez.cubihub

import android.content.Intent
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
import com.rivaldofez.cubihub.databinding.FragmentUsersBinding
import com.rivaldofez.cubihub.listener.OnItemClickListener
import com.rivaldofez.cubihub.model.User
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class UsersFragment : Fragment() {
    companion object {
        private val TAG = "UsersFragment"
    }

    val layoutManager = LinearLayoutManager(activity)
    val addUserList: MutableList<User> = ArrayList()
    lateinit var userAdapter: UsersAdapter
    private lateinit var binding: FragmentUsersBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUsersBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        action()
        fetchFromAPI()
    }

    private fun action() {
        userAdapter.setOnClickItemListener(object : OnItemClickListener{
            override fun onItemClick(item: View, user: User) {
                val goToDetailActivity = Intent(context, UserDetailActivity::class.java)
                goToDetailActivity.putExtra("Key", user)
                startActivity(goToDetailActivity)
            }
            override fun onShowToast(item: View, position: Int) {
                Toast.makeText(context, "You Click This button" ,Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initView() {
        binding.rvUsers.layoutManager = layoutManager
        userAdapter = UsersAdapter(activity!!)
        binding.rvUsers.adapter = userAdapter
        readAllData()
        userAdapter.setUsers(addUserList)
    }

    private fun readAllData() {
        val fileJSON:String = activity!!.applicationContext.assets.open("user_data.json").bufferedReader().use {
            it.readText()
        }

        val jsonObject = JSONObject(fileJSON.substring(fileJSON.indexOf("{"), fileJSON.lastIndexOf("}") + 1))
        val jsonArr = jsonObject.getJSONArray("users")

            for(i in 0..jsonArr.length()-1){
                var jsonObj = jsonArr.getJSONObject(i)
                val user = User(
                    fullname = jsonObj.getString("fullname"),
                    username = jsonObj.getString("username"),
                    email = jsonObj.getString("email"),
                    address = jsonObj.getString("address"),
                    location = jsonObj.getString("location"),
                    phone = jsonObj.getString("phone"),
                    biography = jsonObj.getString("biography"),
                    avatar = jsonObj.getString("avatar"),
                    num_follower = jsonObj.getString("num_follower").toInt(),
                    num_following = jsonObj.getString("num_following").toInt(),
                    num_likes = jsonObj.getString("num_likes").toInt(),
                    num_repository = jsonObj.getString("num_repository").toInt()
                )
                addUserList.add(user)
            }
    }

    private fun fetchFromAPI(){
        val client = AsyncHttpClient()
        val url = "https://api.github.com/search/users?q=avatar"
        client.addHeader("Authorization", "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")
        client.addHeader("User-Agent", "request")

        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
               val result = String(responseBody!!)
                Log.d(TAG,result)
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
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
}