package com.example.kiray.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Marker (
    val id:String?=null,
    val latitude: Double,
    val longitude: Double,
    val house: House? = null,
) : Parcelable