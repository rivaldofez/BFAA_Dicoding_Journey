package com.rivaldofez.cubihub

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
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
    val userDataSearch: MutableList<User> = ArrayList()
    private lateinit var userAdapter : UsersAdapter
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
        fetchFromAPI("rivaldofez")
        userAdapter = UsersAdapter(requireActivity())
        binding.rvUsers.layoutManager = layoutManager
        binding.rvUsers.adapter = userAdapter
        action()

        val searchManager = requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding.searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
        binding.searchView.queryHint = "Masukan Kata Pencarian"
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.progressBar.visibility = View.VISIBLE
                fetchFromAPI(query!!)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

    }

    private fun action() {
        userAdapter.setOnClickItemListener(object : OnItemClickListener{
            override fun onItemClick(item: View, userSearch: User) {
                val goToDetailActivity = Intent(context, UserDetailActivity::class.java)
                goToDetailActivity.putExtra("Key", userSearch.login)
                startActivity(goToDetailActivity)
            }
            override fun onShowToast(item: View, position: Int) {
                Toast.makeText(context, "You Click This button" ,Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchFromAPI(query: String){
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
                binding.progressBar.visibility = View.INVISIBLE

                val result = String(responseBody!!)
                try {
                    val responseObject = JSONObject(result)
                    val item_users = responseObject.getJSONArray("items")
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
}