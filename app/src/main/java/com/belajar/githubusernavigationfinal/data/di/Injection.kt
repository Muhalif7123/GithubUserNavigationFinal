package com.belajar.githubusernavigationfinal.data.di

import android.content.Context
import com.belajar.githubusernavigationfinal.data.retrofit.ApiConfig
import com.belajar.githubusernavigationfinal.data.room.UserDatabase
import com.belajar.githubusernavigationfinal.data.room.UserRepository

object Injection {
    fun provideRepository(context: Context): UserRepository {
        val apiService = ApiConfig.getApiConfig()
        val database = UserDatabase.getInstance(context)
        val dao = database.userDao()
        return UserRepository.getInstance(apiService, dao)
    }
}