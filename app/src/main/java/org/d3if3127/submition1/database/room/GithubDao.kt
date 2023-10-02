package org.d3if3127.submition1.database.room

import androidx.lifecycle.LiveData
import androidx.room.*
import org.d3if3127.submition1.database.entity.GithubEntity

@Dao
interface GithubDao {
    @Query("SELECT * FROM github ORDER BY username")
    fun getGithub(): LiveData<List<GithubEntity>>

    @Query("SELECT * FROM github where bookmarked = 1")
    fun getBookmarkedGithub(): LiveData<List<GithubEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertGithub(github: List<GithubEntity>)

    @Update
    fun updateGithub(github: GithubEntity)

    @Query("DELETE FROM github WHERE bookmarked = 0")
    fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM github WHERE username = :username AND bookmarked = 1)")
    fun isGithubBookmarked(username: String): Boolean
}