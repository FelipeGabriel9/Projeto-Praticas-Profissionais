package com.example.mymoneyandroid.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

private val CorFundoEscuro = Color(0xFF0F0F0F)
private val CorCardInterno = Color(0xFF1C1C1E)
private val CorTextoPrincipal = Color(0xFFFFFFFF)
private val CorTextoSecundario = Color(0xFF8E8E93)
private val CorVerdeDestaque = Color(0xFF2E7D32)
private val CorBarraProgresso = Color(0xFF34C759)
private val CorFundoBarra = Color(0xFF2C2C2E)
@Composable
fun DetalheMetaFixaScreen(
    controleNavegacao: NavController,
    nomeMeta: String,
    idUsuario: Int
) {

    var valorObjetivo by remember { mutableStateOf("") }
    var valorGuardadoAgora by remember { mutableStateOf("") }
    var totalAcumulado by remember { mutableStateOf(0.0) }

    val objetivo = valorObjetivo.replace(",", ".").toDoubleOrNull() ?: 0.0

    val progresso = if (objetivo > 0) {
        (totalAcumulado / objetivo).coerceIn(0.0, 1.0).toFloat()
    } else {
        0f
    }

    MenuScreen(
        tituloDaPagina = nomeMeta,
        controleNagegacao = controleNavegacao,
        idUsuario = idUsuario
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(CorFundoEscuro)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Meu objetivo para: $nomeMeta",
                color = CorTextoSecundario
            )

            OutlinedTextField(
                value = valorObjetivo,
                onValueChange = { valorObjetivo = it },
                label = {
                    Text("Quanto quer poupar no total? (R$)")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = CorTextoPrincipal,
                    unfocusedTextColor = CorTextoPrincipal,
                    focusedBorderColor = CorVerdeDestaque,
                    focusedLabelColor = CorVerdeDestaque
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Progresso: ${(progresso * 100).toInt()}%",
                color = CorTextoPrincipal,
                fontWeight = FontWeight.Bold
            )

            LinearProgressIndicator(
                progress = { progresso },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .padding(vertical = 8.dp),
                color = CorBarraProgresso,
                trackColor = CorFundoBarra
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = CorCardInterno
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Total acumulado",
                        color = CorTextoSecundario,
                        fontSize = 14.sp
                    )

                    Text(
                        text = "R$ ${String.format("%.2f", totalAcumulado)}",
                        color = CorTextoPrincipal,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = valorGuardadoAgora,
                onValueChange = { valorGuardadoAgora = it },
                label = {
                    Text("Adicionar valor economizado (R$)")
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = CorTextoPrincipal,
                    unfocusedTextColor = CorTextoPrincipal,
                    focusedBorderColor = CorVerdeDestaque,
                    focusedLabelColor = CorVerdeDestaque
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val valor = valorGuardadoAgora
                        .replace(",", ".")
                        .toDoubleOrNull() ?: 0.0

                    totalAcumulado += valor
                    valorGuardadoAgora = ""
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CorVerdeDestaque
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Guardar",
                    fontWeight = FontWeight.Bold,
                    color = CorTextoPrincipal
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    controleNavegacao.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFD32F2F)
                )
            ) {
                Text(
                    text = "EXCLUIR META",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}