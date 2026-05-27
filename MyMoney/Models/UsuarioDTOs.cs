namespace MyMoney.Models;

public record CadastroUsuario(string Nome, string Email, string Senha, string Cpf);

public record LoginUsuario(int idUsuario, string Email, string Senha);

public record DadosUsuario(int idUsuario, string Nome, string Email, string CPF);