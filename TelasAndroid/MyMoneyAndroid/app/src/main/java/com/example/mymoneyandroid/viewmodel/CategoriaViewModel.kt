package com.example.mymoneyandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoneyandroid.model.Categoria
import com.example.mymoneyandroid.network.Retrofit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoriaViewModel : ViewModel() {

    private val _categorias =
        MutableStateFlow<List<Categoria>>(emptyList())

    val categorias =
        _categorias.asStateFlow()

    // Categorias fixas para todos os usuários
    private val categoriasFixas = listOf(
        "Saúde",
        "Lazer",
        "Aluguel",
        "Alimentação",
        "Presentes",
        "Transporte",
        "Família",
        "Academia"
    )

    fun buscarCategorias(idUsuario: Int) {

        viewModelScope.launch {

            try {

                // Busca categorias já existentes no banco
                val resposta =
                    Retrofit
                        .apiCategoria
                        .listarCategorias(idUsuario)

                if (resposta.isSuccessful) {

                    val listaDoBanco =
                        resposta.body() ?: emptyList()

                    // Pega apenas os nomes já existentes
                    val nomesExistentes =
                        listaDoBanco.map {
                            it.nomeCategoria
                        }

                    // Verifica quais categorias fixas ainda não existem
                    val categoriasParaCriar =
                        categoriasFixas.filter { nome ->
                            nome !in nomesExistentes
                        }

                    // Cria apenas as que faltam
                    categoriasParaCriar.forEach { nome ->

                        val novaCategoria =
                            Categoria(
                                nomeCategoria = nome,
                                valorDespesa = 0.0,
                                idUsuario = idUsuario
                            )

                        Retrofit
                            .apiCategoria
                            .criarCategoria(novaCategoria)
                    }

                    // Busca novamente após criar
                    val novaResposta =
                        Retrofit
                            .apiCategoria
                            .listarCategorias(idUsuario)

                    if (novaResposta.isSuccessful) {

                        _categorias.value =
                            novaResposta.body() ?: emptyList()
                    }

                }

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }

    fun criarCategoria(
        nome: String,
        idUsuario: Int
    ) {

        viewModelScope.launch {

            try {

                val novaCategoria =
                    Categoria(
                        nomeCategoria = nome,
                        valorDespesa = 0.0,
                        idUsuario = idUsuario
                    )

                val resposta =
                    Retrofit
                        .apiCategoria
                        .criarCategoria(novaCategoria)

                println(resposta.body())

                if (resposta.isSuccessful) {

                    buscarCategorias(idUsuario)
                }

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }

    fun criarCategoriaFixa(
        nomeCategoria: String,
        idUsuario: Int,
        aoCriar: (Categoria) -> Unit
    ) {

        viewModelScope.launch {

            try {

                val categoriaExistente =
                    _categorias.value.find {

                        it.nomeCategoria.equals(
                            nomeCategoria,
                            ignoreCase = true
                        )
                    }

                // Se já existir no banco/lista
                if (categoriaExistente != null) {

                    aoCriar(categoriaExistente)

                } else {

                    val novaCategoria =
                        Categoria(
                            nomeCategoria = nomeCategoria,
                            valorDespesa = 0.0,
                            idUsuario = idUsuario
                        )

                    val resposta =
                        Retrofit
                            .apiCategoria
                            .criarCategoria(novaCategoria)

                    if (resposta.isSuccessful) {

                        val categoriaCriada =
                            resposta.body()

                        if (categoriaCriada != null) {

                            buscarCategorias(idUsuario)

                            aoCriar(categoriaCriada)
                        }
                    }
                }

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }

    fun atualizarValorCategoria(
        categoria: Categoria,
        novoValor: Double,
        idUsuario: Int
    ) {

        viewModelScope.launch {

            try {

                val categoriaAtualizada =
                    categoria.copy(
                        valorDespesa = novoValor
                    )

                val resposta =
                    Retrofit.apiCategoria.atualizarCategoria(
                        categoria.idCategoria ?: 0,
                        categoriaAtualizada
                    )

                if (resposta.isSuccessful) {

                    buscarCategorias(idUsuario)
                }

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }

    fun excluirCategoria(
        idCategoria: Int,
        idUsuario: Int
    ) {

        viewModelScope.launch {

            try {

                val resposta =
                    Retrofit.apiCategoria.excluirCategoria(
                        idCategoria
                    )

                if (resposta.isSuccessful) {

                    buscarCategorias(idUsuario)
                }

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }
}