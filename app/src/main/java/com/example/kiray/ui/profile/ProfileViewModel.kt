package com.example.kiray.ui.profile


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiray.data.model.request.EditProfileRequest
import com.example.kiray.data.model.request.ProfileRequest
import com.example.kiray.data.repository.UserRepository
import com.example.kiray.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {



    fun logout() {
        userRepository.logout()
    }

    private val _profileUiState = MutableStateFlow(ProfileUiState())
    val profileUiState: StateFlow<ProfileUiState> = _profileUiState.asStateFlow()

    val profileSuccess = MutableSharedFlow<User>()

    fun getProfile() {
        viewModelScope.launch {
            try {
                _profileUiState.value = _profileUiState.value.copy(loading = true)
                val response = userRepository.getProfile()

                _profileUiState.value = ProfileUiState(
                    name = response.name,
                    email = response.email,
                    phone = response.phone,
                    address = response.address,
                    loading = false
                )
            } catch (e: Exception) {
                // handle error
                Log.d("profile",e.toString())
                _profileUiState.value = _profileUiState.value.copy(
                    loading = false
                )
            }
        }
    }

    private val _editProfileUiState = MutableStateFlow(EditProfileUiState())
    val editProfileUiState = _editProfileUiState.asStateFlow()

    fun editProfile(user: EditProfileRequest) {
        viewModelScope.launch {
            _editProfileUiState.value = EditProfileUiState(loading = true)
            try {
                val updatedUser = userRepository.updateUser(user)

                // Update state with updated user
                _editProfileUiState.value = EditProfileUiState(success = updatedUser)

            } catch (e: Exception) {
                _editProfileUiState.value = EditProfileUiState(error = e.message ?: "Something went wrong")
            }
        }
    }

    //TODO: Remove static values
    data class ProfileUiState(
        val name: String? = "Deepesh Agarwal",
        val email: String? = "deepesh123@gmail.com",
        val phone: String? = "5567467554",
        val address: String? = "Jaipur",
        val loading: Boolean = true
    )

    data class EditProfileUiState(
        val loading: Boolean = false,
        val success: User? = null,
        val error: String? = null
    )

}

