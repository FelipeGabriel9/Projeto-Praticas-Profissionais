package com.example.mymoneyandroid.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mymoneyandroid.view.CadastroScreen
import com.example.mymoneyandroid.view.LoginScreen
import com.example.mymoneyandroid.view.CategoriaScreen
import com.example.mymoneyandroid.view.PrincipalScreen
import com.example.mymoneyandroid.view.DetalheCategoriaScreen
import com.example.mymoneyandroid.view.InicialScreen
import com.example.mymoneyandroid.view.MensagemScreen


@Composable
// Função central que vai ligar todas as telas do app
fun AppNavigation() {

    // Cria o controlador que gerencia a troca de telas
    val controleNavegacao = rememberNavController()

    // Cria o mapa, entrega para o controlador para começar pela telaInicial
    NavHost(navController = controleNavegacao, startDestination = "telaInicial") {

        // Rota da tela Inicial
        composable("telaInicial") {
            InicialScreen(
                controleNavegacao = controleNavegacao
            )
        }

        // Rota de Cadastro
        composable("telaCadastro") {
            CadastroScreen(
                controleNavegacao = controleNavegacao,
                irParaLogin = {
                    controleNavegacao.navigate("telaLogin")
                }
            )
        }

        // Rota de Login
        composable("telaLogin") {
            LoginScreen(
                controleNavegacao = controleNavegacao
            )
        }

        // Rota Mensagem
        composable("telaMensagem") {
            MensagemScreen(
                controleNavegacao = controleNavegacao
            )
        }

        // Rota de Categorias
        composable("telaCategoria") {
            CategoriaScreen(
                controleNavegacao = controleNavegacao
            )
        }

        // Rota da tela Principal
        composable("telaPrincipal") {
            PrincipalScreen(
                controleNavegacao = controleNavegacao
            )
        }

        // Rota para os detalhes de uma categoria
        composable(
            "detalhescategoria/{nomeCategoria}",
            listOf(navArgument("nomeCategoria") {
                type = NavType.StringType
            })
        ) { tela ->
            val nome = tela.arguments?.getString("nomeCategoria")
            DetalheCategoriaScreen(
                controleNavegacao = controleNavegacao,
                nomeCategoria = nome
            )
        }
    }
}
