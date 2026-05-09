package com.example.mymoneyandroid.network

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