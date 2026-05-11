package com.example.mymoneyandroid.model

// Molde dos dados que o Android vai ENVIAR na hora de cadastrar
data class RegistroRequest(
    val Nome: String,
    val Cpf: String,
    val Email: String,
    val Senha: String // Se no seu C# a variável se chamar apenas 'senha', mude aqui!

)

// Molde dos dados que o Android vai ENVIAR na hora do login
data class LoginRequest(
    val email: String,
    val senhaHash: String // Mude para 'senha' se for assim no C#
)

// Molde dos dados que o Android vai RECEBER da API após dar certo
data class UsuarioResponse(
    val idUsuario: Int,
    val nome: String,
    val email: String,
    val cpf: String
)