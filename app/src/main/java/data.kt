package com.example.myapplication // Kendi paket adın olduğundan emin ol

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// 1. Veri Kalıbı
data class SozModel(
    val kategori: String,
    val icerik: String
)

// 2. İnternet Arayüzü (Interface)
interface SozApi {
    @GET("/3f5671eb3cf9c55ea3ef") // Bu bir test verisidir
    fun sozleriGetir(): Call<List<SozModel>>
}

// 3. Retrofit Bağlantı Merkezi (Object)
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