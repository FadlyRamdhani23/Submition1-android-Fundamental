package org.d3if3127.submition1.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "github")
data class GithubEntity(
    val login : String,
    @PrimaryKey
    val id : Int,
    val avatar : String,
): Serializable
