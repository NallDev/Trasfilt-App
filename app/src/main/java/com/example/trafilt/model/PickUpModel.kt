package com.example.trafilt.model

import android.os.Parcelable 
import kotlinx.parcelize.Parcelize

@Parcelize
data class PickUpModel(
    var idPickUp: Int,

    var namePickUp: String,

    var cityPickUp: String,

    var phonePickUp: String
): Parcelable