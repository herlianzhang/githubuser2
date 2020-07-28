package com.latihangoding.githubuserapp.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.latihangoding.githubuserapp.model.ProfileModel
import com.latihangoding.githubuserapp.model.UserModel
import com.latihangoding.githubuserapp.network.GithubApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailViewModel : ViewModel() {

    private val job = Job()
    private val coroutineScope = CoroutineScope(job + Dispatchers.Main)

    private val _profileModel = MutableLiveData<ProfileModel>()
    val profileModel: LiveData<ProfileModel>
        get() = _profileModel

    fun getProfile(username: String) {
        coroutineScope.launch {
            val profileDeffered = GithubApi.retrofitService.getProfile(username)
            try {
                val result = profileDeffered.await()
                _profileModel.postValue(result)
            } catch (e: Exception) {
                Log.e("masuk", "Fail pak eko sebab: \n$e")
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}