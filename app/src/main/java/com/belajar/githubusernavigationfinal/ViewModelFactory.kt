package com.belajar.githubusernavigationfinal

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.belajar.githubusernavigationfinal.data.di.Injection
import com.belajar.githubusernavigationfinal.data.room.UserRepository
import com.belajar.githubusernavigationfinal.ui.home.HomeViewModel
import com.belajar.githubusernavigationfinal.ui.search.SearchViewModel
import com.belajar.githubusernavigationfinal.ui.setting.SettingPreference
import com.belajar.githubusernavigationfinal.ui.setting.SettingViewModel

class ViewModelFactory(
    private val userRepository: UserRepository? = null,
    val settingPreference: SettingPreference? = null
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECK_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return userRepository?.let { HomeViewModel(it) } as T
        }
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return userRepository?.let { SearchViewModel(it) } as T
        }
        if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            return settingPreference?.let { SettingViewModel(it) } as T
        }
        throw IllegalArgumentException("Unknown Viewmodel Class: ${modelClass.name}")
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { INSTANCE = it }
    }

}