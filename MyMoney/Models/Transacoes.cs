using System.Configuration.Assemblies;

namespace MyMoney.Models;

public class Transacoes
{
    // Propriedades
    public required int idTransacoes {get; set;}
    public int? idCategoria {get; set;} 
    public required Categoria Categoria { get; set; }
    public required int idUsuario  {get; set;}
    public required Usuario Usuario { get; set; }
    public required string Tipo {get; set;}
    public required string Valor {get; set;}
    public required string Descricao {get; set;}
    public DateTime? DataTransacao {get; set;}
    
}