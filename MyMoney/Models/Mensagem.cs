using System.Configuration.Assemblies;

namespace MyMoney.Models;

public class Mensagem
{
    // Propriedades
    public int idMensagem { get; set; }
    public int idUsuario { get; set; }
    public required string Assunto { get; set; }
    public required string mensagem { get; set; }
    public DateTime DataEnvio { get; set; } = DateTime.Now;
}