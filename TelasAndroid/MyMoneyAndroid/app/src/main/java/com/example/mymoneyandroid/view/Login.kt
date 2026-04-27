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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val GreenPrimary  = Color(0xFF2E7D32)   // verde escuro
private val DarkCard      = Color(0xFF1C1C1E)   // card escuro
private val FieldBg       = Color(0xFF2C2C2E)   // fundo dos campos
private val FieldBorder   = Color(0xFF3A3A3C)   // borda dos campos
private val TextHint      = Color(0xFF8E8E93)   // placeholder
private val TextWhite     = Color(0xFFFFFFFF)
private val TextLabel     = Color(0xFFD1D1D6)
private val AccentGreen   = Color(0xFF34C759)


@Composable
fun LoginScreen(
    onRealizarLogin: (name: String, email: String, senha: String) -> Unit = { _, _, _ -> },
    onLoginClick: () -> Unit = {}
) {
    var name  by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha   by remember { mutableStateOf("") }


    Column(
        modifier = Modifier.fillMaxSize().background(GreenPrimary)
    ) {

        TopBar()


        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "MyMoney",
            color = TextWhite,
            fontSize = 36.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Login",
            color = TextWhite.copy(alpha = 0.85f),
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))


        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = DarkCard),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Name
                LoginField(
                    label = "Name",
                    value = name,
                    onValueChange = { name = it },
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


                Button(
                    onClick = { onRealizarLogin(name, email, senha) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = TextWhite)
                ) {
                    Text(
                        text = "Realizar login",
                        color = DarkCard,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                }
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
            color = TextLabel,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(text = "Value", color = TextHint, fontSize = 15.sp)
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor   = FieldBg,
                unfocusedContainerColor = FieldBg,
                focusedBorderColor      = AccentGreen,
                unfocusedBorderColor    = FieldBorder,
                focusedTextColor        = TextWhite,
                unfocusedTextColor      = TextWhite,
                cursorColor             = AccentGreen
            ),
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
            color = TextWhite,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "≡",
            color = TextWhite,
            fontSize = 28.sp
        )
    }
}


@Preview(showBackground = true, widthDp = 360, heightDp = 720)
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}