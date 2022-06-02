package com.example.trafilt.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.trafilt.R
import com.example.trafilt.activity.pickup.PickUpActivity
import com.example.trafilt.databinding.ActivityMainBinding
import com.example.trafilt.lightStatusBar

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lightStatusBar(window)

        binding.pickUp.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.pick_up -> {
                val intent = Intent(this, PickUpActivity::class.java)
                startActivity(intent)
            }
        }
    }
}