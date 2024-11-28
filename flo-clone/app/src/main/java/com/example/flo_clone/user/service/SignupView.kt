package com.example.flo_clone.user.service

import com.example.flo_clone.user.data.JoinSuccessDTO
import com.example.flo_clone.utils.BaseResponse

interface SignupView {
    fun onSignupSuccess(response: JoinSuccessDTO)
    fun onSignupFailure(response: BaseResponse)
}