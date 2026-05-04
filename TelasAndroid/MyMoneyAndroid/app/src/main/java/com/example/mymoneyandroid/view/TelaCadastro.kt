package com.example.mymoneyandroid.view

import androidx.compose.foundation.Image
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.mymoneyandroid.R

// Cores usadas na tela
private val verdePrincipal = Color(0xFF2E7D32)
private val fundoCard = Color(0xFF1C1C1E)
private val fundoCampos = Color(0xFF2C2C2E)
private val bordaCampos = Color(0xFF3A3A3C)
private val corTexto = Color(0xFFFFFFFF)
private val fundoBotao = Color(0xFF34C759)


@Composable
fun CadastroScreen(
    navController: NavHostController,
    realizarCadastro: (name: String, cpf: String, email: String, senha: String) -> Unit = { _, _, _, _ -> },
    irParaLogin: () -> Unit = {}
) {
    // Variáveis para guardar os valores de cada campo
    var nome  by remember { mutableStateOf("") }
    var cpf   by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var arredondarPontas = androidx.compose.ui.layout.ContentScale.Crop

    // Criando a tela
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(verdePrincipal)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
    ) {

        // Header
        Spacer(modifier = Modifier.height(60.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagem da logo
            Box(
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_my_money),
                    contentDescription = "Logo MyMoney",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = arredondarPontas
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "MyMoney",
                color = corTexto,
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = (-1).sp
            )
            Text(
                text = "Crie sua conta",
                color = corTexto,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Formulário
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = fundoCard)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Chamando LoginField para criar os campos

                LoginField(
                    label = "Nome",
                    value = nome,
                    onValueChange = { nome = it },
                    keyboardType = KeyboardType.Text
                )

                LoginField(
                    label = "CPF",
                    value = cpf,
                    onValueChange = { cpf = it },
                    keyboardType = KeyboardType.Number
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
                    keyboardType = KeyboardType.Password)


                Spacer(modifier = Modifier.height(8.dp))

                // Botão de criar conta
                Button(
                    onClick = { realizarCadastro(nome, cpf, email, senha)
                            navController.navigate("telaPrincipal")},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = fundoBotao)
                ) {
                    Text(
                        text = "Criar conta",
                        color = corTexto,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }

        // Cria um espaço entre o card e o rodapé da tela
        Spacer(modifier = Modifier.weight(1f))

        // Rodapé (leva para a tela de login)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Já tem uma conta? ", color = corTexto)
            TextButton(onClick = irParaLogin) {
                Text(text = "Faça login", color = corTexto)
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
            placeholder = { Text(text = " ") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),

            // Cores usadas em cada campo
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = fundoCampos,
                unfocusedContainerColor = fundoCampos,
                focusedBorderColor = fundoBotao,
                unfocusedBorderColor = bordaCampos,
                focusedTextColor = corTexto,
                unfocusedTextColor = corTexto,
                cursorColor = fundoBotao
            ),

            // Define o tipo de teclado que vai abrir (text, email, etc)
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
        )
    }
}


