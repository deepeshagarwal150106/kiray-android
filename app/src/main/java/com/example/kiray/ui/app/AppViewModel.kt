package com.example.kiray.ui.app

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.kiray.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppViewModel@Inject constructor(
    userRepository: UserRepository
): ViewModel(){
    val token = mutableStateOf("")
    init {
        token.value = userRepository.getJwt().toString()
    }
}