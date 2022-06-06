package com.example.trafilt.activity.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.trafilt.LoginViewModel
import com.example.trafilt.R
import com.example.trafilt.ViewModelFactory
import com.example.trafilt.activity.MainActivity
import com.example.trafilt.activity.UserPreference
import com.example.trafilt.databinding.ActivityLoginBinding

private val Context.dataStore:DataStore<Preferences> by preferencesDataStore("Setting")
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        editTextList()
        setLoginViewModel()
        buttonEnable()
        buttonLogin()

    }
    private fun editTextList(){
        binding.editText2.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {
                buttonEnable()
            }

            override fun afterTextChanged(p0: Editable?) {

            }

        })
        binding.editText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {
                buttonEnable()
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
        binding.signUp.setOnClickListener {
            startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
            finish()
        }
    }
    private fun buttonEnable(){
        val password = binding.editText.text
        val email = binding.editText2.text

        binding.signIn.isEnabled = password !=null && email !=null &&
                binding.editText.text.toString().length >= 6 &&
                Helper.isEmailValid(binding.editText2.text.toString())
    }
    private fun setLoginViewModel(){
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[LoginViewModel::class.java]
    }
    private fun buttonLogin(){
        binding.signIn.setOnClickListener {
            val email = binding.editText2.text.toString()
            val password = binding.editText.text.toString()
            viewModel.login(email,password,object :Helper.ApiCallbackString{
                override fun onResponse(success: Boolean, message: String) {
                    alertDialog(success,message)
                }
            })
        }
    }

    private fun alertDialog(param: Boolean, message: String){
        if (param){
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.information))
                setMessage(getString(R.string.log_in_success))
                setPositiveButton(getString(R.string.continue_)) {_, _ ->
                    val intent = Intent(context, MainActivity::class.java)
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
                setMessage(getString(R.string.log_in_failed) +", $message")
                setPositiveButton(getString(R.string.continue_)){_, _ ->
                }
                create()
                show()
            }
        }
    }
}
