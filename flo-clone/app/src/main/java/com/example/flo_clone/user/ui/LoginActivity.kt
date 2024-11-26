package com.example.flo_clone.user.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo_clone.databinding.ActivityLoginBinding
import com.example.flo_clone.user.data.User
import com.example.flo_clone.user.data.UserRepository

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        userRepository = UserRepository(this)

        setContentView(binding.root)

        binding.loginCloseIb.setOnClickListener {
            finish()
        }

        binding.loginIdEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.loginFinishBtn.isEnabled = checkAllFields() && validateInput()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.loginEmailDomainTv.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.loginFinishBtn.isEnabled = checkAllFields() && validateInput()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.loginPasswordEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.loginFinishBtn.isEnabled = checkAllFields() && validateInput()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.loginFinishBtn.setOnClickListener {
            login()
        }

        binding.loginSignupTv.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun checkAllFields(): Boolean {
        return binding.loginIdEt.text.isNotEmpty() && binding.loginEmailDomainTv.text.isNotEmpty() && binding.loginPasswordEt.text.isNotEmpty()
    }

    private fun validateInput(): Boolean {
        if (binding.loginEmailDomainTv.text.toString().contains('.')) {
            return true
        }
        return false
    }

    private fun validatePassword(user: User, password: String): Boolean {
        if (user.password != password) {
            return false
        }

        return true
    }

    private fun login() {
        val email = "${binding.loginIdEt.text}@${binding.loginEmailDomainTv.text}"
        val password = binding.loginPasswordEt.text.toString()
        val user = userRepository.getUserByEmail(email)

        if (user != null && validatePassword(user, password)) {
            val spf = getSharedPreferences("user", MODE_PRIVATE)
            val editor = spf.edit()
            editor.putInt("userIdx", user.id)
            editor.apply()

            val idx = spf.getInt("userIdx", 0)
            Log.d("LoginActivity", "login user id: $idx")
            finish()
        } else {
            Toast.makeText(this, "아이디 또는 패스워드가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}