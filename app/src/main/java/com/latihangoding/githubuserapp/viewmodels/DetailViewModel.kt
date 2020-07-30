package com.latihangoding.githubuserapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.latihangoding.githubuserapp.model.ProfileModel
import com.latihangoding.githubuserapp.network.GithubApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailViewModel : ViewModel() {

    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    private val _profileModel = MutableLiveData<ProfileModel>()
    val profileModel: LiveData<ProfileModel>
        get() = _profileModel

    private val _isBackClicked = MutableLiveData<Boolean>()
    val isBackClicked: LiveData<Boolean>
        get() = _isBackClicked

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean>
        get() = _isError

    init {
        _isBackClicked.postValue(false)
        _isError.postValue(false)
    }

    fun getProfile(username: String) {
        coroutineScope.launch {
            val profileDeffered = GithubApi.retrofitService.getProfileAsync(username)
            try {
                val result = profileDeffered.await()
                _profileModel.postValue(result)
            } catch (e: Exception) {
                _isError.postValue(true)
                Log.e("error", "username: $username cause: $e")
            }
        }
    }

    fun backClicked() {
        _isBackClicked.postValue(true)
    }

    fun doneShowErrorMessage() {
        _isError.postValue(false)
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}