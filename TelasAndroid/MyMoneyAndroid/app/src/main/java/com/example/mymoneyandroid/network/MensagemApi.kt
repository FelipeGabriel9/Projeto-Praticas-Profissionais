package com.example.mymoneyandroid.network

import com.example.mymoneyandroid.model.Mensagem
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MensagemApi {
    @POST("/mensagem/")
    suspend fun criarMensagem(@Body novaMensagem: Mensagem): Response<Mensagem>
}