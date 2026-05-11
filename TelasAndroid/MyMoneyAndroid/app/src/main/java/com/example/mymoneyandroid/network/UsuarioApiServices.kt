package com.example.mymoneyandroid.network

import com.example.mymoneyandroid.model.LoginRequest
import com.example.mymoneyandroid.model.RegistroRequest
import com.example.mymoneyandroid.model.UsuarioResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UsuarioApi {

    @POST("usuarios") // Esse é o caminho da sua rota de cadastro
    suspend fun cadastrarUsuario(@Body request: RegistroRequest): Response<UsuarioResponse>

    @POST("usuarios/login") // Esse é o caminho da sua rota de login
    suspend fun fazerLogin(@Body login: LoginRequest): Response<UsuarioResponse>

}

//@GET("usuarios/")
//suspend fun perfilUsuario() : List<>
