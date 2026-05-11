package com.example.mymoneyandroid.network

import com.example.mymoneyandroid.model.LoginRequest
import com.example.mymoneyandroid.model.RegistroRequest
import com.example.mymoneyandroid.model.UsuarioResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UsuarioApi {
    @POST("/usuarios/")
    suspend fun cadastrarUsuario(@Body requisicao: RegistroRequest): Response<UsuarioResponse>

    @POST("usuarios/login")
    suspend fun fazerLogin(@Body login: LoginRequest): Response<UsuarioResponse>

}