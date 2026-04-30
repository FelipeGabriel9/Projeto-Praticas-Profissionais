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

    // Cria o mapa, entrega para o controlador para começar pela tela1
    NavHost(navController = navController, startDestination = "telaIInicial") {

        composable("telaInicial") {
            TelaInicial(navController = navController)
        }

        // Cria o endereço/rota chamado exatamente "tela1"
        composable("cadastro") {

            // Chama o desenho da Tela 1
            CadastroScreen(
                onLoginClick = {
                    // O NavController faz a transição para a rota de login
                    navController.navigate("login")
                }
            )
        }
        // Rota de Login
        composable("login") {
            LoginScreen() // Sua tela de login

        }

        composable("categoria") {
            CategoriaScreen(navController = navController) // Tela de categoria
        }


        // TESTANDO GEMINI
        // No seu NavHost (AppNavigation ou MainActivity)
        composable("detalhe_categoria/{categoriaNome}") { backStackEntry ->
            val nome = backStackEntry.arguments?.getString("categoriaNome")
            DetalheCategoriaScreen(navController, nome)
        }

        composable("principal") {
            PrincipalScreen()

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
