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
import com.example.mymoneyandroid.viewmodel.CategoriaViewModel

private val corFundo = Color(0xFF2E7D32)
private val branco = Color(0XFFFFFFFF)

@Composable
fun DetalheCategoriaScreen(
    controleNavegacao: NavController,
    idCategoria: Int,
    idUsuario: Int,
    viewModel: CategoriaViewModel = viewModel()
) {

    val listaCategorias by viewModel.categorias.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.buscarCategorias(idUsuario)
    }

    val categoria = listaCategorias.find {
        it.idCategoria == idCategoria
    }

    var valorDigitado by remember {
        mutableStateOf("")
    }

    val totalAcumulado = categoria?.valorDespesa ?: 0.0

    MenuScreen(
        tituloDaPagina = categoria?.nomeCategoria ?: "Categoria",
        controleNagegacao = controleNavegacao,
        idUsuario = idUsuario
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFF0F0F0F))
                .padding(24.dp),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                "Total gasto em ${categoria?.nomeCategoria ?: "Categoria"}",
                color = Color.Gray
            )

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

                onValueChange = {
                    valorDigitado = it
                },

                label = {
                    Text("Novo gasto (R$)")
                },

                modifier = Modifier.fillMaxWidth(),

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),

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

                    val valor =
                        valorDigitado
                            .replace(",", ".")
                            .toDoubleOrNull() ?: 0.0

                    val novoValor =
                        totalAcumulado + valor

                    categoria?.let {

                        viewModel.atualizarValorCategoria(
                            categoria = it,
                            novoValor = novoValor,
                            idUsuario = idUsuario
                        )
                    }

                    valorDigitado = ""
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),

                shape = RoundedCornerShape(12.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = corFundo
                )
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
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Criando lista (enquanto não usa banco de dados)
            val transacoes = listOf("Gasto recente")

            transacoes.forEach {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Color(0xFF1C1C1E),
                            RoundedCornerShape(12.dp)
                        )
                        .padding(16.dp),

                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        categoria?.nomeCategoria ?: "Gasto",
                        color = Color.White
                    )

                    Text(
                        "- R$ ${String.format("%.2f", totalAcumulado)}",
                        color = Color(0xFFF44336)
                    )
                }
            }
        }
    }
}