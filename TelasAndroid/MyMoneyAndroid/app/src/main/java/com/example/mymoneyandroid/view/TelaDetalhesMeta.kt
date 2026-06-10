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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mymoneyandroid.model.Meta
import com.example.mymoneyandroid.viewmodel.MetaViewModel

private val CorFundoEscuro = Color(0xFF0F0F0F)
private val CorCardInterno = Color(0xFF1C1C1E)
private val CorTextoPrincipal = Color(0xFFFFFFFF)
private val CorTextoSecundario = Color(0xFF8E8E93)
private val CorVerdeDestaque = Color(0xFF2E7D32)
private val CorBarraProgresso = Color(0xFF34C759)
private val CorFundoBarra = Color(0xFF2C2C2E)
private val CorVermelhoAlerta = Color(0xFFD32F2F) // Adicionado para padronizar o botão de excluir

@Composable
fun DetalheMetaScreen(
    controleNavegacao: NavController,
    idMeta: Int,
    nomeMeta: String,
    idUsuario: Int,
    viewModel: MetaViewModel = viewModel()
) {
    val listaMetas by viewModel.metas.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.buscarMetas(idUsuario)
    }

    val meta = listaMetas.find { it.idMeta == idMeta }

    var valorObjetivoInput by remember { mutableStateOf("") }
    var valorGuardadoAgora by remember { mutableStateOf("") }

    LaunchedEffect(meta) {
        meta?.let {
            if (valorObjetivoInput.isEmpty() && it.valorObjetivo > 0) {
                valorObjetivoInput = it.valorObjetivo.toString()
            }
        }
    }

    // Lendo DIRETO do banco de dados (se for nulo, mostra 0.0)
    val totalAcumulado = meta?.valorAtual ?: 0.0
    val objetivoTotal = valorObjetivoInput.replace(",", ".").toDoubleOrNull() ?: 0.0

    val progresso = if (objetivoTotal > 0) {
        (totalAcumulado / objetivoTotal).coerceIn(0.0, 1.0).toFloat()
    } else {
        0f
    }

    val tituloTela = meta?.nomeMeta ?: nomeMeta

    MenuScreen(tituloDaPagina = tituloTela, controleNagegacao = controleNavegacao, idUsuario = idUsuario) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(CorFundoEscuro)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Meu objetivo para: $tituloTela",
                color = CorTextoSecundario
            )

            OutlinedTextField(
                value = valorObjetivoInput,
                onValueChange = { valorObjetivoInput = it },
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

            Button(
                onClick = {
                    meta?.let {
                        val metaAtualizada = it.copy(valorObjetivo = objetivoTotal)
                        viewModel.atualizarMeta(metaAtualizada)
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CorVerdeDestaque),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Salvar Objetivo", fontWeight = FontWeight.Bold, color = CorTextoPrincipal)
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Progresso: ${(progresso * 100).toInt()}%",
                color = CorTextoPrincipal,
                fontWeight = FontWeight.Bold
            )

            LinearProgressIndicator(
                progress = { progresso },
                modifier = Modifier.fillMaxWidth().height(12.dp).padding(vertical = 8.dp),
                color = CorBarraProgresso,
                trackColor = CorFundoBarra
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = CorCardInterno)
            ) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Total acumulado", color = CorTextoSecundario, fontSize = 14.sp)
                    Text(
                        // O valor aqui agora é 100% o que está no BD
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
                    val valorDigitado = valorGuardadoAgora.replace(",", ".").toDoubleOrNull() ?: 0.0
                    if (valorDigitado > 0) {
                        meta?.let {
                            // Somamos o valor e mandamos para o ViewModel
                            val metaAtualizada = it.copy(valorAtual = it.valorAtual + valorDigitado)
                            viewModel.atualizarMeta(metaAtualizada)
                        }
                        valorGuardadoAgora = ""
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CorVerdeDestaque),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Guardar", fontWeight = FontWeight.Bold, color = CorTextoPrincipal)
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    meta?.let {
                        viewModel.excluirMeta(it.idMeta)
                        controleNavegacao.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CorVermelhoAlerta)
            ) {
                Text("EXCLUIR META", color = CorTextoPrincipal, fontWeight = FontWeight.Bold)
            }
        }
    }
}