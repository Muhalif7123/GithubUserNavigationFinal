package com.belajar.githubusernavigationfinal.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.belajar.githubusernavigationfinal.data.response.DataDetail
import com.belajar.githubusernavigationfinal.data.response.ItemsItem
import com.belajar.githubusernavigationfinal.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _followings = MutableLiveData<List<ItemsItem>>()
    val followings: LiveData<List<ItemsItem>> = _followings

    private val _followers = MutableLiveData<List<ItemsItem>>()
    val followers: LiveData<List<ItemsItem>> = _followers

    private val _dataDetail = MutableLiveData<DataDetail>()
    val dataDetail: LiveData<DataDetail> = _dataDetail

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun getFollowings(apiId: String?) {
        _loading.value = true
        val client = apiId?.let { ApiConfig.getApiConfig().getFollowings(it, 100) }
        client?.enqueue(object : Callback<List<ItemsItem>> {

            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _loading.value = false
                val responseBody = response.body()
                if (response.isSuccessful && response.body() != null) {
                    for (i in 0 until response.body()!!.size) {
                        _followings.value = responseBody!!
                    }
                } else Log.e(MainActivity.TAG, "onFailure: ${response.message()}")

            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _loading.value = false
                Log.e(MainActivity.TAG, "on failure: ${t.message}")
            }
        })

    }

    fun getFollowers(apiId: String?) {
        _loading.value = true
        val client = apiId?.let { ApiConfig.getApiConfig().getFollowers(it, 100) }
        client?.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _loading.value = false
                val responseBody = response.body()
                if (response.isSuccessful && response.body() != null) {
                    for (i in 0 until response.body()!!.size) {
                        _followers.value = responseBody!!
                    }
                } else Log.e(MainActivity.TAG, "onFailure: ${response.message()}")
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _loading.value = false
                Log.e(MainActivity.TAG, "on failure: ${t.message}")
            }
        })
    }

    fun getDataComplete(apiId: String?) {
        _loading.value = true
        val client = apiId?.let { ApiConfig.getApiConfig().getDetailUser(it) }
        client?.enqueue(object : Callback<DataDetail> {
            override fun onResponse(call: Call<DataDetail>, response: Response<DataDetail>) {
                _loading.value = false
                if (response.isSuccessful) {
                    _dataDetail.value = response.body()
                } else {
                    Log.e(MainActivity.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DataDetail>, t: Throwable) {
                _loading.value = false
                Log.e(MainActivity.TAG, "on failure: ${t.message}")
            }
        })
    }


}