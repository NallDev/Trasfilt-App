package com.example.trafilt.activity.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import androidx.activity.viewModels
import com.example.trafilt.R
import com.example.trafilt.activity.login.LoginActivity
import com.example.trafilt.databinding.ActivityRegisterBinding
import com.google.android.material.snackbar.Snackbar

class RegisterActivity : AppCompatActivity() {
    private var _binding : ActivityRegisterBinding? = null
    private val binding get() = _binding!!
    private val registerViewModel : RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButton()
        inputListener()
        registerCheck()
        binding.signIn.setOnClickListener{
            onBackPressed()
            finish()
        }
    }

    private fun registerCheck() {
        binding.apply {
            btnSignup.setOnClickListener {
                registerViewModel.postRegister(
                    email.text.toString(),
                    name.text.toString(),
                    password.text.toString()
                )
            }
        }

        registerViewModel.isSuccess.observe(this) {
            if (it){
                binding.apply {
                    name.setText("")
                    email.setText("")
                    password.setText("")
                }
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("Message", this.getString(R.string.register_success))
                startActivity(intent)
                finish()
            } else Snackbar.make(binding.root, R.string.already_exists, Snackbar.LENGTH_SHORT).show()
        }

        registerViewModel.isLoading.observe(this) {
            showProgressBar(it)
        }
    }

    private fun inputListener() {
        binding.apply {
            name.addTextChangedListener(object : TextWatcher {
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

            email.addTextChangedListener(object : TextWatcher {
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

            password.addTextChangedListener(object : TextWatcher {
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
    }

    private fun setButton() {
        binding.apply {
            btnSignup.isEnabled =
                name.text!!.isNotEmpty() && password.text!!.length >= 8 && Patterns.EMAIL_ADDRESS.matcher(
                    email.text.toString()
                ).matches()
        }
    }

    private fun showProgressBar(isLoading: Boolean) {
        binding.loading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun navigateUpTo(upIntent: Intent?): Boolean {
        onBackPressed()
        finish()
        return super.navigateUpTo(upIntent)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
