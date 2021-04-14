package com.rivaldofez.cubihub

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.rivaldofez.cubihub.adapter.UsersAdapter
import com.rivaldofez.cubihub.listener.OnItemClickListener
import com.rivaldofez.cubihub.model.User
import kotlinx.android.synthetic.main.fragment_users.*
import org.json.JSONObject

class UsersFragment : Fragment() {
    val layoutManager = LinearLayoutManager(activity)
    val addUserList: MutableList<User> = ArrayList()
    lateinit var userAdapter: UsersAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        action()
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
        rv_users.layoutManager = layoutManager
        userAdapter = UsersAdapter(activity!!)
        rv_users.adapter = userAdapter
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
}