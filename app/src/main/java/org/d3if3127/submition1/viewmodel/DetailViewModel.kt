package org.d3if3127.submition1.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3127.submition1.data.local.entity.GithubEntity
import org.d3if3127.submition1.data.local.room.GithubDao
import org.d3if3127.submition1.data.local.room.GithubDatabase

import org.d3if3127.submition1.data.response.DetailUserResponse
import org.d3if3127.submition1.data.retrofit.ApiConfig
import org.d3if3127.submition1.ui.detail.DetailUserActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var githubDao: GithubDao?
    private var githubDb: GithubDatabase?


    companion object{
        private const val TAG = "DetailViewModel"

    }
    init{
        findUser()
        githubDb = GithubDatabase.getDatabase(application)
        githubDao = githubDb?.githubDao()

    }
    private fun findUser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(DetailUserActivity.GITHUB_USERNAME)
        client.enqueue(object: Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ){
                _isLoading.value = false
                if (response.isSuccessful){
                    _detailUser.value = response.body()
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    fun addFav(id:Int, username: String, avatar:String){
        CoroutineScope(Dispatchers.IO).launch {
            var github = GithubEntity(
                id,
                username,
                avatar
            )
            githubDao?.addToFav(github)
        }
    }

    suspend fun cekFav(id: Int) = githubDao?.checked(id)
    fun removeFav(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            githubDao?.removeFav(id)
        }
    }
}