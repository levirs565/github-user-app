package com.levirs.githubuser.service

import com.levirs.githubuser.model.SearchResult
import com.levirs.githubuser.model.User
import com.levirs.githubuser.model.UserDetails
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {
    companion object {
        fun newInstance(): GithubService {
            val client = OkHttpClient.Builder()
                .addInterceptor {
                    val request = it.request().newBuilder()
                        .addHeader("Authorization", "token ***REMOVED***")
                        .build()
                    it.proceed(request)
                }.build()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(GithubService::class.java)
        }
    }

    @GET("users")
    suspend fun getUserList(): List<User>
    @GET("search/users")
    suspend fun searchUser(@Query("q") query: String): SearchResult<User>
    @GET("users/{userName}")
    suspend fun getUserDetails(@Path("userName") userName: String): UserDetails
    @GET("users/{userName}/followers")
    suspend fun getUserFollowerList(@Path("userName") userName: String): List<User>
    @GET("users/{userName}/following")
    suspend fun getUserFollowingList(@Path("userName") userName: String): List<User>
}