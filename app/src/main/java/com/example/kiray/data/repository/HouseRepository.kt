package com.example.kiray.data.repository

import com.example.kiray.data.api.ApiService
import com.example.kiray.data.model.request.house.HouseRequest
import com.example.kiray.model.House
import retrofit2.http.POST
import javax.inject.Inject


class HouseRepository@Inject constructor(
    private val api: ApiService
) {

    suspend fun getHouses(): List<House> {
        return api.getHouses()
    }

    suspend fun deleteHouse(id: String?): Boolean {
        return api.deleteHouse(id)
    }

    suspend fun addHouse(request: HouseRequest): House {
        return api.addHouse(request)
    }

    suspend fun editHouse(id: String?, request: HouseRequest): House {
        return api.editHouse(id, request)
    }
}
