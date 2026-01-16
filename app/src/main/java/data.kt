package com.example.myapplication

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class SozModel(
    val kategori: String,
    val icerik: String
)

interface SozApi {
    @GET("/882a4b7742a40e6f5db2")
    fun sozleriGetir(): Call<List<SozModel>>
}

object RetrofitClient {
    private const val BASE_URL = "https://api.npoint.io/"
    val instance: SozApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(SozApi::class.java)
    }
}