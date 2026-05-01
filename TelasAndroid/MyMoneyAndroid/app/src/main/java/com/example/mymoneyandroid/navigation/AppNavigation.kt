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
import com.example.mymoneyandroid.view.TelaInicial



@Composable
// Função central que vai ligar todas as telas do app
fun AppNavigation() {

    // Cria o controlador que gerencia o histórico e a troca de telas
    val navController = rememberNavController()

    // Cria o mapa, entrega para o controlador para começar pela telaInicial
    NavHost(navController = navController, startDestination = "telaInicial") {

        composable("telaInicial") {
            TelaInicial(navController = navController)
        }

        // Rota de cadastro
        composable("telaCadastro") {

            // Chama os eventos da tela
            CadastroScreen(
                irParaLogin = {
                    // O NavController faz a transição para a rota de login
                    navController.navigate("telaLogin")
                }
            )
        }

        // Rota de Login
        composable("telaLogin") {
            // Chama os eventos da tela de login
            LoginScreen(
                irParaPrincipal = {
                // O NavController faz a transição para a rota da tela principal
                  navController.navigate("telaPrincipal")
                }
            )

        }

        // Rota de Categorias
        composable("telaCategoria") {
            CategoriaScreen(navController = navController) // Chama os eventos da tela de categoria
        }

        // Rota da tela Principal
        composable("telaPrincipal") {
            PrincipalScreen() // Chama os eventos da tela principal
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
