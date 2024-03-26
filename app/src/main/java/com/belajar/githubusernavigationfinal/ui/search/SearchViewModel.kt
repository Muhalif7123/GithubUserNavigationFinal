package com.belajar.githubusernavigationfinal.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belajar.githubusernavigationfinal.data.Result
import com.belajar.githubusernavigationfinal.data.entity.UserEntity
import com.belajar.githubusernavigationfinal.data.room.UserRepository
import kotlinx.coroutines.launch

class SearchViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun getDataSearch(text: String): LiveData<Result<List<UserEntity>>> {
        return userRepository.getDataSearch(text)
    }

    fun saveUser(user: UserEntity) {
        viewModelScope.launch {
            userRepository.setFavorite(user, true)
        }
    }

    fun deleteUser(user: UserEntity) {
        viewModelScope.launch {
            userRepository.setFavorite(user, false)
        }
    }

}