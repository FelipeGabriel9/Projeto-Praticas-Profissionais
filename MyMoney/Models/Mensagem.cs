using System.Configuration.Assemblies;

namespace MyMoney.Models;

public class Mensagem
{
    // Propriedades
    public required int idCategoria { get; set; }
    public required int idUsuario { get; set; }
    public required string Assunto { get; set; }
    public required string Mensagem { get; set; }
    public required DateTime DataCriacao { get; set; }
}