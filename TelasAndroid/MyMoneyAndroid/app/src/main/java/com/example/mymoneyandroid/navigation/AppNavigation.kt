package com.example.mymoneyandroid.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mymoneyandroid.view.CadastroScreen
import com.example.mymoneyandroid.view.LoginScreen
import com.example.mymoneyandroid.view.CategoriaScreen
import com.example.mymoneyandroid.view.DetalheCategoriaFixaScreen
import com.example.mymoneyandroid.view.PrincipalScreen
import com.example.mymoneyandroid.view.DetalheCategoriaScreen
import com.example.mymoneyandroid.view.DetalheMetaScreen
import com.example.mymoneyandroid.view.DetalheMetaFixaScreen // Importado corretamente
import com.example.mymoneyandroid.view.InicialScreen
import com.example.mymoneyandroid.view.MensagemScreen
import com.example.mymoneyandroid.view.MetasScreen
import com.example.mymoneyandroid.view.PerfilScreen

@Composable
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
        composable(
            route = "telaMensagem/{idUsuario}",
            arguments = listOf(navArgument("idUsuario") {
                type = NavType.IntType
            })
        ) { tela ->
            val idUsuario = tela.arguments?.getInt("idUsuario") ?: 0
            MensagemScreen(
                controleNavegacao = controleNavegacao,
                idUsuario = idUsuario
            )
        }

        // Rota de Categorias
        composable(
            route = "telaCategoria/{idUsuario}",
            arguments = listOf(navArgument("idUsuario") {
                type = NavType.IntType
            })
        ) { tela ->
            val idUsuario = tela.arguments?.getInt("idUsuario") ?: 0
            CategoriaScreen(
                controleNavegacao = controleNavegacao,
                idUsuario = idUsuario
            )
        }

        // Rota da tela Principal
        composable(
            route = "telaPrincipal/{idUsuario}",
            arguments = listOf(navArgument("idUsuario") {
                type = NavType.IntType
            })
        ) { tela ->
            val idUsuario = tela.arguments?.getInt("idUsuario") ?: 0
            PrincipalScreen(
                controleNavegacao = controleNavegacao,
                idUsuario = idUsuario
            )
        }

        // Rota Metas
        composable(
            route = "telaMetas/{idUsuario}",
            arguments = listOf(navArgument("idUsuario") {
                type = NavType.IntType
            })
        ) { tela ->
            val idUsuario = tela.arguments?.getInt("idUsuario") ?: 0
            MetasScreen(
                controleNavegacao = controleNavegacao,
                idUsuario = idUsuario
            )
        }

        // Rota Perfil
        composable(
            route = "telaPerfil/{idUsuario}",
            arguments = listOf(navArgument("idUsuario") {
                type = NavType.IntType
            })
        ) { tela ->
            val idUsuario = tela.arguments?.getInt("idUsuario") ?: 0
            PerfilScreen(
                controleNavegacao = controleNavegacao,
                idUsuario = idUsuario
            )
        }

        // Rota para os detalhes de uma categoria
        composable(
            route = "detalhescategoria/{idCategoria}/{idUsuario}",
            arguments = listOf(
                navArgument("idCategoria") { type = NavType.IntType },
                navArgument("idUsuario") { type = NavType.IntType }
            )
        ) { tela ->
            val idCategoria = tela.arguments?.getInt("idCategoria") ?: 0
            val idUsuario = tela.arguments?.getInt("idUsuario") ?: 0

            DetalheCategoriaScreen(
                controleNavegacao = controleNavegacao,
                idCategoria = idCategoria,
                idUsuario = idUsuario
            )
        }

        // Rota para uma categoria fixa
        composable(
            route = "detalhescategoriafixa/{nomeCategoria}/{idUsuario}",
            arguments = listOf(
                navArgument("nomeCategoria") {
                    type = NavType.StringType
                },
                navArgument("idUsuario") {
                    type = NavType.IntType
                }
            )
        ) { tela ->
            val nomeCategoria = tela.arguments?.getString("nomeCategoria") ?: ""
            val idUsuario = tela.arguments?.getInt("idUsuario") ?: 0

            DetalheCategoriaFixaScreen(
                controleNavegacao = controleNavegacao,
                nomeCategoria = nomeCategoria,
                idUsuario = idUsuario
            )
        }

        // Rota para os detalhes de uma meta (Corrigida e limpa)
        composable(
            route = "detalhemeta/{idMeta}/{nomeMeta}/{idUsuario}",
            arguments = listOf(
                navArgument("idMeta") { type = NavType.IntType },
                navArgument("nomeMeta") { type = NavType.StringType },
                navArgument("idUsuario") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val idMeta = backStackEntry.arguments?.getInt("idMeta") ?: 0
            val nomeLogico = backStackEntry.arguments?.getString("nomeMeta")
            val nomeMeta = Uri.decode(nomeLogico) ?: "Meta"
            val idUsuario = backStackEntry.arguments?.getInt("idUsuario") ?: 0

            DetalheMetaScreen(
                controleNavegacao = controleNavegacao,
                idMeta = idMeta,
                nomeMeta = nomeMeta,
                idUsuario = idUsuario
            )
        }

        // Rota para uma meta fixa (Corrigida para chamar a tela DetalheMetaFixaScreen)
        composable(
            route = "detalhesmetafixa/{nomeMeta}/{idUsuario}",
            arguments = listOf(
                navArgument("nomeMeta") {
                    type = NavType.StringType
                },
                navArgument("idUsuario") {
                    type = NavType.IntType
                }
            )
        ) { tela ->
            val nomeMetaLogico = tela.arguments?.getString("nomeMeta")
            val nomeMeta = Uri.decode(nomeMetaLogico) ?: ""
            val idUsuario = tela.arguments?.getInt("idUsuario") ?: 0

            // Ajustado para apontar para a tela fixa correta que você enviou no primeiro prompt
            DetalheMetaFixaScreen(
                controleNavegacao = controleNavegacao,
                nomeMeta = nomeMeta,
                idUsuario = idUsuario
            )
        }
    }
}