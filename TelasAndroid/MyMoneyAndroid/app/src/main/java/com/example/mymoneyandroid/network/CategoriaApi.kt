package com.example.mymoneyandroid.network


import com.example.mymoneyandroid.model.Categoria
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CategoriaApi {
    // Lista todas as categorias
    @GET("/categorias/")
    suspend fun listarCategorias(): Response<List<Categoria>>

    // Cria uma nova categoria
    @POST("/categorias/")
    suspend fun criarCategoria(@Body novaCategoria: Categoria): Response<Categoria>

}