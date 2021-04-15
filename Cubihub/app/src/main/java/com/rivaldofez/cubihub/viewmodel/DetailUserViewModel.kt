package com.rivaldofez.cubihub.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.rivaldofez.cubihub.model.DetailUser
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailUserViewModel : ViewModel() {
    val detailUser = MutableLiveData<DetailUser>()
    var errorState = false

    fun getDetailUser() : LiveData<DetailUser> {
        return detailUser
    }

    fun setDetailUser(username: String){
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
                val result = String(responseBody!!)
                try {
                    val item = JSONObject(result)
                    val temp = DetailUser(
                        name = item.getString("name"),
                        login = item.getString("login"),
                        location = item.getString("location"),
                        followers = item.getInt("followers"),
                        following = item.getInt("following"),
                        public_repos = item.getInt("public_repos"),
                        avatar_url = item.getString("avatar_url"),
                        type = item.getString("type"),
                        id = item.getInt("id")
                    )
                    detailUser.postValue(temp)
                    errorState = false
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
                errorState = true
            }
        })
    }
}