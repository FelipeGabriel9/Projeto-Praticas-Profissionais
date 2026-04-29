package com.example.mymoneyandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mymoneyandroid.ui.theme.MyMoneyAndroidTheme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mymoneyandroid.navigation.AppNavigation
import com.example.mymoneyandroid.view.CategoriaScreen

// Cria a classe principal (a primeira que o Android procura para abrir o app)
class MainActivity : ComponentActivity() {

    // Função que é executada no exato momento que o app é aberto
    override fun onCreate(savedInstanceState: Bundle?) {

        // Chama a preparação padrão do sistema Android
        super.onCreate(savedInstanceState)

        // Inicia o ambiente Jetpack Compose (tudo aqui dentro é interface visual)
        setContent {

            // Aplica o tema visual (cores, modos claro/escuro) do projeto
            MyMoneyAndroidTheme() {

                // Cria um contêiner base (uma folha de papel em branco)
                Surface(
                    // Diz para essa folha ocupar a tela toda
                    modifier = Modifier.fillMaxSize(),
                    // Cor de fundo padrão do tema
                    color = MaterialTheme.colorScheme.background
                ) {

                    // Chama a nossa função que contém o mapa. Ela cuidará de exibir a Tela 1 de início.
                    AppNavigation()

                }
            }
        }
    }

    @Composable
    fun MinhaApp() {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "categorias") {
            composable("categorias") {
                CategoriaScreen (navController)
            }
            composable("perfil") {
                // TelaPerfilScreen (navController)
            }
            composable("mensagem") {
                // TelaMensagemScreen (navController)
            }
            composable("principal") {
                // TelaPrincipalScreen (navController)
            }
        }
    }
}