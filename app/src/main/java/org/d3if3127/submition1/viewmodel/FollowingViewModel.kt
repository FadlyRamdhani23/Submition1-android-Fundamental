package org.d3if3127.submition1.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.d3if3127.submition1.data.response.ItemsItem
import org.d3if3127.submition1.data.retrofit.ApiConfig
import org.d3if3127.submition1.ui.FollowFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {

    private val _Following =  MutableLiveData<List<ItemsItem>>()
    val following: LiveData<List<ItemsItem>> get() = _Following

    private val _isLoading = MutableLiveData<Boolean>()

    companion object{
        private const val TAG = "FollowersViewModel"
    }

    init {
        findFollowers()
    }
    private fun findFollowers() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowing(FollowFragment.ARG_USERNAME)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ){
                _isLoading.value = false
                if (response.isSuccessful){
                    _Following.value = response.body()
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }
}