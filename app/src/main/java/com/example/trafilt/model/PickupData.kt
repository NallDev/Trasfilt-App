package com.example.trafilt.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PickupData(
    var name: String,
    var phone: String,
    var city: String
): Parcelable