package org.d3if3127.submition1.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.d3if3127.submition1.data.response.GithubResponse
import org.d3if3127.submition1.data.response.ItemsItem
import org.d3if3127.submition1.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel(){

    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String>
        get() = _searchQuery

    private val _githubQuery =  MutableLiveData<List<ItemsItem>>()
    val githubQuery: LiveData<List<ItemsItem>>
        get() = _githubQuery

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading



    companion object{
        private const val TAG = "MainViewModel"
        public var GITHUB_Query = "fadly"
    }

    init {
        findGithubUser()
    }
    private fun findGithubUser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getGithubUser(GITHUB_Query)
        client.enqueue(object: Callback<GithubResponse>{
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ){
                _isLoading.value = false
                if (response.isSuccessful){
                    _githubQuery.value = response.body()?.items
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
        GITHUB_Query = query
    }
    fun performSearch() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getGithubUser(GITHUB_Query)
        client.enqueue(object: Callback<GithubResponse>{
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ){
                _isLoading.value = false
                if (response.isSuccessful){
                    _githubQuery.value = response.body()?.items
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}