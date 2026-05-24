package com.example.mymoneyandroid.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoneyandroid.model.Categoria
import com.example.mymoneyandroid.network.Retrofit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CategoriaViewModel : ViewModel() {
    val categorias = MutableStateFlow<List<Categoria>>(emptyList())

    // Categorias fixas para todos os usuários
    private val categoriasFixas = listOf(
        "Saúde", "Lazer", "Aluguel", "Alimentação", "Presentes", "Transporte", "Família", "Academia"
    )

    fun buscarCategorias(idUsuario: Int) {
        viewModelScope.launch {
            try {
                // Mapeia a lista de nomes fixos para objetos do tipo Categoria
                val listaFixasComoObjetos = categoriasFixas.map { nome ->
                    Categoria(NomeCategoria = nome, ValorDespesa = 0.0, idUsuario = idUsuario)
                }

                // Busca o que o usuário já adicionou
                val response = Retrofit.apiCategoria.listarCategorias(idUsuario)

                if (response.isSuccessful) {
                    val listaDoBanco = response.body() ?: emptyList()

                    categorias.value = listaFixasComoObjetos + listaDoBanco
                } else {
                    // Se a API falhar por algum motivo, mostra apenas categorias fixas na tela
                    categorias.value = listaFixasComoObjetos
                }
            } catch (e: Exception) {
                // Se der erro de servidor desligado, garante que as fixas apareçam
                val listaFixasComoObjetos = categoriasFixas.map { nome ->
                    Categoria(NomeCategoria = nome, ValorDespesa = 0.0, idUsuario = idUsuario)
                }
                categorias.value = listaFixasComoObjetos
            }
        }
    }

    fun criarCategoria(nome: String, idUsuario: Int) {
        viewModelScope.launch {
            try {
                // Monta a categoria nova atribuindo ao ID do usuário logado
                val novaCategoria = Categoria(
                    NomeCategoria = nome,
                    ValorDespesa = 0.0,
                    idUsuario = idUsuario
                )

                val response = Retrofit.apiCategoria.criarCategoria(novaCategoria)

                if (response.isSuccessful) {
                    // Atualiza a tela chamando a busca novamente
                    buscarCategorias(idUsuario)
                }
            } catch (e: Exception) {
                // Trata o erro silenciosamente ou mantém o fluxo básico
            }
        }
    }
}