package com.example.trafilt.activity

import android.content.Intent
import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.trafilt.R
import com.example.trafilt.activity.login.LoginActivity
import com.example.trafilt.activity.pickup.PickUpActivity
import com.example.trafilt.activity.scan.ResultScanActivity
import com.example.trafilt.activity.selltrash.SellTrashActivity
import com.example.trafilt.activity.selltrash.SellTrashViewModel
import com.example.trafilt.data.PrefManager
import com.example.trafilt.databinding.ActivityMainBinding
import com.example.trafilt.utility.createCustomTempFile
import com.example.trafilt.utility.lightStatusBar
import java.io.File

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var prefManager: PrefManager

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lightStatusBar(window)
        init()

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        checkLogin()
        binding.pickUp.setOnClickListener(this)
        binding.sellTrash.setOnClickListener(this)
        binding.scan.setOnClickListener(this)
        binding.cvLogout.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.scan -> {
                startCamera()
            }

            R.id.pick_up -> {
                val intent = Intent(this, PickUpActivity::class.java)
                startActivity(intent)
            }
            R.id.sell_trash -> {
                val intent = Intent(this, SellTrashActivity::class.java)
                startActivity(intent)
            }
            R.id.cv_logout -> {
                let {
                    prefManager.removeData()
                    val intent = Intent(it, LoginActivity::class.java)
                    it.startActivity(intent)
                    it.finish()
                }
            }
        }
    }

    private fun startCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@MainActivity,
                "com.example.trafilt.activity",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private lateinit var currentPhotoPath: String
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            val intent = Intent(this, ResultScanActivity::class.java)
            intent.putExtra("Image", myFile.path)
            startActivity(intent)
        }
    }

    private fun checkLogin() {
        if (prefManager.isLogin() == false) {
            let {
                val intent = Intent(it, LoginActivity::class.java)
                it.startActivity(intent)
                it.finish()
            }
        }
    }

    private fun init() {
        prefManager = PrefManager(this)
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}