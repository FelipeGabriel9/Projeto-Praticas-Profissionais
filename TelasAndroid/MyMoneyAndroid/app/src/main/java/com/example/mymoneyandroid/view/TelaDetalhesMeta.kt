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
import androidx.lifecycle.viewmodel.compose.viewModel // Importante para o funcionamento do viewModel()
import androidx.navigation.NavController
import com.example.mymoneyandroid.viewmodel.MetaViewModel

// Cores usadas na tela
private val CorFundoEscuro = Color(0xFF0F0F0F)
private val CorCardInterno = Color(0xFF1C1C1E)
private val CorTextoPrincipal = Color(0xFFFFFFFF)
private val CorTextoSecundario = Color(0xFF8E8E93)
private val CorVerdeDestaque = Color(0xFF2E7D32)
private val CorBarraProgresso = Color(0xFF34C759)
private val CorFundoBarra = Color(0xFF2C2C2E)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalheMetaScreen(
    controleNavegacao: NavController,
    idMeta: Int,          // CORREÇÃO 1: Adicionado o idMeta que estava faltando aqui!
    nomeMeta: String?,
    idUsuario: Int,
    viewModel: MetaViewModel = viewModel() // CORREÇÃO 2: Injetando o ViewModel corretamente por padrão
) {
    // Estados para os valores
    var valorObjetivo by remember { mutableStateOf("") }
    var valorGuardadoAgora by remember { mutableStateOf("") }
    var totalAcumulado by remember { mutableStateOf(0.0) }

    // Cálculo do progresso para a barra (0.0 a 1.0)
    val objetivoTotal = valorObjetivo.replace(",", ".").toDoubleOrNull() ?: 1.0
    val progresso = (totalAcumulado / objetivoTotal).coerceIn(0.0, 1.0).toFloat()

    MenuScreen(tituloDaPagina = nomeMeta ?: "Meta", controleNagegacao = controleNavegacao, idUsuario = idUsuario) { padding ->
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

            // Campo para definir a meta final
            OutlinedTextField(
                value = valorObjetivo,
                onValueChange = { valorObjetivo = it },
                label = { Text("Quanto quer poupar no total? (R$)") },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = CorTextoPrincipal,
                    unfocusedTextColor = CorTextoPrincipal,
                    focusedBorderColor = CorVerdeDestaque,
                    focusedLabelColor = CorVerdeDestaque
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            // BARRA DE PROGRESSO
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

            // Card com o total já guardado
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CorCardInterno)
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

            // Campo para adicionar valor agora
            OutlinedTextField(
                value = valorGuardadoAgora,
                onValueChange = { valorGuardadoAgora = it },
                label = { Text("Adicionar valor economizado (R$)") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                    val valor = valorGuardadoAgora.replace(",", ".").toDoubleOrNull() ?: 0.0
                    totalAcumulado += valor
                    valorGuardadoAgora = ""
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CorVerdeDestaque),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Guardar",
                    fontWeight = FontWeight.Bold,
                    color = CorTextoPrincipal
                )
            }

            // Botão de excluir meta
            Button(
                onClick = {
                    // Agora o viewModel e o idMeta estão acessíveis e vão funcionar de primeira!
                    viewModel.excluirMeta(idMeta = idMeta, idUsuario = idUsuario)
                    controleNavegacao.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(top = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
            ) {
                Text("EXCLUIR META", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    }
}