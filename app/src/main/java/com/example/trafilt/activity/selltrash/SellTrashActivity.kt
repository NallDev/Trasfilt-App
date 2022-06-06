package com.example.trafilt.activity.selltrash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.trafilt.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.trafilt.databinding.ActivitySellTrashBinding
import com.example.trafilt.utility.lightStatusBar

class SellTrashActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivitySellTrashBinding
    private var locationMap: ArrayList<LatLng>? = null
    private val sellTrashViewModel : SellTrashViewModel by viewModels()

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

        locationMap = ArrayList()

        mMap.uiSettings.apply {
            isZoomControlsEnabled = true
            isCompassEnabled = true
            isMapToolbarEnabled = true
        }

        sellTrashViewModel.getAllSellTrash()
        sellTrashViewModel.getLocation().observe(this){
            if (it != null){
                for (i in it.indices){
                    locationMap!!.add(LatLng(it[i].latSellTrash.toDouble(), it[i].longSellTrash.toDouble()))

                    Log.e("data", locationMap!![i].toString())

                    mMap.addMarker(
                        MarkerOptions()
                            .position(locationMap!![i])
                            .title(it[i].nameSellTrash)
                            .snippet(it[i].addressSellTrash)
                    )
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(locationMap!![i], 15f))
                }
            }
        }
    }
}