package com.levirs.githubuser.data.github

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
    suspend fun getUserList(): List<GithubUserEntity>
    @GET("search/users")
    suspend fun searchUser(@Query("q") query: String): GithubSearchEntity<GithubUserEntity>
    @GET("users/{userName}")
    suspend fun getUserDetails(@Path("userName") userName: String): GithubUserDetailsEntity
    @GET("users/{userName}/followers")
    suspend fun getUserFollowerList(@Path("userName") userName: String): List<GithubUserEntity>
    @GET("users/{userName}/following")
    suspend fun getUserFollowingList(@Path("userName") userName: String): List<GithubUserEntity>
}