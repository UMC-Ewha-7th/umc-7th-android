package com.example.flo_clone.utils

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://3.35.121.185"

fun getRetrofit(): Retrofit {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

suspend fun <T> handleResponseWithData(
    response: Response<BaseResponseWithData<T>>,
    onSuccess: suspend (T) -> Unit,
    onFailure: suspend (BaseResponse) -> Unit
) {
    if (response.isSuccessful) {
        val body = response.body()

        if (body != null) {
            onSuccess(body.result)
        } else {
            onFailure(
                BaseResponse(
                    response.isSuccessful,
                    response.code().toString(),
                    response.message()
                )
            )
        }
    } else {
        val errorBody = response.errorBody()?.string()

        if (!errorBody.isNullOrEmpty()) {
            val gson = Gson()
            val errorResponse = gson.fromJson(errorBody, BaseResponse::class.java)
            onFailure(errorResponse)
        } else {
            onFailure(BaseResponse(false, "500", "INTERNAL SERVER ERROR"))
        }
    }
}