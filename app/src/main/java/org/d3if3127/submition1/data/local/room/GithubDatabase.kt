package org.d3if3127.submition1.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3if3127.submition1.data.local.entity.GithubEntity


@Database(entities = [GithubEntity::class], version = 1)
abstract class GithubDatabase : RoomDatabase() {

    abstract fun githubDao(): GithubDao

    companion object{
        var INSTANCE: GithubDatabase? = null

        fun getDatabase(context: Context): GithubDatabase? {
            if(INSTANCE == null){
                synchronized(GithubDatabase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        GithubDatabase::class.java,
                        "github.db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }

}