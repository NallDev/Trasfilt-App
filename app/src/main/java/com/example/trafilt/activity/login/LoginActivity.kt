package com.example.trafilt.activity.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.trafilt.R
import com.example.trafilt.activity.MainActivity
import com.example.trafilt.activity.register.RegisterActivity
import com.example.trafilt.activity.scan.ResultScanActivity
import com.example.trafilt.data.PrefManager
import com.example.trafilt.databinding.ActivityLoginBinding
import com.example.trafilt.utility.createCustomTempFile
import com.example.trafilt.utility.lightStatusBar
import com.google.android.material.snackbar.Snackbar
import java.io.File

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var prefManager: PrefManager
    private val loginViewModel by viewModels<LoginViewModel>()

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
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lightStatusBar(window)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
        showMessage()
        init()
        setButton()
        inputListener()
        loginCheck()
        loginWithDummy()

        binding.signUp.setOnClickListener(this)
        binding.scan.setOnClickListener(this)
    }

    private fun loginWithDummy() {
        binding.signIn.setOnClickListener{
            showProgressBar(true)
            Handler(Looper.getMainLooper()).postDelayed(
                {
                    if(dummyEmailPass(binding.email.text.toString(), binding.password.text.toString())){
                        Intent(this, MainActivity::class.java).also {
                            prefManager.setLogin(true)
                            startActivity(it)
                            finish()
                        }
                    }
                    showProgressBar(false)
                },
                3000
            )
        }
    }

    private fun loginCheck() {
        binding.signIn.setOnClickListener {
            loginViewModel.response.observe(this) {
                it[0].toString()
                Log.e("data", it[0].toString())
                prefManager.setLogin(true)
            }
            loginViewModel.postLogin(
                binding.email.text.toString(),
                binding.password.text.toString()
            )
        }

        loginViewModel.isLoading.observe(this) {
            showProgressBar(it)
        }

        loginViewModel.isSuccess.observe(this) { isSuccess ->
            if (isSuccess) {
                Snackbar.make(binding.root, R.string.login_success, Snackbar.LENGTH_SHORT).show()
                Handler(Looper.getMainLooper()).postDelayed({
                    let {
                        val intent = Intent(this, MainActivity::class.java)
                        it.startActivity(intent)
                        it.finish()
                    }
                }, 1000)
            } else Snackbar.make(binding.root, R.string.login_fail, Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun inputListener() {
        binding.email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setButton()
            }

            override fun afterTextChanged(s: Editable) {

            }
        })
    }

    private fun setButton() {
        binding.apply {
            signIn.isEnabled =
                Patterns.EMAIL_ADDRESS.matcher(email.text.toString())
                    .matches()
        }
    }

    private fun showMessage() {
        val message = intent.getStringExtra("Message")
        if (message != null) {
            Snackbar.make(binding.root, message.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.sign_up -> {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }

            R.id.scan -> {
                startCamera()
            }
        }
    }

    private fun startCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this,
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

    private fun showProgressBar(isLoading: Boolean) {
        binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun init() {
        prefManager = PrefManager(this)
    }

    override fun onStart() {
        super.onStart()
        if (prefManager.isLogin()!!) {
            let {
                val intent = Intent(it, MainActivity::class.java)
                it.startActivity(intent)
                it.finish()
            }
        }
    }

    private fun dummyEmailPass(email :String, pass: String) : Boolean{
        return if (email == "afrinaldi@gmail.com" && pass == "12345678"){
            Toast.makeText(this, R.string.login_success, Toast.LENGTH_SHORT).show()
            true
        } else {

            Toast.makeText(this, R.string.login_fail, Toast.LENGTH_SHORT).show()
            false
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}