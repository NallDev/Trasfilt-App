package com.example.trafilt.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LocationData(
    var lat: String,
    var long: String,
    var name: String,
    var address: String
): Parcelable