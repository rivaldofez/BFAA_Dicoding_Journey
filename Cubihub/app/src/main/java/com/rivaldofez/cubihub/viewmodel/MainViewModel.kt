package com.rivaldofez.cubihub.viewmodel

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.rivaldofez.cubihub.UsersFragment
import com.rivaldofez.cubihub.model.User
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class MainViewModel : ViewModel() {
    val listSearchedUser = MutableLiveData<ArrayList<User>>()

    fun getSearchedUser() : LiveData<ArrayList<User>>{
        return listSearchedUser
    }

    fun setSearchedUser(query: String){
        val searchedItems = ArrayList<User>()

        val client = AsyncHttpClient()
        val url = "https://api.github.com/search/users?q=$query"
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
                    val responseObject = JSONObject(result)
                    val item_users = responseObject.getJSONArray("items")

                    for(i in 0 until item_users.length()){
                        val item = item_users.getJSONObject(i)
                        val temp = User(
                            login = item.getString("login"),
                            type = item.getString("type"),
                            html_url = item.getString("html_url"),
                            avatar_url = item.getString("avatar_url"),
                            id = item.getInt("id"),
                        )
                        searchedItems.add(temp)
                    }
                    listSearchedUser.postValue(searchedItems)
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