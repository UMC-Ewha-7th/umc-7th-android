package com.example.flo_clone

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/login")
    fun signUp(@Body user: User): Call<AuthResponse>

    @POST("/join")
    fun login(@Body user: User): Call<AuthResponse>
}
