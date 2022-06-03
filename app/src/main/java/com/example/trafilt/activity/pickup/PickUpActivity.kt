package com.example.trafilt.activity.pickup

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.trafilt.R
import com.example.trafilt.adapter.ListPickUpAdapter
import com.example.trafilt.databinding.ActivityPickUpBinding
import com.example.trafilt.utility.lightStatusBar
import com.example.trafilt.model.PickupData

class PickUpActivity : AppCompatActivity() {
    private var _binding: ActivityPickUpBinding? = null
    private val binding get() = _binding!!

    private val list = ArrayList<PickupData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPickUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        lightStatusBar(window)

        binding.rvPickUp.setHasFixedSize(true)

        list.addAll(listPickUps)
        showData()
    }

    private fun showData() {
        val listPickUpAdapter = ListPickUpAdapter(list)
        binding.rvPickUp.adapter = listPickUpAdapter

        listPickUpAdapter.setOnItemClickCallback(object : ListPickUpAdapter.OnItemClickCallback{
            override fun onItemClicked(data: PickupData) {
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:" + data.phone)
                startActivity(intent)
            }
        })
    }

    private val listPickUps: ArrayList<PickupData>
        get() {
            val dataName = resources.getStringArray(R.array.data_name)
            val dataPhone = resources.getStringArray(R.array.data_phone)
            val dataCity = resources.getStringArray(R.array.data_city)
            val listPickUp = ArrayList<PickupData>()
            for (i in dataName.indices){
                val pickUp = PickupData(dataName[i], dataPhone[i], dataCity[i])
                listPickUp.add(pickUp)
            }
            return listPickUp
        }


}