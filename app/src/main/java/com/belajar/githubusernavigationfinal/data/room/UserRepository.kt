package com.belajar.githubusernavigationfinal.data.room

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.belajar.githubusernavigationfinal.data.Result
import com.belajar.githubusernavigationfinal.data.entity.UserEntity
import com.belajar.githubusernavigationfinal.data.response.DataDetail
import com.belajar.githubusernavigationfinal.data.response.ItemsItem
import com.belajar.githubusernavigationfinal.data.response.UserResponse
import com.belajar.githubusernavigationfinal.data.retrofit.ApiConfig
import com.belajar.githubusernavigationfinal.data.retrofit.ApiService
import com.belajar.githubusernavigationfinal.ui.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.coroutines.*

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
        val localMemoryData: LiveData<Result<List<UserEntity>>> = userDao.getUser().map { Result.Success(it) }
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
        val localMemoryData: LiveData<Result<List<UserEntity>>> = userDao.getUser().map { Result.Success(it) }
        emitSource(localMemoryData)
    }

//    fun getFollowings(apiId: String?): LiveData<Result<List<UserEntity>>> = liveData {
//        emit(Result.Loading)
//        try {
//            val client = apiId?.let { apiService.getFollowings(it, 100) }
//            val user = client?.map {
//                val favorite = userDao.userFavorite(it.login)
//                UserEntity(
//                    it.login,
//                    it.htmlUrl,
//                    it.url,
//                    it.avatarUrl,
//                    favorite,
//                    followingUrl = it.followingUrl
//                )
//            }
//            userDao.deleteUser()
//            if (user != null) {
//                userDao.insertUser(user)
//            }
//
//
//        } catch (e: Exception) {
//            emit(Result.Failure(e.message.toString()))
//            Log.d("UserRepository", "getDataSearch: ${e.message.toString()}")
//        }
//        val localDataMemory: LiveData<Result<List<UserEntity>>> = userDao.getUser().map { Result.Success(it) }
//        emitSource(localDataMemory)
//    }
//
//    fun getFollowers(apiId: String?): LiveData<Result<List<UserEntity>>> = liveData {
//        emit(Result.Loading)
//        try {
//            val client = apiId?.let { ApiConfig.getApiConfig().getFollowers(it, 100) }
//            val user = client?.map {
//                val favorite = userDao.userFavorite(it.login)
//                UserEntity(
//                    it.login,
//                    it.htmlUrl,
//                    it.url,
//                    it.avatarUrl,
//                    favorite,
//                    followersUrl = it.followersUrl
//                )
//            }
//            userDao.deleteUser()
//            if (user != null) {
//                userDao.insertUser(user)
//            }
//        } catch (e: Exception) {
//            Log.d("UserRepository", "getDataSearch: ${e.message.toString()}")
//        }
//        val localDataMemory: LiveData<Result<List<UserEntity>>> = userDao.getUser().map { Result.Success(it) }
//        emitSource(localDataMemory)
//    }

//    fun getDataComplete(apiId: String?): LiveData<Result<List<UserEntity>>> = liveData {
//        emit(Result.Loading)
//        try {
//            val client = apiId?.let { ApiConfig.getApiConfig().getDetailUser1(it) }
//            val favorite = client?.login?.let { userDao.userFavorite(it) }?: false
//            val user: List<UserEntity> = listOf(
//                    UserEntity(
//                        client!!.login,
//                        client.htmlUrl,
//                        client.url,
//                        client.avatarUrl,
//                        favorite,
//                        client.company,
//                        client.name,
//                        client.following,
//                        client.followers)
//             )
//            userDao.deleteUser()
//            userDao.insertUser(user)
//
//        }catch (e: Exception) {
//            Log.d("UserRepository", "getDataSearch: ${e.message.toString()}")
//        }
//        val localMemoryData: LiveData<Result<List<UserEntity>>> = userDao.get.map { Result.Success(it) }
//        emitSource(localMemoryData)
//    }



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