package com.example.mymoneyandroid.network

import retrofit2.http.Body
import retrofit2.http.POST

// Aqui definimos as rotas que você criou no C#
interface UsuarioApiService {

    @POST("usuarios") // Esse é o caminho da sua rota de cadastro
    suspend fun cadastrarUsuario(@Body request: RegistroRequest): UsuarioResponse

    @POST("usuarios/login") // Esse é o caminho da sua rota de login
    suspend fun fazerLogin(@Body login: LoginRequest): UsuarioResponse
}