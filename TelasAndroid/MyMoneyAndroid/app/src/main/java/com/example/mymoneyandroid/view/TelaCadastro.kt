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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mymoneyandroid.R

// Cores usadas na tela
private val verdePrincipal = Color(0xFF2E7D32)
private val fundoCard = Color(0xFF1C1C1E)
private val fundoCampos = Color(0xFF2C2C2E)
private val bordaCampos   = Color(0xFF3A3A3C)
private val corTexto     = Color(0xFFFFFFFF)
private val fundoBotao   = Color(0xFF34C759)


@Composable
fun CadastroScreen(
    realizarCadastro: (name: String, cpf: String, email: String, senha: String) -> Unit = { _, _, _, _ -> },
    irParaLogin: () -> Unit = {}
) {
    // Variáveis para guardar os valores de cada campo
    var name  by remember { mutableStateOf("") }
    var cpf   by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

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
            Surface(
                modifier = Modifier.size(60.dp),
                color = corTexto.copy(alpha = 0.2f),
                shape = RoundedCornerShape(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_my_money), // Nome do arquivo no drawable
                    contentDescription = "Logo MyMoney",
                    modifier = Modifier
                        .size(2000.dp) // Ajuste o tamanho conforme sua logo
                        .padding(bottom = 8.dp)
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
                text = "Crie sua conta em segundos",
                color = corTexto.copy(alpha = 0.7f),
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Card com cantos mais suaves
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            shape = RoundedCornerShape(28.dp), // Cantos mais arredondados = mais moderno
            colors = CardDefaults.cardColors(containerColor = fundoCard),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp) // Flat design é tendência
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                LoginField("Nome Completo", name, { name = it }, KeyboardType.Text)
                LoginField("CPF", cpf, { cpf = it }, KeyboardType.Number)
                LoginField("E-mail", email, { email = it }, KeyboardType.Email)
                LoginField("Senha", senha, { senha = it }, KeyboardType.Password)

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = { realizarCadastro(name, cpf, email, senha) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp), // Botão mais robusto
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = fundoBotao) // Use o verde de destaque no botão
                ) {
                    Text(
                        text = "Realizar cadastro",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f)) // Empurra o rodapé para baixo

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Já tem uma conta? ", color = corTexto)
            TextButton(onClick = irParaLogin) {
                Text(text = "Faça login", color = corTexto, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun LoginField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            text = label,
            color = corTexto,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(text = " ")
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor   = fundoCampos,
                unfocusedContainerColor = fundoCampos,
                focusedBorderColor      = fundoBotao,
                unfocusedBorderColor    = bordaCampos,
                focusedTextColor        = corTexto,
                unfocusedTextColor      = corTexto,
                cursorColor             = fundoBotao
            ),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
        )
    }
}



@Preview(showBackground = true, widthDp = 360, heightDp = 720)
@Composable
fun CadastroScreenPreview() {
    CadastroScreen()
}