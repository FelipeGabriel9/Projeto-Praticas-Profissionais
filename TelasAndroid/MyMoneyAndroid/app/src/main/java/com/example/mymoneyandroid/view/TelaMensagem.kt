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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// Cores seguindo o padrão das telas anteriores
private val CorFundoTela = Color(0xFF0F0F0F)
private val CorTexto      = Color(0xFFFFFFFF)
private val CorBotao      = Color(0xFF1B823E)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaMensagem(navController: NavController) {
    AbaMenu(tituloDaPagina = "", controleNagegacao = navController) { valoresPadding ->

        var assunto by remember { mutableStateOf("") }
        var mensagem by remember { mutableStateOf("") }
        var usarEmailCadastrado by remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(valoresPadding)
                .background(CorFundoTela)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Enviar Mensagem",
                color = CorTexto,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --- Campo: Assunto ---
            OutlinedTextField(
                value = assunto,
                onValueChange = { assunto = it },
                label = { Text("Assunto") },
                modifier = Modifier.fillMaxWidth(),
                // CORREÇÃO AQUI: Usando OutlinedTextFieldDefaults.colors
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

            // --- Checkbox: Usar E-mail Cadastrado ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = usarEmailCadastrado,
                    onCheckedChange = { usarEmailCadastrado = it },
                    colors = CheckboxDefaults.colors(checkedColor = CorBotao)
                )
                Text(
                    text = "Enviar do meu e-mail cadastrado",
                    color = CorTexto,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // --- Campo: Mensagem ---
            OutlinedTextField(
                value = mensagem,
                onValueChange = { mensagem = it },
                label = { Text("Sua mensagem") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                colors = OutlinedTextFieldDefaults.colors( // CORREÇÃO AQUI TAMBÉM
                    focusedBorderColor = CorBotao,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = CorBotao,
                    cursorColor = CorBotao,
                    focusedTextColor = CorTexto,
                    unfocusedTextColor = CorTexto
                ),
                maxLines = 10 // Melhor usar maxLines do que multiline puro
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { /* Lógica de envio */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = CorBotao),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "ENVIAR", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}
