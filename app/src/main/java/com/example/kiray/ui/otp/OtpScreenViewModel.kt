package com.example.kiray.ui.otp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiray.data.api.SignUpRequest
import com.example.kiray.data.repository.UserRepository
import com.example.kiray.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpScreenViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel(){

    var error = mutableStateOf("")
    var success = MutableSharedFlow<Unit>()
    var loading = mutableStateOf(false)

    fun registerUser(data: SignUpRequest){
        viewModelScope.launch {
            loading.value = true
            val response = userRepository.registerUser(data)

            if(!response.success)error.value = response.message
            loading.value = false
            if(response.success){
                success.emit(Unit)
            }
        }
    }
}