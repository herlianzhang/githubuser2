package com.latihangoding.githubuserapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.latihangoding.githubuserapp.model.ItemModel
import com.latihangoding.githubuserapp.network.GithubApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class ListViewModel : ViewModel() {

    private val _usersModel = MutableLiveData<List<ItemModel>>()
    val usersModel: LiveData<List<ItemModel>>
        get() = _usersModel

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isShowNoData = MutableLiveData<Boolean>()
    val isShowNoData: LiveData<Boolean>
        get() = _isShowNoData

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean>
        get() = _isError

    private var job = Job()

    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    init {
        _isShowNoData.postValue(true)
        _isError.postValue(false)
    }

    fun getSearch(username: String) {
        coroutineScope.launch(block = {
            _isLoading.postValue(true)
            if (_isShowNoData.value == true) {
                _isShowNoData.postValue(false)
            }
            val getSearchDeferred = GithubApi.retrofitService.getSearchDataAsync(username)

            try {
                val result = getSearchDeferred.await()
                if (result.items.isEmpty()) {
                    _isShowNoData.postValue(true)
                }
                _usersModel.postValue(result.items)
            } catch (e: Exception) {
                _isError.postValue(true)
                Log.e("error", "username: $username cause: $e")
            }

            _isLoading.postValue(false)
        })
    }

    fun getFollowers(username: String) {
        coroutineScope.launch {
            _isLoading.postValue(true)
            if (_isShowNoData.value == true) {
                _isShowNoData.postValue(false)
            }
            val getSearchDeferred = GithubApi.retrofitService.getFollowersAsync(username)

            try {
                val result = getSearchDeferred.await()
                if (result.isEmpty()) {
                    _isShowNoData.postValue(true)
                }
                _usersModel.postValue(result)
            } catch (e: Exception) {
                _isError.postValue(true)
                Log.e("error", "username: $username cause: $e")
            }

            _isLoading.postValue(false)
        }
    }

    fun getFollowing(username: String) {
        coroutineScope.launch {
            _isLoading.postValue(true)
            if (_isShowNoData.value == true) {
                _isShowNoData.postValue(false)
            }
            val getSearchDeferred = GithubApi.retrofitService.getFollowingAsync(username)
            try {
                val result = getSearchDeferred.await()
                if (result.isEmpty()) {
                    _isShowNoData.postValue(true)
                }
                _usersModel.postValue(result)
            } catch (e: Exception) {
                _isError.postValue(true)
                Log.e("error", "username: $username cause: $e")
            }

            _isLoading.postValue(false)
        }
    }

    fun doneShowErrorMessage() {
        _isError.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}