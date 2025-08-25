package com.example.kiray.ui.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiray.data.api.SignInRequest
import com.example.kiray.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
):ViewModel(){

    var error = mutableStateOf("")
    val success = MutableSharedFlow<Unit>()
    var loading = mutableStateOf(false)

    fun loginUser(signInRequest: SignInRequest){
        viewModelScope.launch {
            loading.value = true
            val response = userRepository.loginUser(signInRequest)
            var suc = response.success
            if(!suc)error.value = response.message
            else{
                if(response.token==null){
                    error.value = response.message
                    suc=false
                }
                else {
                    val res = userRepository.saveJwt(response.token)
                    Log.d("token", response.token)
                    if (!res.success) {
                        error.value = "Something went wrong"
                        suc = false
                    }
                }
            }
            loading.value = false
            if(suc){
                success.emit(Unit)
            }
        }
    }
}