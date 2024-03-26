package com.belajar.githubusernavigationfinal.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class UserEntity(
    @ColumnInfo(name = "login")
    @PrimaryKey
    val login: String,

    @ColumnInfo(name = "html_url")
    val htmlUrl: String? = null,

    @ColumnInfo(name = "url")
    val url: String? = null,

    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String? = null,

    @ColumnInfo(name = "favorite")
    var favorite: Boolean

)
