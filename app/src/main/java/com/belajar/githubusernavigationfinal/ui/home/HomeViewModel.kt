package com.belajar.githubusernavigationfinal.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belajar.githubusernavigationfinal.data.entity.UserEntity
import com.belajar.githubusernavigationfinal.data.response.ItemsItem
import com.belajar.githubusernavigationfinal.data.response.UserResponse
import com.belajar.githubusernavigationfinal.data.retrofit.ApiConfig
import com.belajar.githubusernavigationfinal.data.room.UserRepository
import com.belajar.githubusernavigationfinal.ui.MainActivity
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(private val userRepository: UserRepository) : ViewModel() {

//    private val _data = MutableLiveData<List<ItemsItem>>()
//    val data: LiveData<List<ItemsItem>> = _data

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun getData() = userRepository.getData()

    fun getFavorite() = userRepository.getFavorite()

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





//    init {
//        getData()
//    }
//
//    private fun getData() {
//        _loading.value = true
//        val client = ApiConfig.getApiConfig().getUser("a", perPage = 100)
//        client.enqueue(object : Callback<UserResponse> {
//            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
//                _loading.value = false
//                if (response.isSuccessful) {
//                    _data.value = response.body()?.items
//                } else Log.e(MainActivity.TAG, "onFailure: ${response.message()}")
//            }
//
//            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
//                _loading.value = false
//                Log.e(MainActivity.TAG, "on failure: ${t.message}")
//            }
//        })
//    }

}