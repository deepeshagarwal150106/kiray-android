package com.example.kiray.model

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.parcelize.Parcelize


@Parcelize
data class House(
    val id:String?=null,
    val name:String?=null,
    val markerId:String?=null,
    val description:String?=null,
    val ownerId:String?=null,
    val address:String?=null,
    val price:String?=null,
    val type:String?=null,
    val phone:String?=null,
    val payInfo:PayDetail?=null,
    val owner:User?=null,
    val maker:Marker?=null,
    val city:String?=null,
    val rooms:String?=null,
    val amenities: List<String>?,
    val latLng: LatLng?
) : Parcelable
