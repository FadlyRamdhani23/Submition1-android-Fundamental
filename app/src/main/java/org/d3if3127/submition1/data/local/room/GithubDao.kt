package org.d3if3127.submition1.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import org.d3if3127.submition1.data.local.entity.GithubEntity

@Dao
interface GithubDao {
    @Insert
    suspend fun addToFav(githubEntity: GithubEntity)

    @Query("SELECT * FROM github")
    fun getFav(): LiveData<List<GithubEntity>>

    @Query("SELECT count(*) FROM github WHERE github.id = :id")
    suspend fun checked(id: Int): Int

    @Query("DELETE FROM github WHERE github.id = :id")
    suspend fun removeFav(id: Int): Int

}