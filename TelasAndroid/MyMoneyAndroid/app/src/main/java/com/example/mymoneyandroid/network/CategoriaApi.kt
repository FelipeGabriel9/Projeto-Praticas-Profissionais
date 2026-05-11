package com.example.mymoneyandroid.network


import com.example.mymoneyandroid.model.Categoria
import com.example.mymoneyandroid.model.RegistroRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CategoriaApi {
    // Lista todas as categorias
    @GET("categorias")
    suspend fun listarCategorias(): Response<List<Categoria>>

    // Cria uma nova categoria
    @POST("categorias")
    suspend fun criarCategoria(@Body novaCategoria: Categoria): Response<Categoria>

    // Busca uma categoria por ID
    @GET("categorias/{id}")
    suspend fun getCategoriaPorId(@Path("id") id: Int): Response<Categoria>

    // Atualiza uma categoria
    @PUT("categorias/{id}")
    suspend fun atualizarCategoria(@Path("id") id: Int, @Body categoriaAtualizada: Categoria): Response<Categoria>

}