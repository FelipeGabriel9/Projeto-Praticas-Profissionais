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

    // Cria o controlador que gerencia o histórico e a troca de telas
    val navController = rememberNavController()

    // Cria o mapa, entrega para o controlador para começar pela telaInicial
    NavHost(navController = navController, startDestination = "telaInicial") {

        composable("telaInicial") {
            InicialScreen(controleNavegacao = navController)
        }

        // Rota de cadastro
        composable("telaCadastro") {

            // Chama os eventos da tela
            CadastroScreen(
                controleNavegacao = navController,
                irParaLogin = {
                    navController.navigate("telaLogin")
                }
            )
        }

        // Rota de Login
        composable("telaLogin") {
            // Chama os eventos da tela de login
            LoginScreen(
                controleNavegacao = navController
            )

        }


        // Rota Mensagem
        composable("telaMensagem") {
            MensagemScreen(controleNavegacao = navController) // Chama os eventos da tela mensagem
        }

        // Rota de Categorias
        composable("telaCategoria") {
            CategoriaScreen(controleNavegacao = navController) // Chama os eventos da tela de categoria
        }

        // Rota da tela Principal
        composable("telaPrincipal") {
            PrincipalScreen(controleNavegacao = navController) // Chama os eventos da tela principal
        }

        // TESTANDO GEMINI
        // No seu NavHost (AppNavigation ou MainActivity)
        composable("detalhe_categoria/{categoriaNome}") { backStackEntry ->
            val nome = backStackEntry.arguments?.getString("categoriaNome")
            DetalheCategoriaScreen(navController, nome)
        }

        composable(
            route = "det_categoria/{categoriaNome}",
            arguments = listOf(navArgument("categoriaNome") { type = NavType.StringType })
        ) { backStackEntry ->
            val nome = backStackEntry.arguments?.getString("categoriaNome")
            DetalheCategoriaScreen(navController, nome)
        }
    }
}
