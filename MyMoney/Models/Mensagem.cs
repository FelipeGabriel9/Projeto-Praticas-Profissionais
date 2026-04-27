using System.Configuration.Assemblies;

namespace MyMoney.Models;

public class Mensagem
{
    // Propriedades
    public required int idMensagem { get; set; }
    public required int idUsuario { get; set; }
    public required Usuario Usuario { get; set; }
    public required string Assunto { get; set; }
    public required string mensagem { get; set; }
    public required DateTime DataEnvio { get; set; }
}