package org.d3if3127.submition1.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.d3if3127.submition1.data.response.GithubResponse
import org.d3if3127.submition1.data.response.ItemsItem
import org.d3if3127.submition1.data.retrofit.ApiConfig
import org.d3if3127.submition1.database.entity.GithubEntity
import org.d3if3127.submition1.repository.GithubRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel (private val githubRepository: GithubRepository): ViewModel(){

    private val _searchQuery = MutableLiveData<String>()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "MainViewModel"
        var GITHUB_Query = "fadly"

    }
    fun getBookmarkedGithub() = githubRepository.getBookmarkedGithub()
    fun getHeadlineGithub() = githubRepository.getHeadlineGithub()
    fun saveNews(github : GithubEntity){
        githubRepository.setBookmarkedGithub(github, true)
    }
    fun deleteNews(github : GithubEntity) {
        githubRepository.setBookmarkedGithub(github, false)
    }
    fun setSearchQuery(query: String) {
        _searchQuery.value = query
        GITHUB_Query = query
    }
}