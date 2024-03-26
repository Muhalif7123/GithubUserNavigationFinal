package com.belajar.githubusernavigationfinal.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class SettingViewModel(private val settingPreference: SettingPreference) : ViewModel() {

    fun getDarkMode(): LiveData<Boolean> {
        return settingPreference.getDarkMode().asLiveData()
    }

    fun saveDarkMode(isActive: Boolean) {
        viewModelScope.launch {
            settingPreference.saveDarkMode(isActive)
        }
    }

}