package org.d3if3127.submition1.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import org.d3if3127.submition1.data.response.GithubResponse
import org.d3if3127.submition1.data.retrofit.ApiConfig

import org.d3if3127.submition1.data.retrofit.ApiService

import org.d3if3127.submition1.database.entity.GithubEntity
import org.d3if3127.submition1.database.room.GithubDao
import org.d3if3127.submition1.utils.AppExecutors
import org.d3if3127.submition1.viewmodel.MainViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GithubRepository private constructor(
    private val apiService: ApiService,
    private val githubDao: GithubDao,
    private val appExecutors: AppExecutors
) {
    private val result = MediatorLiveData<Result<List<GithubEntity>>>()
    fun getBookmarkedGithub(): LiveData<List<GithubEntity>> {
        return githubDao.getBookmarkedGithub()
    }
    fun setBookmarkedGithub(github: GithubEntity, bookmarkState: Boolean) {
        appExecutors.diskIO.execute {
            github.isBookmarked = bookmarkState
            githubDao.updateGithub(github)
        }
    }
    fun getHeadlineGithub(): LiveData<Result<List<GithubEntity>>> {
        result.value = Result.Loading
        val client =  ApiConfig.getApiService().getGithubUser(MainViewModel.GITHUB_Query)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(call: Call<GithubResponse>, response: Response<GithubResponse>) {
                if (response.isSuccessful) {
                    val itemItems = response.body()?.items
                    val githubList = ArrayList<GithubEntity>()
                    appExecutors.diskIO.execute {
                        itemItems?.forEach { items ->
                            val isBookmarked = githubDao.isGithubBookmarked(items.login)
                            val github = GithubEntity(
                                items.login,
                                items.avatarUrl,
                                isBookmarked
                            )
                            githubList.add(github)
                        }
                        githubDao.deleteAll()
                        githubDao.insertGithub(githubList)
                    }
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }
        })
        val localData = githubDao.getGithub()
        result.addSource(localData) { newData: List<GithubEntity> ->
            result.value = Result.Success(newData)
        }
        return result
    }

    companion object {
        @Volatile
        private var instance: GithubRepository? = null
        fun getInstance(
            apiService: ApiService,
            githubDao: GithubDao,
            appExecutors: AppExecutors
        ): GithubRepository =
            instance ?: synchronized(this) {
                instance ?: GithubRepository(apiService, githubDao, appExecutors)
            }.also { instance = it }
    }
}