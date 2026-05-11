package com.example.mymoneyandroid.viewmodel // Ajuste o pacote se precisar

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoneyandroid.model.Categoria
import com.example.mymoneyandroid.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class CategoriaViewModel : ViewModel() {

    // Guarda a lista de categorias. A tela vai "ficar olhando" para essa variável.
    private val _categorias = MutableStateFlow<List<Categoria>>(emptyList())
    val categorias: StateFlow<List<Categoria>> = _categorias

    init {
        // Assim que a tela abrir, ele já puxa os dados da API
        buscarCategorias()
    }

    private fun buscarCategorias() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiCategoria.listarCategorias()
                if (response.isSuccessful) {
                    response.body()?.let { lista ->
                        _categorias.value = lista
                    }
                } else {
                    Log.e("API", "Erro ao buscar: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("API", "Falha de conexão: ${e.message}")
            }
        }
    }

    fun criarCategoria(nome: String) {
        viewModelScope.launch {
            try {
                // Monta o objeto para enviar para a API C#.
                // Coloquei ValorDespesa = 0.0 pois a API exige esse campo.
                val novaCategoria = Categoria(NomeCategoria = nome, ValorDespesa = 0.0)

                val response = RetrofitClient.apiCategoria.criarCategoria(novaCategoria)
                if (response.isSuccessful) {
                    // Se deu certo criar, busca a lista atualizada do banco!
                    buscarCategorias()
                }
            } catch (e: Exception) {
                Log.e("API", "Falha ao criar: ${e.message}")
            }
        }
    }
}