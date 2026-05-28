package com.example.mymoneyandroid.network


import com.example.mymoneyandroid.model.Categoria
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CategoriaApi {
    // Lista todas as categorias
    @GET("/categorias/{idUsuario}")
    suspend fun listarCategorias(@Path("idUsuario") idUsuario: Int): Response<List<Categoria>>

    // Cria uma nova categoria
    @POST("/categorias/")
    suspend fun criarCategoria(@Body novaCategoria: Categoria): Response<Categoria>

    // Atualiza o valor de uma despesa no BD
    @PUT("/categorias/{idCategoria}")
    suspend fun atualizarCategoria(@Path("idCategoria") idCategoria: Int, @Body categoria: Categoria): Response<Categoria>
}