package com.example.mymoneyandroid.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mymoneyandroid.viewmodel.MensagemViewModel

// Cores usadas na tela
private val CorFundoTela = Color(0xFF0F0F0F)
private val CorTexto = Color(0xFFFFFFFF)
private val CorBotao = Color(0xFF1B823E)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MensagemScreen(
    controleNavegacao: NavController,
    viewModel: MensagemViewModel = viewModel()
) {
    MenuScreen(tituloDaPagina = "", controleNagegacao = controleNavegacao) { valoresPadding ->

        // Variáveis que vão guardar os valores lidos
        var assunto by remember { mutableStateOf("") }
        var mensagem by remember { mutableStateOf("") }

        // Cria a tela, que é ocupada inteiramente pela cor preta
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(valoresPadding)
                .background(CorFundoTela)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Text(
                text = "Converse conosco",
                color = CorTexto,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Campo onde o usuário vai digitar o assunto principal da mensagem
            OutlinedTextField(
                value = assunto,
                onValueChange = { assunto = it },
                label = { Text("Assunto") },
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CorBotao,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = CorBotao,
                    cursorColor = CorBotao,
                    focusedTextColor = CorTexto,
                    unfocusedTextColor = CorTexto
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo onde o usuário digita sua mensagem
            OutlinedTextField(
                value = mensagem,
                onValueChange = { mensagem = it },
                label = { Text("Sua mensagem") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = CorBotao,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = CorBotao,
                    cursorColor = CorBotao,
                    focusedTextColor = CorTexto,
                    unfocusedTextColor = CorTexto
                ),
                maxLines = 10
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Botão de enviar mensagem
            Button(
                onClick = {
                    viewModel.realizarEnvioMensagem(assunto, mensagem)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CorBotao),
                shape = RoundedCornerShape(8.dp)
            ) {
                // Se estiver carregando, mostra uma bolinha girando. Se não, mostra o texto.
                if (viewModel.carregando) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = "Enviar",
                        color = CorTexto,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }

            // Se houver uma mensagem de erro, ela aparece em vermelho (TIRAR DEPOIS DOS TESTES)
            viewModel.mensagemErro?.let { erro ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = erro,
                    color = Color(0xFFFF453A), // Cor de perigo (vermelho)
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
