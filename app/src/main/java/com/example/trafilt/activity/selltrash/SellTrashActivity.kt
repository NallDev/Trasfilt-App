package com.example.trafilt.activity.selltrash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.trafilt.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.trafilt.databinding.ActivitySellTrashBinding
import com.example.trafilt.utility.lightStatusBar
import com.example.trafilt.model.LocationData

class SellTrashActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivitySellTrashBinding
    private val list = ArrayList<LocationData>()
    private var locationMap: ArrayList<LatLng>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySellTrashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lightStatusBar(window)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        list.addAll(listLocations)
        locationMap = ArrayList()

        mMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isCompassEnabled = true
            isMapToolbarEnabled = true
        }

        for (i in list.indices){
            locationMap!!.add(LatLng(list[i].lat.toDouble(), list[i].long.toDouble()))

            mMap.addMarker(
                MarkerOptions()
                    .position(locationMap!![i])
                    .title(list[i].name)
                    .snippet(list[i].address)
            )
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locationMap!![i], 15f))
        }
    }

    private val listLocations: ArrayList<LocationData>
        get() {
            val lat = resources.getStringArray(R.array.lat)
            val long = resources.getStringArray(R.array.lon)
            val name = resources.getStringArray(R.array.data_name)
            val address = resources.getStringArray(R.array.data_city)
            val listLocation = ArrayList<LocationData>()
            for (i in lat.indices){
                val location = LocationData(lat[i], long[i], name[i], address[i])
                listLocation.add(location)
            }
            return listLocation
        }

}