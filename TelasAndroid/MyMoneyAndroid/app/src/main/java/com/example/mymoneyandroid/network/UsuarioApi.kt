package com.example.mymoneyandroid.network

import com.example.mymoneyandroid.model.LoginUsuario
import com.example.mymoneyandroid.model.DadosUsuario
import com.example.mymoneyandroid.model.CadastroUsuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UsuarioApi {

    // Rota para obter os dados de um usuário
    @GET("/usuarios/{id}")
    suspend fun dadosPerfil(@Path("id") id: Int): Response<DadosUsuario>

    // Rota para cadastrar um novo usuário
    @POST("/usuarios/")
    suspend fun cadastrarUsuario(@Body requisicao: CadastroUsuario): Response<DadosUsuario>

    // Rota para realizar login
    @POST("/usuarios/login")
    suspend fun loginUsuario(@Body login: LoginUsuario): Response<DadosUsuario>

    // Rota para deletar uma conta
    @DELETE("/usuarios/{id}")
    suspend fun excluirUsuario(@Path("id") id: Int): Response<Unit>

}