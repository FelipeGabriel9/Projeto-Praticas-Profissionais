using System.Configuration.Assemblies;

namespace MyMoney.Models;

public class Usuario
{
    // Propriedades
    public int idUsuario {get; set;}
    public string nome {get; set;}
    public string email {get; set;}
    public string senhaHash {get; set;}
    public string moedaPadrao {get; set;}
    public DateTime dataCriacao {get; set;}
    public string idioma {get; set;}
    
}