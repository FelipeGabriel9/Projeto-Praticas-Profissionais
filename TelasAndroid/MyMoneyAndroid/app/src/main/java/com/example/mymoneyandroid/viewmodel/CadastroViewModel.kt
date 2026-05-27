package com.example.mymoneyandroid.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoneyandroid.model.CadastroUsuario
import com.example.mymoneyandroid.model.ValidarCadastro
import com.example.mymoneyandroid.network.Retrofit
import kotlinx.coroutines.launch


class CadastroViewModel : ViewModel() {

    var carregando by mutableStateOf(false)
    var mensagemErro by mutableStateOf<String?>(null)
    private val apiCadastro = Retrofit.apiUsuario
    private val validador = ValidarCadastro()

    fun realizarCadastro(nome: String, cpf: String, email: String, senha: String, irParaTelaPrincipal: (Int) -> Unit) {
        viewModelScope.launch {
            try {
                carregando = true
                mensagemErro = null

                val erroValidacao = validador.validarCadastro(cpf, email, senha)

                if (erroValidacao != null) {
                    mensagemErro = erroValidacao
                    println("Erro: ${mensagemErro.toString()}")
                }

                // Preenchendo o molde com os dados da tela
                val dados = CadastroUsuario(
                    Nome = nome,
                    Cpf = cpf,
                    Email = email,
                    Senha = senha
                )

                // Envia para a API C#
                val enviarDados = apiCadastro.cadastrarUsuario(dados)


                if (enviarDados.isSuccessful) {
                    val idUsuario: Int? = enviarDados.body()?.idUsuario

                    if (idUsuario != null) {
                        irParaTelaPrincipal(idUsuario)
                    } else {
                        irParaTelaPrincipal(1) // Caso de erro ao buscar o id do usuário, forçamos a usar um padrão
                    }

                } else {
                    // Pega a mensagem de erro que vem da API se possível
                    mensagemErro = "Erro: ${enviarDados.code()} - Verifique os dados"
                }

            }catch (e: Exception) {
                    // Aqui tratamos qualquer erro de conexão ou falha na chamada
                    mensagemErro = "Falha na conexão: Verifique se o servidor está rodando."
                    println(e.stackTraceToString())
            } finally {
                carregando = false

            }
        }
    }
}