package com.example.mymoneyandroid.network

import com.example.mymoneyandroid.model.LoginUsuario
import com.example.mymoneyandroid.model.DadosUsuario
import com.example.mymoneyandroid.model.CadastroUsuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UsuarioApi {
    @GET("/usuarios/{id}")
    suspend fun dadosPerfil(@Path("id") id: Int): Response<DadosUsuario>

    @POST("/usuarios/")
    suspend fun cadastrarUsuario(@Body requisicao: CadastroUsuario): Response<DadosUsuario>

    @POST("usuarios/login")
    suspend fun loginUsuario(@Body login: LoginUsuario): Response<DadosUsuario>

}