package com.example.kiray.ui.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiray.data.repository.UserRepository
import com.example.kiray.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val userRepository: UserRepository
):ViewModel(){

    var error = mutableStateOf("")
    var loading = mutableStateOf(false)


    var success = MutableSharedFlow<Unit>()




    fun requestOtp(user:User){
        viewModelScope.launch {
            loading.value = true
            val response = userRepository.requestOtp(user)
            if(!response.success)error.value = response.message
            loading.value = false
            if(response.success){
                success.emit(Unit)
            }
        }
    }

}