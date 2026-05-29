package com.example.mymoneyandroid.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoneyandroid.model.Meta
import com.example.mymoneyandroid.network.Retrofit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MetaViewModel : ViewModel() {
    val metas = MutableStateFlow<List<Meta>>(emptyList())

    // Metas fixas para todos os usuários
    private val metasFixas = listOf(
        "Viagens", "Casamento", "Compras", "Criar"
    )

    fun buscarMetas(idUsuario: Int) {
        viewModelScope.launch {
            try {
                // Mapeia a lista de nomes fixos para objetos do tipo Meta
                val listaFixasComoObjetos = metasFixas.map { nome ->
                    Meta(
                        idUsuario = idUsuario,
                        nomeMeta = nome,
                        valorObjetivo = 0.0,
                        valorAtual = 0.0
                    )
                }

                // Busca o que o usuário já adicionou
                val resposta = Retrofit.apiMeta.listarMetas(idUsuario)

                if (resposta.isSuccessful) {
                    val listaDoBanco = resposta.body() ?: emptyList()

                    metas.value = listaFixasComoObjetos + listaDoBanco
                } else {
                    // Se a API falhar por algum motivo, mostra apenas metas fixas na tela
                    metas.value = listaFixasComoObjetos
                }
            } catch (e: Exception) {
                // Se der erro de servidor desligado, garante que as fixas apareçam
                val listaFixasComoObjetos = metasFixas.map { nome ->
                    Meta(
                        idUsuario = idUsuario,
                        nomeMeta = nome,
                        valorObjetivo = 0.0,
                        valorAtual = 0.0
                    )
                }
                metas.value = listaFixasComoObjetos
                e.printStackTrace()
            }
        }
    }

    fun criarMeta(nome: String, idUsuario: Int) {
        viewModelScope.launch {
            try {
                // Monta a meta nova atribuindo ao ID do usuário logado
                val novaMeta = Meta(
                    idUsuario = idUsuario,
                    nomeMeta = nome,
                    valorObjetivo = 0.0,
                    valorAtual = 0.0
                )

                val resposta = Retrofit.apiMeta.criarMeta(novaMeta)

                if (resposta.isSuccessful) {
                    // Atualiza a tela chamando a busca novamente
                    buscarMetas(idUsuario)
                }
            } catch (e: Exception) {
                // Trata o erro silenciosamente ou mantém o fluxo básico
            }
        }
    }

    // Adicione esta função dentro da classe MetaViewModel
    fun excluirMeta(idMeta: Int, idUsuario: Int) {
        viewModelScope.launch {
            try {
                // Substitua 'Retrofit.apiMeta' pelo nome real do seu objeto de conexão do Retrofit
                val response = Retrofit.apiMeta.excluirMeta(idMeta, idUsuario)

                if (response.isSuccessful) {
                    // Se deletou no banco com sucesso, atualiza a lista de metas na tela
                    buscarMetas(idUsuario)
                }
            } catch (e: Exception) {
                // Trate o erro se necessário (ex: log ou mudar um estado de erro)
            }
        }
    }
}