package com.example.qrscannerexam.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RestRetrofit {
    // 위에서 만든 RetrofitService를 연결해줍니다.
    fun getService(): RestInterface = retrofit.create(RestInterface::class.java)

    private val retrofit =
        Retrofit.Builder()
            .baseUrl("http://dev592.cafe24.com") // 도메인 주소
            .addConverterFactory(GsonConverterFactory.create()) // GSON을 사요아기 위해 ConverterFactory에 GSON 지정
            .build()
}