package com.example.mymoneyandroid.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


private val corFundo = Color(0xFF2E7D32)   // Verde
private val branco = Color(0XFFFFFFFF)

@Composable
fun DetalheCategoriaScreen(
    controleNavegacao: NavController,
    nomeCategoria: String?,
    idUsuario: Int
) {
    // Estados para controlar o que acontece na tela
    var valorDigitado by remember { mutableStateOf("") }
    var totalAcumulado by remember { mutableStateOf(0.0) }

    MenuScreen(tituloDaPagina = nomeCategoria ?: "Detalhes", controleNagegacao = controleNavegacao, idUsuario = idUsuario) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFF0F0F0F))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Total gasto em $nomeCategoria", color = Color.Gray)

            // Exibe o total acumulado formatado
            Text(
                text = "R$ ${String.format("%.2f", totalAcumulado)}",
                color = branco,
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Campo de texto para digitar o valor
            OutlinedTextField(
                value = valorDigitado,
                onValueChange = { valorDigitado = it },
                label = { Text("Novo gasto (R$)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = branco,
                    unfocusedTextColor = branco,
                    focusedBorderColor = corFundo
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
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = corFundo)
            ) {
                Text("ADICIONAR DESPESA")
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Histórico
            Text(
                text = "Histórico",
                color = branco,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start))

            Spacer(modifier = Modifier.height(12.dp))

            // Criando lista (enquanto não usa banco de dados)
            val transacoes = listOf("Gasto recente")
            transacoes.forEach { _ ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF1C1C1E), RoundedCornerShape(12.dp))
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(nomeCategoria ?: "Gasto", color = Color.White)
                    Text("- R$ ${String.format("%.2f", totalAcumulado)}", color = Color(0xFFF44336))
                }
            }
        }
    }
}