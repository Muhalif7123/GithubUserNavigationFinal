package com.belajar.githubusernavigationfinal.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.belajar.githubusernavigationfinal.data.Result
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

class SearchViewModel(private val userRepository: UserRepository) : ViewModel() {
//    private val _data = MutableLiveData<List<ItemsItem>>()
//    val data: LiveData<List<ItemsItem>> = _data
//
//    private val _loading = MutableLiveData<Boolean>()
//    val loading: LiveData<Boolean> = _loading
//
//    private val _search = MutableLiveData<List<ItemsItem>>()
//    val search: LiveData<List<ItemsItem>> = _search

    fun getDataSearch(text: String): LiveData<Result<List<UserEntity>>> {
        return userRepository.getDataSearch(text)
    }

//    init {
//        getData()
//    }

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
//
//
//    fun getDataSearch(text: String) {
//        _loading.value = true
//        val client = ApiConfig.getApiConfig().getUser(text, perPage = 100)
//        client.enqueue(object : Callback<UserResponse> {
//            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
//                _loading.value = false
//                if (response.isSuccessful) {
//                    _search.value = response.body()!!.items
//                    Log.d(MainActivity.TAG, "Search status: ${response.message()}")
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