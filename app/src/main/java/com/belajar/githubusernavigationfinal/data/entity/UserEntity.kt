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
    var favorite: Boolean,

    @ColumnInfo(name = "company")
    val company: String? = null,

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "following")
    val following: Int? = 0,

    @ColumnInfo(name = "followers")
    val followers: Int? = 0,

    @ColumnInfo(name = "following_url")
    val followingUrl: String? = null,

    @ColumnInfo(name = "followers_url")
    val followersUrl: String? = null

)
