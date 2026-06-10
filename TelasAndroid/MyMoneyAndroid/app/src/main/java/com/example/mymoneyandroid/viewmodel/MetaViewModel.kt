package com.example.mymoneyandroid.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoneyandroid.model.Meta
import com.example.mymoneyandroid.network.Retrofit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MetaViewModel : ViewModel() {
    // Guarda apenas as metas reais que possuem IDs gerados pelo IDENTITY do SQL
    val metas = MutableStateFlow<List<Meta>>(emptyList())

    fun buscarMetas(idUsuario: Int) {
        viewModelScope.launch {
            try {
                val resposta = Retrofit.apiMeta.listarMetas(idUsuario)
                if (resposta.isSuccessful) {
                    metas.value = resposta.body() ?: emptyList()
                } else {
                    metas.value = emptyList()
                }
            } catch (e: Exception) {
                metas.value = emptyList()
                e.printStackTrace()
            }
        }
    }

    fun criarMeta(nome: String, idUsuario: Int) {
        viewModelScope.launch {
            try {
                val novaMeta = Meta(
                    idUsuario = idUsuario,
                    nomeMeta = nome,
                    valorObjetivo = 0.0,
                    valorAtual = 0.0
                )
                val resposta = Retrofit.apiMeta.criarMeta(novaMeta)
                if (resposta.isSuccessful) {
                    buscarMetas(idUsuario) // Atualiza e puxa a meta com o ID gerado pelo banco
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun criarMetaFixa(nomeMeta: String, idUsuario: Int, aoCriar: (Int) -> Unit) {
        viewModelScope.launch {
            try {
                val novaMeta = Meta(
                    idUsuario = idUsuario,
                    nomeMeta = nomeMeta,
                    valorObjetivo = 0.0,
                    valorAtual = 0.0
                )
                val resposta = Retrofit.apiMeta.criarMeta(novaMeta)

                if (resposta.isSuccessful) {
                    val metaCriada = resposta.body()
                    if (metaCriada != null && metaCriada.idMeta != 0) {
                        buscarMetas(idUsuario) // Atualiza a lista local
                        aoCriar(metaCriada.idMeta) // Devolve o ID para a tela fazer a navegação
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun excluirMeta(idMeta: Int) {

        viewModelScope.launch {

            try {

                val response = Retrofit.apiMeta.excluirMeta(idMeta)

                if (response.isSuccessful) {

                    metas.value = metas.value.filter {
                        it.idMeta != idMeta
                    }
                }

            } catch (e: Exception) {

                e.printStackTrace()

            }
        }
    }

    fun atualizarMeta(meta: Meta) {

        viewModelScope.launch {

            try {

                val resposta = Retrofit.apiMeta.atualizarMeta(
                    meta.idMeta,
                    meta
                )

                if (!resposta.isSuccessful) {

                    println("Erro ao atualizar meta")

                }

            } catch (e: Exception) {

                e.printStackTrace()

            }
        }
    }
}