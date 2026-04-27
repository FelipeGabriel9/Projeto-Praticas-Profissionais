using System.Configuration.Assemblies;

namespace MyMoney.Models;

public class Usuario
{
    // Propriedades
    public int idUsuario {get; set;}
    public required string nome {get; set;}
    public required string email {get; set;}
    public required string senhaHash {get; set;}
    public required string moedaPadrao {get; set;}
    public required DateTime dataCriacao {get; set;}
    public required string idioma {get; set;}
    
}