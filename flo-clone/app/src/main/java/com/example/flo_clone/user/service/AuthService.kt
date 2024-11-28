package com.example.flo_clone.user.service

import android.util.Log
import com.example.flo_clone.user.data.JoinRequestDTO
import com.example.flo_clone.user.data.LoginRequestDTO
import com.example.flo_clone.utils.BaseResponse
import com.example.flo_clone.utils.getRetrofit
import com.example.flo_clone.utils.handleResponseWithData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AuthService {
    private lateinit var signupView: SignupView
    private lateinit var loginView: LoginView
    private val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

    fun setSignupView(signupView: SignupView) {
        this.signupView = signupView
    }

    fun setLoginView(loginView: LoginView) {
        this.loginView = loginView
    }

    fun signup(user: JoinRequestDTO) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = authService.join(user)
                handleResponseWithData(
                    response,
                    onSuccess = { result ->
                        withContext(Dispatchers.Main) {
                            signupView.onSignupSuccess(result)
                        }
                    },
                    onFailure = { errorResponse ->
                        withContext(Dispatchers.Main) {
                            signupView.onSignupFailure(errorResponse)
                        }
                    }
                )
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    signupView.onSignupFailure(
                        BaseResponse(
                            false,
                            "500",
                            "회원가입 중 오류가 발생했습니다. 다시 시도해주세요."
                        )
                    )
                    Log.e("Signup", "Error: ${e.message}")
                }
            }
        }
    }

    fun login(user: LoginRequestDTO) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = authService.login(user)
                handleResponseWithData(
                    response,
                    onSuccess = { responseBody ->
                        withContext(Dispatchers.Main) {
                            loginView.onLoginSuccess(responseBody)
                        }
                    },
                    onFailure = { errorResponse ->
                        withContext(Dispatchers.Main) {
                            loginView.onLoginFailure(errorResponse)
                        }
                    }
                )
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    loginView.onLoginFailure(
                        BaseResponse(
                            false,
                            "500",
                            "로그인 중 오류가 발생했습니다. 다시 시도해주세요."
                        )
                    )
                    Log.e("Login", "Error: ${e.message}")
                }
            }
        }
    }
}