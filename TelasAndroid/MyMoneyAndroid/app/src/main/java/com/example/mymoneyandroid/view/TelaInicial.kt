package com.example.mymoneyandroid.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mymoneyandroid.R

// Criando a tela inicial, a primeira que o usuário vê ao abrir o App
@Composable
fun InicialScreen(controleNavegacao: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF2E7D32)), // Cor de fundo verde
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(24.dp)
        ) {
            // Logo do aplicativo
            Image(
                painter = painterResource(id = R.drawable.logo_my_money),
                contentDescription = "Logo MyMoney",
                modifier = Modifier.size(150.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Título do App
            Text(
                text = "MyMoney",
                color = Color.White,
                fontSize = 40.sp,
                fontWeight = FontWeight.ExtraBold
            )

            // Slogan
            Text(
                text = "Seu controle financeiro na palma da mão",
                color = Color.White.copy(alpha = 0.8f),
                fontSize = 16.sp,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Botão para ir para a tela de Login
            Button(
                onClick = { controleNavegacao.navigate("login") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                shape = RoundedCornerShape(28.dp)
            ) {
                Text(
                    text = "COMEÇAR",
                    color = Color(0xFF2E7D32),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botão para ir para a tela de Cadastro
            OutlinedButton(
                onClick = { controleNavegacao.navigate("cadastro") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                border = androidx.compose.foundation.BorderStroke(2.dp, Color.White),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
            ) {
                Text(
                    text = "CRIAR CONTA",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}