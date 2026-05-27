package com.example.mymoneyandroid.model

import com.google.gson.annotations.SerializedName

data class CadastroUsuario(
    val Nome: String,
    val Cpf: String,
    val Email: String,
    val Senha: String
)

data class DadosUsuario(
    @SerializedName(value = "idUsuario", alternate = ["id"])
    val idUsuario: Int = 0,
    val nome: String = "",
    val email: String = "",
    val cpf: String? = null
)

data class LoginUsuario(
    val email: String,
    val senha: String
)

data class LoginResposta(
    val idUsuario: Int,
    val nome: String,
    val email: String
)