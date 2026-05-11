package com.example.mymoneyandroid.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Tem que ser "object" e não "class"
object RetrofitClient {
    private const val urlBase = "http://10.0.2.2:5194/"

    val apiCategoria: CategoriaApi by lazy {
        Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CategoriaApi::class.java)
    }

    val apiUsuario: UsuarioApi by lazy {
        Retrofit.Builder()
            .baseUrl(urlBase)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UsuarioApi::class.java)
    }
}