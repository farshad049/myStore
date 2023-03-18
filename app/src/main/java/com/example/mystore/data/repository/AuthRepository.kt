package com.example.mystore.data.repository

import com.example.mystore.data.model.mapper.UserMapper
import com.example.mystore.ui.profile.model.LoginResponse
import com.example.mystore.data.model.network.NetworkUser
import com.example.mystore.data.model.network.post.LoginPostBody
import com.example.mystore.data.network.AuthService
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authService : AuthService ,
    private val userMapper: UserMapper
){
    suspend fun login (username : String , password : String):Response<LoginResponse>{
        return authService.login(LoginPostBody(username = username , password =  password))
    }

    suspend fun getUserInfo (userId : Int):Response<NetworkUser>{
        return authService.fetchUser(userId)
    }
}