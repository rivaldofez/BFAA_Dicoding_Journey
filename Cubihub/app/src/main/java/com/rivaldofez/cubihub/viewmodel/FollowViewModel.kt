package com.rivaldofez.cubihub.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.rivaldofez.cubihub.model.User
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowViewModel: ViewModel() {
    val listFollowsUser = MutableLiveData<ArrayList<User>>()

    fun getFollowUser() : LiveData<ArrayList<User>> {
        return listFollowsUser
    }

    fun setFollowUser(username: String, option: String){
        val followersItems = ArrayList<User>()

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
                val result = String(responseBody!!)
                try {
                    val item_users = JSONArray(result)
                    followersItems.clear()
                    for(i in 0 until item_users.length()){
                        val item = item_users.getJSONObject(i)
                        val temp = User(
                            login = item.getString("login"),
                            type = item.getString("type"),
                            html_url = item.getString("html_url"),
                            avatar_url = item.getString("avatar_url"),
                            id = item.getInt("id"),
                        )
                        followersItems.add(temp)
                    }
                    listFollowsUser.postValue(followersItems)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("Test", error!!.message.toString())
            }
        })
    }
}