namespace MyMoney.Models;

public record CadastroUsuario(string Nome, string Email, string Senha, string Cpf);

// O que o usuário envia no Login
public record LoginUsuario(string Email, string Senha);

// O que a API responde (sem a senha!)
public record DadosUsuario(int Id, string Nome, string Email);