package com.example.mymoneyandroid.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoneyandroid.model.RegistroRequest
import com.example.mymoneyandroid.network.UsuarioApiService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CadastroViewModel : ViewModel() {

    var carregando by mutableStateOf(false)
    var mensagemErro by mutableStateOf<String?>(null)

    // Ajustei a porta para 5194 para bater com a sua API C#
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:5194/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(UsuarioApiService::class.java)

    fun realizarCadastro(nome: String, cpf: String, email: String, senha: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            carregando = true
            mensagemErro = null

            try {
                // Preenchendo o molde com os dados da tela (garantindo que cada um vá pro lugar certo)
                val request = RegistroRequest(
                    nome = nome,
                    email = email,
                    senhaHash = senha,
                    cpf = cpf
                )

                // Envia para a API C#
                val response = apiService.cadastrarUsuario(request)

                if (response.isSuccessful) {
                    onSuccess() // Se deu certo, a tela vai navegar para frente
                } else {
                    mensagemErro = "Erro da API: ${response.code()}"
                }

            } catch (e: Exception) {
                mensagemErro = "Erro ao conectar: ${e.message}"
            } finally {
                carregando = false
            }
        }
    }
}