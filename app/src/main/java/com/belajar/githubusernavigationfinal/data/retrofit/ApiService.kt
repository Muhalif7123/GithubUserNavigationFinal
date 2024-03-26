package com.belajar.githubusernavigationfinal.data.retrofit

import com.belajar.githubusernavigationfinal.data.response.DataDetail
import com.belajar.githubusernavigationfinal.data.response.ItemsItem
import com.belajar.githubusernavigationfinal.data.response.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    suspend fun getUser(@Query("q") q: String, @Query("per_page") perPage: Int): UserResponse

    @GET("users/{username}")
    fun getDetailUser(@Path("username") login: String): Call<DataDetail>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") login: String,
        @Query("per_page") perPage: Int
    ): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowings(
        @Path("username") login: String,
        @Query("per_page") perPage: Int
    ): Call<List<ItemsItem>>
}