package com.example.qrscannerexam.rest

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

interface RestInterface {
    @POST("/test/android.php")
    fun saveData(
        @Query("data") data: String
    ): Call<RestResult>

}