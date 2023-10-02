package org.d3if3127.submition1.database.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "github")
@Parcelize
class GithubEntity(
    @field:ColumnInfo(name = "username")
    @field:PrimaryKey
    val username: String,

    @field:ColumnInfo(name = "avatarUrl")
    var avatarUrl: String? = null,

    @field:ColumnInfo(name = "bookmarked")
    var isBookmarked: Boolean
): Parcelable