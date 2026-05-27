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

    fun realizarLogin(
        email: String,
        senha: String,
        irParaTelaPrincipal: (Int) -> Unit
    ) {

        viewModelScope.launch {

            try {

                carregando = true
                mensagemErro = null

                val erroValidacao =
                    validador.validarLogin(email, senha)

                if (erroValidacao != null) {

                    mensagemErro = erroValidacao
                    println("Erro: $mensagemErro")
                    return@launch
                }

                val dados = LoginUsuario(
                    email = email,
                    senha = senha
                )

                val enviarDados =
                    apiLogin.loginUsuario(dados)

                if (enviarDados.isSuccessful) {

                    val usuarioLogado =
                        enviarDados.body()

                    if (usuarioLogado != null) {

                        irParaTelaPrincipal(
                            usuarioLogado.idUsuario
                        )

                    } else {

                        mensagemErro =
                            "Erro ao obter ID do usuário"
                    }

                } else {

                    mensagemErro =
                        "Erro: ${enviarDados.code()} - Verifique os dados"
                }

            } catch (e: Exception) {

                mensagemErro =
                    "Falha na conexão: Verifique se o servidor está rodando."

                println("Erro: ${e.message}")

            } finally {

                carregando = false
            }
        }
    }
}