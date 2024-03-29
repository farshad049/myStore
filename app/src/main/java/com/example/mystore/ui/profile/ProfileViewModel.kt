package com.example.mystore.ui.profile

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystore.data.model.domain.Address
import com.example.mystore.data.model.mapper.UserMapper
import com.example.mystore.data.repository.AuthRepository
import com.example.mystore.redux.ApplicationState
import com.example.mystore.redux.Store
import com.example.mystore.util.capitalize
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val store : Store<ApplicationState>,
    private val authRepository : AuthRepository ,
    private val userMapper: UserMapper
):ViewModel() {

    private val _intentFlow  = MutableStateFlow<Intent?>(null)
    val intentFlow : StateFlow<Intent?> = _intentFlow

    private fun ResponseBody?.parseError():String? {
        return this?.byteStream()?.bufferedReader()?.readLine()?.capitalize()
    }



    fun login (username: String ,password: String) = viewModelScope.launch {
        val response = authRepository.login(username , password)

        if (response.isSuccessful){
            val userResponse = authRepository.getUserInfo(4)
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
        store.update {currentState->
            return@update currentState.copy(user = ApplicationState.UserLoginResponse.UnAuthenticated())
        }
    }


    fun sendCallIntent() = viewModelScope.launch{
        val phoneNumber : String? = store.read { currentState ->
            if (currentState.user is ApplicationState.UserLoginResponse.Authenticated) {
                currentState.user.user.phoneNumber
            } else {
                null
            }
        }

//            val phoneNumber : String = store.read {
//                (it.user as ApplicationState.UserLoginResponse.Authenticated).user.phoneNumber
//            }

            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$phoneNumber")
            _intentFlow.emit(intent)
        }


    fun sendMapIntent() = viewModelScope.launch{
        val address : Address = store.read {currentState ->
            (currentState.user as ApplicationState.UserLoginResponse.Authenticated).user.address
        }


        val uri = Uri.parse("geo:${address.lat},${address.long}?z=9&q=${address.city}")

        val mapIntent = Intent(Intent.ACTION_VIEW, uri)
        //i can set this if i want intent to be open only with google map and not other map apps
        //mapIntent.setPackage("com.google.android.apps.maps")
        _intentFlow.emit(mapIntent)

    }
}

