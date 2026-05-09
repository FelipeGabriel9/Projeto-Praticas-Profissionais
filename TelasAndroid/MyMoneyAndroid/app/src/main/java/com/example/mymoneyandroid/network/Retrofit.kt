package com.example.mymoneyandroid.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // Se estiver usando emulador, o localhost do seu PC é 10.0.2.2
    private const val BASE_URL = "http://10.0.2.2:5000/"

    val instancia: UsuarioApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Faz o JSON virar Classe
            .build()
            .create(UsuarioApiService::class.java)
    }
}