package com.example.trafilt.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.ThemedSpinnerAdapter
import androidx.constraintlayout.core.widgets.Helper
import com.example.trafilt.R
import com.example.trafilt.RegisterViewModel
import com.example.trafilt.activity.login.LoginActivity
import com.example.trafilt.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel by viewModels<RegisterViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        buttonEnable()
        buttonListener()
        textListener()

    }
    private fun buttonEnable(){
        binding.signIn.isEnabled =
            binding.editText.text.toString().isNotEmpty() &&
                    binding.editText3.text.toString().isNotEmpty()&&
                    binding.editText3.text.toString().length >= 6 &&
                    Helper.isEmailValid(binding.editText.text.toString())
    }
    private fun setAlertDialog (param : Boolean, message : String) {
        if (param) {
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.information))
                setMessage(getString(R.string.register_success))
                setPositiveButton(getString(R.string.continue_)) { _, _ ->
                    val intent = Intent(context, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
                create()
                show()
            }
        } else {
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.information))
                setMessage(getString(R.string.register_failed) +", $message")
                setPositiveButton(getString(R.string.continue_)){_, _ ->
                }
                create()
                show()
            }
        }
    }

    private fun buttonListener(){
        binding.scan.setOnClickListener {
            val user = binding.editText2.text.toString()
            val email = binding.editText.text.toString()
            val password = binding.editText3.text.toString()

            registerViewModel.register(
                user,
                email,
                password,
                object : ThemedSpinnerAdapter.Helper.ApiCallbackString{
                    override fun onResponse(success: Boolean, message: String) {
                        setAlertDialog(success, message)
                    }
                }
            )
        }
    }
    private fun textListener(){
        binding.editText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                buttonEnable()
            }

            override fun afterTextChanged(s: Editable) {

            }

        })
        binding.editText3.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                buttonEnable()
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
        binding.signIn.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
        }
    }
}
