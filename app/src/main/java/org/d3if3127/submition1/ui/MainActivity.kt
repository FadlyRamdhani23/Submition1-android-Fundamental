package org.d3if3127.submition1.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import org.d3if3127.submition1.R
import org.d3if3127.submition1.data.response.GithubResponse
import org.d3if3127.submition1.data.response.ItemsItem
import org.d3if3127.submition1.data.retrofit.ApiConfig
import org.d3if3127.submition1.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val TAG = "MainActivity"
        private const val GITHUB_Query = "Fadly"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.rvGithub.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvGithub.addItemDecoration(itemDecoration)

        findGithubUser()

    }

    private fun findGithubUser() {
        showLoading(true)
        val client = ApiConfig.getApiService().getGithubUser(GITHUB_Query)
        client.enqueue(object: Callback<GithubResponse>{
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ){
                showLoading(false)
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                   setGithubData(responseBody.items)
                    }
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })

    }
//    private fun setGithubData(itemsItem: ItemsItem) {
//        binding.tvTitle.text = itemsItem.login
//        binding.tvDescription.text = itemsItem.type
//        Glide.with(this@MainActivity)
//            .load("https://avatars.githubusercontent.com/u/118094020?v=4${itemsItem.avatarUrl}")
//            .into(binding.ivPicture)
//    }
    private fun setGithubData(itemsItem :List<ItemsItem>) {
        val adapter = GithubAdapter()
        adapter.submitList(itemsItem)
        binding.rvGithub.adapter = adapter

    }
    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}