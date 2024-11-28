package com.example.flo_clone.user.service

import com.example.flo_clone.user.data.LoginSuccessDTO
import com.example.flo_clone.utils.BaseResponse

interface LoginView {
    fun onLoginSuccess(response: LoginSuccessDTO)
    fun onLoginFailure(response: BaseResponse)
}