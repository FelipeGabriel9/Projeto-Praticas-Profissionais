package com.example.mymoneyandroid.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoneyandroid.model.DadosUsuario
import com.example.mymoneyandroid.network.Retrofit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PerfilViewModel : ViewModel() {

    // Mutable privado
    private val _perfil = MutableStateFlow<DadosUsuario?>(null)

    // StateFlow público
    val perfil: StateFlow<DadosUsuario?> = _perfil

    var carregando by mutableStateOf(false)
    var mensagemErro by mutableStateOf<String?>(null)

    private val apiPerfil = Retrofit.apiUsuario

    fun carregarPerfilDoUsuario(idUsuario: Int) {

        viewModelScope.launch {

            carregando = true
            mensagemErro = null

            try {

                val resposta = apiPerfil.dadosPerfil(idUsuario)

                if (resposta.isSuccessful) {
                    _perfil.value = resposta.body()
                }
                else{
                    mensagemErro =
                        "Erro ao carregar perfil: ${resposta.code()}"
                }

            } catch (e: Exception) {

                mensagemErro = "Falha na conexão com o servidor."

                Log.e("PerfilVM", "Erro: ${e.message}")

            } finally {

                carregando = false
            }
        }
    }
}