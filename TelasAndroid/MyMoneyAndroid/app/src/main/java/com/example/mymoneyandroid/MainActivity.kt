package com.example.mymoneyandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.mymoneyandroid.navigation.AppNavigation

// Cria a classe principal (a primeira que o Android procura para abrir o app)
class MainActivity : ComponentActivity() {

    // Função que é executada no exato momento que o app é aberto
    override fun onCreate(savedInstanceState: Bundle?) {

        // Chama a preparação padrão do sistema Android
        super.onCreate(savedInstanceState)

        // Inicia o ambiente Jetpack Compose (tudo aqui dentro é interface visual)
        setContent {
            // Cria um contêiner base
            Surface(
                // O contêiner passa a ocupar a tela toda
                modifier = Modifier.fillMaxSize(),
                // Cor de fundo padrão
                color = MaterialTheme.colorScheme.background
            ) {

                // Chama o appNavigation, onde temos as interações entre telas
                AppNavigation()

            }
        }
    }

}