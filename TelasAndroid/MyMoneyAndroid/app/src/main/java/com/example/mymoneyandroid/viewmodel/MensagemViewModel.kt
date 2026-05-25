package com.example.mymoneyandroid.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoneyandroid.model.Mensagem
import com.example.mymoneyandroid.network.Retrofit
import kotlinx.coroutines.launch

class MensagemViewModel : ViewModel() {

    var carregando by mutableStateOf(false)
    var mensagemErro by mutableStateOf<String?>(null)
    private val apiMensagem = Retrofit.apiMensagem

    fun realizarEnvioMensagem(assunto: String, mensagem: String, idUsuario: Int, mensagemEnviada: () -> Unit){
        viewModelScope.launch {
            carregando = true
            mensagemErro = null

            try {
                // Preenchendo o molde com os dados da tela
                val dados = Mensagem(
                    idUsuario = idUsuario,
                    assunto = assunto,
                    mensagem = mensagem
                )

                // Envia para a API C#
                val enviarDados = apiMensagem.criarMensagem(dados)

                if (enviarDados.isSuccessful) {
                    mensagemEnviada()
                }
                else{
                    // Pega a mensagem de erro que vem da API se possível
                    mensagemErro = "Erro: ${enviarDados.code()} - Verifique os dados"
                }

            } catch (e: Exception) {
                // Aqui tratamos qualquer erro de conexão ou falha na chamada
                mensagemErro = "Falha na conexão: Verifique se o servidor está rodando."
                println("Erro: ${e.message}")
            } finally {
                carregando = false
            }

        }
    }
}