package com.example.flo_clone.user.service

import com.example.flo_clone.user.data.JoinRequestDTO
import com.example.flo_clone.user.data.JoinSuccessDTO
import com.example.flo_clone.user.data.LoginRequestDTO
import com.example.flo_clone.user.data.LoginSuccessDTO
import com.example.flo_clone.utils.BaseResponseWithData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/join")
    suspend fun join(@Body request: JoinRequestDTO): Response<BaseResponseWithData<JoinSuccessDTO>>

    @POST("/login")
    suspend fun login(@Body request: LoginRequestDTO): Response<BaseResponseWithData<LoginSuccessDTO>>
}