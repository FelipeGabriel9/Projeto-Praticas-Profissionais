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
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mymoneyandroid.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mymoneyandroid.viewmodel.CadastroViewModel

// Cores usadas na tela
private val verdePrincipal = Color(0xFF2E7D32)
private val fundoCard = Color(0xFF1C1C1E)
private val fundoCampos = Color(0xFF2C2C2E)
private val bordaCampos = Color(0xFF3A3A3C)
private val corTexto = Color(0xFFFFFFFF)
private val fundoBotao = Color(0xFF34C759)
private val verdeGradiente = Color(0xFF1A2E1A)


// Criando a função que permite um usuário se cadastrar no sistema
@Composable
fun CadastroScreen(
    controleNavegacao: NavHostController,
    viewModel: CadastroViewModel = viewModel(),
    irParaLogin: () -> Unit = {}
) {
    // Variáveis para guardar os valores de cada campo
    var nome  by remember { mutableStateOf("") }
    var cpf   by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }

    // Criando a tela
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = linearGradient(
                colors = listOf(verdePrincipal, verdeGradiente)
                )
            )
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
                    .size(120.dp)
                    .clip(RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_my_money),
                    contentDescription = "Logo MyMoney",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
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
                    textoLabel = "Nome",
                    valor = nome,
                    mudarValor = { nome = it },
                    tipoTeclado = KeyboardType.Text
                )

                LoginField(
                    textoLabel = "CPF",
                    valor = cpf,
                    mudarValor = { cpf = it },
                    tipoTeclado = KeyboardType.Number
                )

                LoginField(
                    textoLabel = "Email",
                    valor = email,
                    mudarValor = { email = it },
                    tipoTeclado = KeyboardType.Email
                )

                LoginField(
                    textoLabel = "Senha",
                    valor = senha,
                    mudarValor = { senha = it },
                    tipoTeclado = KeyboardType.Password)


                Spacer(modifier = Modifier.height(8.dp))

                // Botão de criar conta
                Button(
                    onClick = {
                        viewModel.realizarCadastro(nome, cpf, email, senha) {
                            controleNavegacao.navigate("telaPrincipal")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = fundoBotao),
                    enabled = !viewModel.carregando
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
                            text = "Criar conta",
                            color = corTexto,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }

                // Se houver uma mensagem de erro, ela aparece em vermelho
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

        // Cria um espaço entre o card e o rodapé da tela
        Spacer(modifier = Modifier.weight(1f))

        // Rodapé (leva para a tela de login)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Já tem uma conta? ",
                color = corTexto
            )
            TextButton(onClick = irParaLogin) {
                Text(
                    text = "Faça login",
                    color = corTexto
                )
            }
        }
    }
}


// Função usada para criar um campo de login (input e label)
@Composable
private fun LoginField(
    textoLabel: String,
    valor: String,
    mudarValor: (String) -> Unit,
    tipoTeclado: KeyboardType = KeyboardType.Text
) {
    // Organiza os campos na vertical
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {

        // Texto com o nome do campo (label)
        Text(
            text = textoLabel,
            color = corTexto,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )

        // Campo de entrada (input)
        OutlinedTextField(
            value = valor,
            onValueChange = mudarValor,
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
            keyboardOptions = KeyboardOptions(keyboardType = tipoTeclado)
        )
    }
}


