package com.example.kiray.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class PayDetail (
    val id:String?=null,
    val houseId:String,
    val bankName:String,
    val accountNo:String,
    val ifscCode:String,
    val house:House?=null
) : Parcelable