package com.example.kiray.data.model.request.house

import com.example.kiray.model.User
import com.google.android.gms.maps.model.LatLng

data class HouseRequest(
    val id: String?=null,
    val name: String?,
    val price: String?,
    val rooms: String?,
    val amenities: List<String>?,
    val address: String?,
    val city: String?,
    val description: String?,
    val latLng: LatLng?,
    val ownerId:String?
)
