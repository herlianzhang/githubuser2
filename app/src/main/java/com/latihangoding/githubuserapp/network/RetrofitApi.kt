package com.latihangoding.githubuserapp.network

import com.latihangoding.githubuserapp.helpers.Values
import com.latihangoding.githubuserapp.model.ProfileModel
import com.latihangoding.githubuserapp.model.SearchModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitApi {
    @GET("search/users")
    fun getSearchData(
        @Query("q") username: String,
        @Header("Authorization") token: String = Values.TOKEN
    ): Deferred<SearchModel>

    @GET("users/{username}")
    fun getProfile(
        @Path("username") username: String,
        @Header("Authorization") token: String = Values.TOKEN
    ): Deferred<ProfileModel>

//    @GET("users/{username}/followers")
//    fun getFollowers(
//        @Path("username") username: String,
//        @Header("Authorization") token: String = Values.TOKEN
//    ): Call<List<ItemModel>>
//
//    @GET("users/{username}/following")
//    fun getFollowing(
//        @Path("username") username: String,
//        @Header("Authorization") token: String = Values.TOKEN
//    ): Call<List<ItemModel>>
}