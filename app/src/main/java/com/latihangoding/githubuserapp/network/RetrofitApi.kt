package com.latihangoding.githubuserapp.network

import com.latihangoding.githubuserapp.helpers.Values
import com.latihangoding.githubuserapp.model.ItemModel
import com.latihangoding.githubuserapp.model.ProfileModel
import com.latihangoding.githubuserapp.model.SearchModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitApi {
    @GET("search/users")
    fun getSearchDataAsync(
        @Query("q") username: String,
        @Header("Authorization") token: String = Values.TOKEN
    ): Deferred<SearchModel>

    @GET("users/{username}")
    fun getProfileAsync(
        @Path("username") username: String,
        @Header("Authorization") token: String = Values.TOKEN
    ): Deferred<ProfileModel>

    @GET("users/{username}/followers")
    fun getFollowersAsync(
        @Path("username") username: String,
        @Header("Authorization") token: String = Values.TOKEN
    ): Deferred<List<ItemModel>>

    @GET("users/{username}/following")
    fun getFollowingAsync(
        @Path("username") username: String,
        @Header("Authorization") token: String = Values.TOKEN
    ): Deferred<List<ItemModel>>
}