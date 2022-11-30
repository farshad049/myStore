package com.example.mystore.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystore.data.model.mapper.UserMapper
import com.example.mystore.data.repository.AuthRepository
import com.example.mystore.redux.ApplicationState
import com.example.mystore.redux.Store
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val store : Store<ApplicationState>,
    private val authRepository : AuthRepository ,
    private val userMapper: UserMapper
):ViewModel() {

    fun login (username: String ,password: String) = viewModelScope.launch {
        val response = authRepository.login(username , password)
        Log.e("LOGIN", response.body().toString())

        if (response.isSuccessful){
            val userResponse = authRepository.login(4)
            store.update { currentState ->
                currentState.copy(
                    domainUser = userResponse.body()?.let { userMapper.buildFrom(it) }
                )
            }

            if(userResponse.body() == null){
                Log.e("LOGIN", response.errorBody()?.toString() ?: response.message())
            }

        }else{
            Log.e("LOGIN", response.errorBody()?.byteStream()?.bufferedReader()?.readLine() ?: "invalid login")
        }
    }


    fun logout() = viewModelScope.launch {
        store.update {
            it.copy(domainUser = null)
        }
    }




}

