package com.example.kiray.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class User(
    val id:String?=null,
    val name:String?=null,
    val email:String?=null,
    val phone:String?=null,
    val password:String?=null,
    val address:String?=null,
    val houses:List<House>?=null
) : Parcelable
