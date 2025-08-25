package com.example.kiray.data.repository

import com.example.kiray.data.api.ApiService
import com.example.kiray.model.Marker
import javax.inject.Inject

class MarkerRepository@Inject constructor(
    private val api: ApiService
) {

    suspend fun getMarkers(): List<Marker> {
        return api.getMarkers()
    }

}
