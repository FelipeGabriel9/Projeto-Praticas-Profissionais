package com.example.mymoneyandroid.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


private val ColorBackground = Color(0xFF2E7D32)   // Verde

@Composable
fun DetalheCategoriaScreen(navController: NavController, categoriaNome: String?) {
    // Se o nome vier nulo, tratamos para não quebrar
    val nome = categoriaNome ?: "Categoria"

    AbaMenu(tituloDaPagina = nome, navController = navController) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ColorBackground) // O mesmo verde das outras telas
                .padding(paddingValues)
                .padding(20.dp)
        ) {
            // --- GRÁFICO DE GASTOS (Visual) ---
            Text("Resumo de Gastos", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF1C1C1E), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                Text("Gasto Atual: R$ 450,00", color = Color.White, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))

                // Barra de Progresso (Gráfico de Barra)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp)
                        .background(Color.Gray, RoundedCornerShape(6.dp))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.45f) // 45% de gastos por exemplo
                            .fillMaxHeight()
                            .background(Color(0xFF81C784), RoundedCornerShape(6.dp))
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("Meta Mensal: R$ 1.000,00", color = Color.LightGray, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(32.dp))

//            // --- LISTA DE TRANSAÇÕES ---
//            Text("Histórico", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
//            Spacer(modifier = Modifier.height(16.dp))
//
//            val transacoes = listOf("Mercado", "Farmácia", "Uber", "Lanche")
//
//            LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
//                items(transacoes) { item ->
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .background(Color(0xFF3A3A3C), RoundedCornerShape(12.dp))
//                            .padding(16.dp),
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Text(item, color = Color.White)
//                        Text("- R$ 45,00", color = Color(0xFFF44336)) // Vermelho para gastos
//                    }
//                }
//            }
        }
    }
}