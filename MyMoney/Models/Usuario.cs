using System.Configuration.Assemblies;

namespace MyMoney.Models;

public class Usuario
{
    // Propriedades
    public int idUsuario {get; set;}
    public required string nome {get; set;}
    public required string email {get; set;}
    public required string senhaHash {get; set;}
    public required string cpf {get; set;}
    public DateTime dataCriacao {get; set;}
    
}