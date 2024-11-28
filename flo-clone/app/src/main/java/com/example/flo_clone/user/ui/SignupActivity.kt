package com.example.flo_clone.user.ui

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo_clone.R
import com.example.flo_clone.databinding.ActivitySignupBinding
import com.example.flo_clone.user.data.JoinRequestDTO
import com.example.flo_clone.user.data.JoinSuccessDTO
import com.example.flo_clone.user.data.UserRepository
import com.example.flo_clone.user.service.AuthService
import com.example.flo_clone.user.service.SignupView
import com.example.flo_clone.utils.BaseResponse

class SignupActivity : AppCompatActivity(), SignupView {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var userRepository: UserRepository
    private val emailDomains = arrayOf("naver.com", "gmail.com", "hanmail.net", "nate.com")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        userRepository = UserRepository(this)

        setContentView(binding.root)

        val adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, emailDomains)
        binding.signupEmailDomainTv.setAdapter(adapter)

        binding.signupFinishBtn.setOnClickListener {
            signup()
        }
    }

    private fun validateName(name: String): Boolean {
        if (name.isEmpty()) {
            setErrorMsg(resources.getString(R.string.signup_validate_name_empty))
            return false
        }

        binding.signupValidateTv.visibility = View.GONE
        return true
    }

    private fun validateEmail(email: String, emailDomain: String): Boolean {
        if (email.isEmpty()) {
            setErrorMsg(resources.getString(R.string.signup_validate_id_empty))
            return false
        }

        if (emailDomain.isEmpty()) {
            setErrorMsg(resources.getString(R.string.signup_validate_email_empty))
            return false
        }

        if (!emailDomain.contains('.')) {
            setErrorMsg(resources.getString(R.string.signup_validate_email))
            return false
        }

        binding.signupValidateTv.visibility = View.GONE
        return true
    }

    private fun validatePassword(password: String, passwordCheck: String): Boolean {
        if (password.isEmpty()) {
            setErrorMsg(resources.getString(R.string.signup_validate_pw_empty))
            return false
        }

        if (passwordCheck.isEmpty()) {
            setErrorMsg(resources.getString(R.string.signup_validate_pw_check_empty))
            return false
        }

        if (password != passwordCheck) {
            setErrorMsg(resources.getString(R.string.signup_validate_pw))
            return false
        }

        binding.signupValidateTv.visibility = View.GONE
        return true
    }

    private fun signup() {
        val name = binding.signupNameEt.text.toString()

        val email = binding.signupIdEt.text.toString()
        val emailDomain = binding.signupEmailDomainTv.text.toString()

        val password = binding.signupPasswordEt.text.toString()
        val passwordCheck = binding.signupPasswordCheckEt.text.toString()

        if (!validateName(name)) return
        if (!validateEmail(email, emailDomain)) return
        if (!validatePassword(password, passwordCheck)) return

        val emailFull = "$email@$emailDomain"
        val user = JoinRequestDTO(name, emailFull, password)

        val authService = AuthService()
        authService.setSignupView(this)
        authService.signup(user)
    }

    private fun setErrorMsg(message: String) {
        binding.signupValidateTv.text = message
        binding.signupValidateTv.visibility = View.VISIBLE
    }

    override fun onSignupSuccess(response: JoinSuccessDTO) {
        Toast.makeText(this, "회원가입 성공!", Toast.LENGTH_SHORT).show()
        finish()
    }

    override fun onSignupFailure(response: BaseResponse) {
        setErrorMsg(response.message)
    }
}