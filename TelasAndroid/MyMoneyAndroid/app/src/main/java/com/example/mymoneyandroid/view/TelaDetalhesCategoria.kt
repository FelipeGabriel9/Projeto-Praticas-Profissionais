package com.example.mymoneyandroid.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


private val ColorBackground = Color(0xFF2E7D32)   // Verde

@Composable
fun DetalheCategoriaScreen(navController: NavController, categoriaNome: String?) {
    // Estados para controlar o que acontece na tela
    var valorDigitado by remember { mutableStateOf("") }
    var totalAcumulado by remember { mutableStateOf(0.0) }

    MenuScreen(tituloDaPagina = categoriaNome ?: "Detalhes", controleNagegacao = navController) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFF0F0F0F))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Total gasto em $categoriaNome", color = Color.Gray)

            // Exibe o total acumulado formatado
            Text(
                text = "R$ ${String.format("%.2f", totalAcumulado)}",
                color = Color.White,
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Campo de texto para digitar o valor
            OutlinedTextField(
                value = valorDigitado,
                onValueChange = { valorDigitado = it },
                label = { Text("Valor da despesa") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedBorderColor = Color(0xFF2E7D32)
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            // BOTÃO QUE REALMENTE ADICIONA
            Button(
                onClick = {
                    // Tenta transformar o texto em número, se der erro vira 0.0
                    val valor = valorDigitado.replace(",", ".").toDoubleOrNull() ?: 0.0
                    totalAcumulado += valor // SOMA AO TOTAL
                    valorDigitado = "" // LIMPA O CAMPO
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
            ) {
                Text("ADICIONAR DESPESA")
            }
        }
    }
}

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