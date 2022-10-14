package com.example.trafilt.activity.pickup

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.example.trafilt.R
import com.example.trafilt.adapter.ListPickUpAdapter
import com.example.trafilt.api.PickUpItem
import com.example.trafilt.databinding.ActivityPickUpBinding
import com.example.trafilt.model.PickUpModel
import com.example.trafilt.utility.lightStatusBar

class PickUpActivity : AppCompatActivity() {
    private var _binding: ActivityPickUpBinding? = null
    private val binding get() = _binding!!
    private val pickUpViewModel : PickUpViewModel by viewModels()
    private val listPickUpAdapter by lazy { ListPickUpAdapter() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPickUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        lightStatusBar(window)

        binding.rvPickUp.setHasFixedSize(true)

//        progressBar()
        showProgressBar(true)
        val listPickUp = ArrayList<PickUpModel>()

        Handler(Looper.getMainLooper()).postDelayed(
            {
                listPickUp.addAll(dataPickups)
                listPickUpAdapter.setData(listPickUp)
                showProgressBar(false)
            },
            3000
        )

        showData()

//        pickUpViewModel.showPickUp()
//        pickUpViewModel.pickUps.observe(this){
//            for (i in it.indices){
//                val pickUp = PickUpItem(it[i].idPickUp, it[i].namePickUp, it[i].cityPickUp, it[i].phonePickUp)
//                listPickUp.add(pickUp)
//            }
//            Log.e("data", listPickUp.toString())
//            listPickUpAdapter.setData(listPickUp)
//            showData()
//        }
    }

    private fun showData() {
        binding.rvPickUp.adapter = listPickUpAdapter

        listPickUpAdapter.setOnItemClickCallback(object : ListPickUpAdapter.OnItemClickCallback{
            override fun onItemClicked(data: PickUpModel) {
                Log.e("data", data.toString())
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:" + data.phonePickUp)
                startActivity(intent)
            }
        })
    }

    private val dataPickups: ArrayList<PickUpModel>
        get() {
            val name = resources.getStringArray(R.array.data_name)
            val phone = resources.getStringArray(R.array.data_phone)
            val city = resources.getStringArray(R.array.data_city)
            val dataPickup = ArrayList<PickUpModel>()
            for (i in title.indices) {
                val items = PickUpModel(i, name[i], city[i], phone[i])
                dataPickup.add(items)
            }
            return dataPickup
        }

    private fun showProgressBar(isLoading: Boolean) {
        binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun progressBar() {
        pickUpViewModel.isLoading.observe(this) {
            showProgressBar(it)
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}