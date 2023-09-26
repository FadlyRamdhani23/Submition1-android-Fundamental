package org.d3if3127.submition1.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.d3if3127.submition1.data.response.DetailUserResponse
import org.d3if3127.submition1.data.retrofit.ApiConfig
import org.d3if3127.submition1.ui.detail.DetailUserActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {
    private val _detailUser = MutableLiveData<DetailUserResponse>()
    val detailUser: LiveData<DetailUserResponse> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "DetailViewModel"

    }

    init{
        findUser()
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
}