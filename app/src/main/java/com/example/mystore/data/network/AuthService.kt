package com.example.mystore.data.network

import com.example.mystore.ui.profile.model.LoginResponse
import com.example.mystore.data.model.network.NetworkUser
import com.example.mystore.data.model.network.post.LoginPostBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AuthService {

    @POST("auth/login")
    suspend fun login(@Body loginPostBody : LoginPostBody):Response<LoginResponse>

    @GET("users/{user-id}")
    suspend fun fetchUser(@Path("user-id") userId: Int):Response<NetworkUser>

}