package com.example.mymoneyandroid.network

import com.example.mymoneyandroid.model.LoginRequest
import com.example.mymoneyandroid.model.RegistroRequest
import com.example.mymoneyandroid.model.UsuarioResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UsuarioApiService {

    @POST("usuarios") // Esse é o caminho da sua rota de cadastro
    suspend fun cadastrarUsuario(@Body request: RegistroRequest): Response<UsuarioResponse>

    @POST("usuarios/login") // Esse é o caminho da sua rota de login
    suspend fun fazerLogin(@Body login: LoginRequest): Response<UsuarioResponse>

import com.example.mymoneyandroid.model.CadastroUsuario
import com.example.mymoneyandroid.model.DadosUsuario
import com.example.mymoneyandroid.model.LoginUsuario
import retrofit2.http.Body
import retrofit2.http.POST


interface UsuarioApiService {

    @POST("usuarios")
    suspend fun cadastrarUsuario(@Body request: CadastroUsuario): DadosUsuario

    @POST("usuarios/login")
    suspend fun fazerLogin(@Body login: LoginUsuario): DadosUsuario
}