package com.example.kiray.ui.myHouse.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiray.data.model.request.house.HouseRequest
import com.example.kiray.data.repository.HouseRepository
import com.example.kiray.model.House
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyHouseViewModel @Inject constructor(
    private val houseRepository: HouseRepository
):ViewModel(){

      val houses = mutableStateListOf<House>()

      fun getHouses(){
          viewModelScope.launch {
              val response = houseRepository.getHouses()
              houses.addAll(response)
          }
      }
    private val _houseUiState = MutableStateFlow(HouseListingUiState())
    val houseUiState: StateFlow<HouseListingUiState> = _houseUiState

    fun getHouseList() {
        viewModelScope.launch {
            try {
                _houseUiState.value = _houseUiState.value.copy(loading = true)

                val response = houseRepository.getHouses() // API call
                _houseUiState.value = HouseListingUiState(
                    houses = response,
                    loading = false
                )
            } catch (e: Exception) {
                _houseUiState.value = HouseListingUiState(
                    houses = emptyList(),
                    loading = false,
                    error = e.message
                )
            }
        }
    }

    fun deleteHouse(id: String?) {
        viewModelScope.launch {
            try {
                val success = houseRepository.deleteHouse(id)
                if (success) {
                    // Update local state by removing deleted house
                    val updatedList = _houseUiState.value.houses?.filterNot { it.id == id }
                    _houseUiState.value = _houseUiState.value.copy(houses = updatedList)
                }
            } catch (e: Exception) {
                _houseUiState.value = _houseUiState.value.copy(error = e.message)
            }
        }
    }

    private val _saveUiState = MutableStateFlow(SaveHouseUiState())
    val saveUiState: StateFlow<SaveHouseUiState> = _saveUiState

    fun saveHouse(isEdit: Boolean,request: HouseRequest) {
        viewModelScope.launch {
            try {
                _saveUiState.value = SaveHouseUiState(loading = true)

                val response =  if (isEdit && request.id != null) {
                    houseRepository.addHouse(request)
                } else {
                    houseRepository.editHouse(request.id, request)
                }

                _saveUiState.value = SaveHouseUiState(
                    house = response,
                    loading = false,
                    success = true
                )
            } catch (e: Exception) {
                _saveUiState.value = SaveHouseUiState(
                    loading = false,
                    error = e.message
                )
            }
        }
    }

    data class SaveHouseUiState(
        val house: House? = null,
        val loading: Boolean = false,
        val error: String? = null,
        val success: Boolean = false
    )

    data class HouseListingUiState(
        val houses: List<House>? = emptyList(),
        val loading: Boolean = true,
        val error: String? = null
    )
}