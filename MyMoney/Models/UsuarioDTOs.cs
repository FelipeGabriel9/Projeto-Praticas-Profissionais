namespace MyMoney.Models;

public record CadastroUsuario(string Nome, string Email, string Senha, string Cpf);

public record LoginUsuario(string Email, string Senha);

public record DadosUsuario(int Id, string Nome, string Email);