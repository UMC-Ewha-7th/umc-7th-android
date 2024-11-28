package com.example.flo_clone.user.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo_clone.databinding.ActivityLoginBinding
import com.example.flo_clone.user.data.LoginRequestDTO
import com.example.flo_clone.user.data.LoginSuccessDTO
import com.example.flo_clone.user.data.UserRepository
import com.example.flo_clone.user.service.AuthService
import com.example.flo_clone.user.service.LoginView
import com.example.flo_clone.utils.BaseResponse

class LoginActivity : AppCompatActivity(), LoginView {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        userRepository = UserRepository(this)

        if (checkLogin()) {
            Toast.makeText(this, "로그인 완료.", Toast.LENGTH_SHORT).show()
            finish()
        }

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

    private fun checkLogin(): Boolean {
        val spf = getSharedPreferences("user", MODE_PRIVATE)
        val idx = spf.getInt("userIdx", 0)

        return idx != 0
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

    private fun login() {
        val email = "${binding.loginIdEt.text}@${binding.loginEmailDomainTv.text}"
        val password = binding.loginPasswordEt.text.toString()
        val user = LoginRequestDTO(email, password)

        val authService = AuthService()
        authService.setLoginView(this)
        authService.login(user)
    }

    override fun onLoginSuccess(response: LoginSuccessDTO) {
        val spf = getSharedPreferences("user", MODE_PRIVATE)
        val editor = spf.edit()
        editor.putString("accessToken", response.accessToken)
        editor.putInt("userIdx", response.memberId)
        editor.apply()

        val token = spf.getString("accessToken", "")
        Log.d("LoginActivity", "login user token: $token")
        finish()
    }

    override fun onLoginFailure(response: BaseResponse) {
        Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
    }
}