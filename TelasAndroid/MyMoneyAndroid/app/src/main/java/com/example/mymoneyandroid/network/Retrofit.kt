package com.example.mymoneyandroid.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Tem que ser "object" e não "class"
object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:5194/"

    val api: CategoriaApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CategoriaApi::class.java)
    }
}