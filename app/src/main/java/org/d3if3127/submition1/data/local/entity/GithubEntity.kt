package org.d3if3127.submition1.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "github")
data class GithubEntity(

    @PrimaryKey
    val id : Int,
    val login : String,
    val avatar : String,
): Serializable
