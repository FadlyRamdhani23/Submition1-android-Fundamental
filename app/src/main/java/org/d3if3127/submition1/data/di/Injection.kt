package org.d3if3127.submition1.data.di

import android.content.Context
import org.d3if3127.submition1.data.retrofit.ApiConfig
import org.d3if3127.submition1.database.room.GithubDatabase
import org.d3if3127.submition1.repository.GithubRepository
import org.d3if3127.submition1.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): GithubRepository {
        val apiService = ApiConfig.getApiService()
        val database = GithubDatabase.getInstance(context)
        val dao = database.githubDao()
        val appExecutors = AppExecutors()
        return GithubRepository.getInstance(apiService, dao, appExecutors)
    }
}