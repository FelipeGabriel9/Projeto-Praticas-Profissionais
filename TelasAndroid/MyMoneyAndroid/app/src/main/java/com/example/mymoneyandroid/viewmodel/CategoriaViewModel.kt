package com.example.mymoneyandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoneyandroid.model.Categoria
import com.example.mymoneyandroid.network.Retrofit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CategoriaViewModel : ViewModel() {

    private val _categorias = MutableStateFlow<List<Categoria>>(emptyList())

    val categorias = _categorias.asStateFlow()

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
                val resposta = Retrofit.apiCategoria.listarCategorias(idUsuario)

                if (resposta.isSuccessful) {
                    val listaDoBanco = resposta.body() ?: emptyList()
                    println(listaDoBanco)

                    val listaCompleta = (listaFixasComoObjetos + listaDoBanco).distinctBy { it.NomeCategoria }
                    _categorias.value = listaCompleta
                } else {
                    // Se a API falhar por algum motivo, mostra apenas categorias fixas na tela
                    _categorias.value = listaFixasComoObjetos
                }
            } catch (e: Exception) {
                e.printStackTrace()

                // Se der erro de servidor desligado, garante que as fixas apareçam
                val listaFixasComoObjetos = categoriasFixas.map { nome ->
                    Categoria(NomeCategoria = nome, ValorDespesa = 0.0, idUsuario = idUsuario)
                }
                _categorias.value = listaFixasComoObjetos
            }
        }
    }

    fun criarCategoria(nome: String, idUsuario: Int) {

        viewModelScope.launch {

            try {

                val novaCategoria = Categoria(
                    NomeCategoria = nome,
                    ValorDespesa = 0.0,
                    idUsuario = idUsuario
                )

                val resposta =
                    Retrofit.apiCategoria.criarCategoria(novaCategoria)

                if (resposta.isSuccessful) {

                    // BUSCA TUDO DE NOVO
                    buscarCategorias(idUsuario)
                }

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }
}