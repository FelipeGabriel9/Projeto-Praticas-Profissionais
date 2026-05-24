package com.example.mymoneyandroid.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoneyandroid.model.DadosUsuario
import com.example.mymoneyandroid.network.Retrofit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PerfilViewModel : ViewModel() {

    // Guarda os dados do perfil obtidos da API
    val perfil = MutableStateFlow<DadosUsuario?>(null)

    // Estados para controlar o carregamento e as mensagens de erro na tela
    var carregando by mutableStateOf(false)
    var mensagemErro by mutableStateOf<String?>(null)

    // Instancia o serviço de API para comunicação com o servidor
    private val apiPerfil = Retrofit.apiUsuario

    // Busca os dados do perfil do usuário com base no ID
    fun carregarPerfilDoUsuario(idUsuario: Int) {
        viewModelScope.launch {
            carregando = true
            mensagemErro = null

            try {
                val resposta = apiPerfil.dadosPerfil(idUsuario)

                if (resposta.isSuccessful) {
                    perfil.value = resposta.body()
                } else {
                    mensagemErro = "Erro ao carregar perfil: ${resposta.code()}"
                }
            } catch (e: Exception) {
                mensagemErro = "Falha na conexão com o servidor."
                e.printStackTrace()
            } finally {
                carregando = false
            }
        }
    }

    // Exclui permanentemente a conta do usuário do banco de dados
    fun excluirConta(idUsuario: Int, onSucesso: () -> Unit) {
        viewModelScope.launch {
            carregando = true
            mensagemErro = null

            try {
                val resposta = apiPerfil.excluirUsuario(idUsuario)

                if (resposta.isSuccessful) {
                    onSucesso() // Executa a navegação e limpeza local se a API deletar com sucesso
                } else {
                    mensagemErro = "Erro ao excluir conta: ${resposta.code()}"
                }
            } catch (e: Exception) {
                mensagemErro = "Falha na conexão com o servidor."
                e.printStackTrace()
            } finally {
                carregando = false
            }
        }
    }
}