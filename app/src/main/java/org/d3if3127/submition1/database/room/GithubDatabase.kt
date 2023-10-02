package org.d3if3127.submition1.database.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3if3127.submition1.database.entity.GithubEntity

@Database(entities = [GithubEntity::class], version = 1, exportSchema = false)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun githubDao(): GithubDao

    companion object {
        @Volatile
        private var instance: GithubDatabase? = null
        fun getInstance(context: Context): GithubDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    GithubDatabase::class.java, "Github.db"
                ).build()
            }
    }
}