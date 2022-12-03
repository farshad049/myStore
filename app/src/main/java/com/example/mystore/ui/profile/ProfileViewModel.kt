package com.example.mystore.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystore.data.model.mapper.UserMapper
import com.example.mystore.data.repository.AuthRepository
import com.example.mystore.redux.ApplicationState
import com.example.mystore.redux.Store
import com.example.mystore.util.capitalize
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val store : Store<ApplicationState>,
    private val authRepository : AuthRepository ,
    private val userMapper: UserMapper
):ViewModel() {

    private fun ResponseBody?.parseError():String? {
        return this?.byteStream()?.bufferedReader()?.readLine()?.capitalize()
    }



    fun login (username: String ,password: String) = viewModelScope.launch {
        val response = authRepository.login(username , password)

        if (response.isSuccessful){
            val userResponse = authRepository.login(4)
            store.update { currentState ->
                val authState = userResponse.body()?.let {body ->
                    ApplicationState.UserLoginResponse.Authenticated(user = userMapper.buildFrom(body))
                } ?: ApplicationState.UserLoginResponse.UnAuthenticated(error = response.errorBody()?.parseError())
                return@update currentState.copy(user = authState)
            }
        }else{
            store.update { currentState ->
                return@update currentState.copy(
                    user= ApplicationState.UserLoginResponse.UnAuthenticated(error = response.errorBody()?.parseError())
                )
            }

        }
    }



    fun logout() = viewModelScope.launch {
        store.update {
            it.copy(user = ApplicationState.UserLoginResponse.UnAuthenticated())
        }
    }




}

