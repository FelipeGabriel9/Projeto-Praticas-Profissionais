package com.example.mymoneyandroid.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoneyandroid.model.CadastroUsuario
import com.example.mymoneyandroid.network.RetrofitClient
import kotlinx.coroutines.launch

class CadastroViewModel : ViewModel() {
    var carregando by mutableStateOf(false)
    var mensagemErro by mutableStateOf<String?>(null)

    // NÃO precisa criar o retrofit aqui de novo.
    // Usamos a instância que já configuramos no RetrofitClient
    private val apiService = RetrofitClient.instancia

    fun realizarCadastro(nome: String, cpf: String, email: String, senha: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            carregando = true
            mensagemErro = null
            try {
                // Use o nome da classe do seu model: CadastroUsuario
                val request = CadastroUsuario(nome, email, cpf, senha)
                apiService.cadastrarUsuario(request)
                onSuccess()
            } catch (e: Exception) {
                // Melhora a mensagem de erro para o usuário
                mensagemErro = "Erro ao cadastrar. Verifique sua conexão."
                println("DEBUG_API: ${e.message}") // Log para você ver o erro real no Logcat
            } finally {
                carregando = false
            }
        }
    }
}