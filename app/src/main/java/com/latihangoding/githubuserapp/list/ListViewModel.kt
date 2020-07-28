package com.latihangoding.githubuserapp.list

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

    private var job = Job()

    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    init {
        _isShowNoData.postValue(true)
    }

    fun getSearch(username: String) {
        coroutineScope.launch {
            val getSearchDeferred = GithubApi.retrofitService.getSearchData(username)

            _isLoading.postValue(true)
            if (_isShowNoData.value == true) {
                _isShowNoData.postValue(false)
            }
            try {
                val result = getSearchDeferred.await()
                if (result.items.isEmpty()) {
                    _isShowNoData.postValue(true)
                }
                _usersModel.postValue(result.items)
                Log.e("masuk", "Success pak eko")
            } catch (e: Exception) {
                Log.e("masuk", "Fail pak eko")
            }

            _isLoading.postValue(false)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}