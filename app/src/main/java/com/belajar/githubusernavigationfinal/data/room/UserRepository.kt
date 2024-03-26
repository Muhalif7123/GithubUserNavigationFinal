package com.belajar.githubusernavigationfinal.data.room

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.belajar.githubusernavigationfinal.data.Result
import com.belajar.githubusernavigationfinal.data.entity.UserEntity
import com.belajar.githubusernavigationfinal.data.retrofit.ApiConfig
import com.belajar.githubusernavigationfinal.data.retrofit.ApiService

class UserRepository private constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
){
    fun getData(): LiveData<Result<List<UserEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val client = ApiConfig.getApiConfig().getUser("a", perPage = 100)
            val user = client.items
            val userLists = user.map {
                val favorite = userDao.userFavorite(it.login)
                UserEntity(
                    it.login,
                    it.htmlUrl,
                    it.url,
                    it.avatarUrl,
                    favorite
                )
            }
            userDao.deleteUser()
            userDao.insertUser(userLists)
        } catch (e: Exception) {
            Log.d("UserRepository", "getData: ${e.message.toString()}")
        }
        val localMemoryData: LiveData<Result<List<UserEntity>>> =
            userDao.getUser().map { Result.Success(it) }
        emitSource(localMemoryData)
    }


    fun getDataSearch(text: String): LiveData<Result<List<UserEntity>>> = liveData {
        emit(Result.Loading)
        try {
            val client = ApiConfig.getApiConfig().getUser(text, perPage = 100)
            val user = client.items
            val userList = user.map {
                val favorite = userDao.userFavorite(it.login)
                UserEntity(
                    it.login,
                    it.htmlUrl,
                    it.url,
                    it.avatarUrl,
                    favorite
                )
            }
            userDao.deleteUser()
            userDao.insertUser(userList)

        } catch (e: Exception) {
            Log.d("UserRepository", "getDataSearch: ${e.message.toString()}")
        }

        val localMemoryData: LiveData<Result<List<UserEntity>>> =
            userDao.getUserByNoFavorite(text).map { Result.Success(it) }
        emitSource(localMemoryData)
    }


    fun getFavorite(): LiveData<List<UserEntity>> {
        return userDao.getUserFavorite()
    }

    suspend fun setFavorite(user: UserEntity, favorite: Boolean) {
        user.favorite = favorite
        userDao.updateUser(user)
    }

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            userDao: UserDao
        ): UserRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserRepository(apiService, userDao)
            }.also { INSTANCE = it }
    }

}