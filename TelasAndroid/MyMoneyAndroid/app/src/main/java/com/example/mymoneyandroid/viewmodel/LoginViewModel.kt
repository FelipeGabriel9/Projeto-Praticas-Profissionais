package com.example.mymoneyandroid.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoneyandroid.model.LoginUsuario
import com.example.mymoneyandroid.model.ValidarLogin
import com.example.mymoneyandroid.network.Retrofit
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    var carregando by mutableStateOf(false)
    var mensagemErro by mutableStateOf<String?>(null)
    private val apiLogin = Retrofit.apiUsuario
    private val validador = ValidarLogin()

    fun realizarLogin(email: String, senha: String, irParaTelaPrincipal: () -> Unit) {
        viewModelScope.launch {
            carregando = true
            mensagemErro = null

            // Dentro do CadastroViewModel:
            val erroValidacao = validador.validarLogin(email, senha)

            if (erroValidacao != null) {
                mensagemErro = erroValidacao
                println("Erro: ${mensagemErro.toString()}")
            }
            else {
                try {

                    val dados = LoginUsuario(
                        email = email,
                        senha = senha
                    )

                    val enviarDados = apiLogin.loginUsuario(dados)

                    if (enviarDados.isSuccessful) {
                        irParaTelaPrincipal()
                    } else {
                        mensagemErro = "Erro: ${enviarDados.code()} - Verifique os dados"
                    }
                } catch (e: Exception) {
                    mensagemErro = "Falha na conexão: Verifique se o servidor está rodando."
                    println("Erro: ${e.message}")
                } finally {
                    carregando = false
                }
            }
        }
    }
}