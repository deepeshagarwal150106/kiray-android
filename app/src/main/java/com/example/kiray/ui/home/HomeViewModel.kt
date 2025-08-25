package com.example.kiray.ui.home

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kiray.data.repository.MarkerRepository
import com.example.kiray.model.Marker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel@Inject constructor(
      private val markerRepository: MarkerRepository
): ViewModel(){
      val markers = mutableStateListOf<Marker>()
      private fun fetchMarkers(){
            viewModelScope.launch {
                  try {
                        val response = markerRepository.getMarkers()
                        markers.clear()
                        markers.addAll(response)
                        Log.d("markers",markers.toString())
                  }
                  catch (e:Exception){
                        e.printStackTrace()
                  }
            }
      }
      init {
            Log.d("markers",markers.toString())
            fetchMarkers()
            Log.d("markers",markers.toString())
      }
}