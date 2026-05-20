package com.example.mymoneyandroid.model

class ValidarLogin {
    fun validarLogin(email: String, senha: String): String? {
        if (!email.contains("@"))
            return "E-mail inválido!"

        if (senha.length < 6)
            return "A senha deve ter no mínimo 6 dígitos!"
        return null
    }
}

class ValidarCadastro {
    fun validarCadastro(cpf: String, email: String, senha: String): String? {

        if (cpf.length != 11)
            return "Número de caracteres inválidos"

        if (!email.contains("@"))
            return "E-mail inválido!"

        if (senha.length < 6)
            return "A senha deve ter 6+ dígitos!"
        return null
    }
}