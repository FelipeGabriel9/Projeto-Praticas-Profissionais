using System.Configuration.Assemblies;

namespace MyMoney.Models;

public class Meta
{
    // Propriedades
    public required int idMeta { get; set; }
    public required int idUsuario { get; set; }
    public required string NomeMeta { get; set; }
    public required Money ValorObjetivo { get; set; }
    public required Money ValorAtual { get; set; }
    public required DateTime DataCriacao { get; set; }

}