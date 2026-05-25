package com.example.mymoneyandroid.network

import com.example.mymoneyandroid.model.Meta
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MetaApi {
    @GET("/metas/{idUsuario}")
    suspend fun listarMetas(@Path("idUsuario") idUsuario: Int): Response<List<Meta>>

    // Cria uma nova meta
    @POST("/metas/")
    suspend fun criarMeta(@Body novaMeta: Meta): Response<Meta>
}