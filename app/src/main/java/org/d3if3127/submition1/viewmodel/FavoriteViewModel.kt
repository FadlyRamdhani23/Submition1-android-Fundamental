package org.d3if3127.submition1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import org.d3if3127.submition1.data.local.entity.GithubEntity
import org.d3if3127.submition1.data.local.room.GithubDao
import org.d3if3127.submition1.data.local.room.GithubDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private var githubDao: GithubDao?
    private var githubDb: GithubDatabase?

    init {
        githubDb = GithubDatabase.getDatabase(application)
        githubDao = githubDb?.githubDao()
    }

    fun getFav(): LiveData<List<GithubEntity>>?{
        return githubDao?.getFav()
    }
}