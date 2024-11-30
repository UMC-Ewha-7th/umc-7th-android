package com.example.flo_clone

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName(value = "inSuccess") val inSuccess:Boolean,
    @SerializedName(value = "code") val code:Int,
    @SerializedName(value = "message") val message:String,
    @SerializedName(value = "result") val result: Result?
)

data class Result(
    @SerializedName(value = "memberId") var memberId : Int,
    @SerializedName(value = "accessToken") var accessToken : String
)