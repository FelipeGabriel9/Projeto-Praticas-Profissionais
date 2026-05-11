package com.example.mymoneyandroid.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoneyandroid.model.RegistroRequest
import com.example.mymoneyandroid.network.Retrofit
import kotlinx.coroutines.launch


class CadastroViewModel : ViewModel() {

    var carregando by mutableStateOf(false)
    var mensagemErro by mutableStateOf<String?>(null)

    // Usamos apenas uma instância, vinda do seu RetrofitClient configurado
    private val apiService = Retrofit.apiUsuario

    fun realizarCadastro(nome: String, cpf: String, email: String, senha: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            carregando = true
            mensagemErro = null

            try {
                // Preenchendo o molde com os dados da tela
                val request = RegistroRequest(
                    nome = nome,
                    email = email,
                    senhaHash = senha,
                    cpf = cpf
                )

                // Envia para a API C#
                val response = apiService.cadastrarUsuario(request)

                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    // Pega a mensagem de erro que vem da API se possível
                    mensagemErro = "Erro: ${response.code()} - Verifique os dados"
                }

            } catch (e: Exception) {
                // Aqui tratamos qualquer erro de conexão ou falha na chamada
                mensagemErro = "Falha na conexão: Verifique se o servidor está rodando."
                println("DEBUG_API_ERROR: ${e.message}")
            } finally {
                carregando = false
            }
        }
    }
}