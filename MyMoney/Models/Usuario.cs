using System.Configuration.Assemblies;

namespace MyMoney.Models;

public class Usuario
{
    // Propriedades
    public int idUsuario {get; set;}
    public required string Nome {get; set;}
    public required string Email {get; set;}
    public required string SenhaHash {get; set;}
    public required string CPF {get; set;}
    public DateTime? DataCriacao {get; set;}
    
}