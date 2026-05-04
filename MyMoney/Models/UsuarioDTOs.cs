namespace MyMoney.Models;

public record RegistroRequest(string Nome, string Email, string Senha, string MoedaPadrao, string Idioma);

// O que o usuário envia no Login
public record LoginRequest(string Email, string Senha);

// O que a API responde (sem a senha!)
public record UsuarioResponse(int Id, string Nome, string Email);