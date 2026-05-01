package com.example.mymoneyandroid.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Cores usadas na tela
private val verdePrincipal = Color(0xFF2E7D32)
private val fundoCard = Color(0xFF1C1C1E)
private val fundoCampos = Color(0xFF2C2C2E)
private val bordaCampos = Color(0xFF3A3A3C)
private val corTexto = Color(0xFFFFFFFF)
private val fundoBotao = Color(0xFF34C759)


@Composable
fun LoginScreen(
    realizarLogin: (name: String, email: String, senha: String) -> Unit = { _, _, _ -> },
    irParaPrincipal: () -> Unit = {}
) {

    // Váriaveis para guardar os valores de cada campo
    var nome  by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha   by remember { mutableStateOf("") }


    // Cria a tela, que é ocupada inteiramente pela cor verde
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(verdePrincipal)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
    ) {

        // Header
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "MyMoney",
            color = corTexto,
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Login",
            color = corTexto.copy(alpha = 0.85f),
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Formulário
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = fundoCard),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // Chamando LoginField para criar os campos

                LoginField(
                    label = "Nome",
                    value = nome,
                    onValueChange = { nome = it },
                    keyboardType = KeyboardType.Text
                )

                LoginField(
                    label = "Email",
                    value = email,
                    onValueChange = { email = it },
                    keyboardType = KeyboardType.Email
                )

                LoginField(
                    label = "Senha",
                    value = senha,
                    onValueChange = { senha = it },
                    keyboardType = KeyboardType.Password
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Botão de fazer login
                Button(
                    onClick = { realizarLogin(nome, email, senha) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = fundoBotao)
                ) {
                    Text(
                        text = "Realizar login",
                        color = corTexto,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }

    }
}

// Função usada para criar um campo de login (input e label)
@Composable
private fun LoginField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text
) {

    // Organiza os campos na vertical
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {

        // Texto com o nome do campo (label)
        Text(
            text = label,
            color = corTexto,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )

        // Campo de entrada (input)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(text = " ")
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),

            // Cores usadas em cada campo
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor   = fundoCampos,
                unfocusedContainerColor = fundoCampos,
                focusedBorderColor      = fundoBotao,
                unfocusedBorderColor    = bordaCampos,
                focusedTextColor        = corTexto,
                unfocusedTextColor      = corTexto,
                cursorColor             = fundoBotao
            ),

            // Tipo do teclado
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
        )
    }
}


@Composable
private fun TopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = "⊞",
            color = corTexto,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "≡",
            color = corTexto,
            fontSize = 28.sp
        )
    }
}


@Preview(showBackground = true, widthDp = 360, heightDp = 720)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}