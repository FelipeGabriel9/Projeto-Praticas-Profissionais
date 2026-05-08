using System.Configuration.Assemblies;

namespace MyMoney.Models;

public class Categoria
{
	// Propriedades
	public int? idCategoria { get; set; }
	public int? idUsuario { get; set; }
	public required string NomeCategoria { get; set; }
	public required money ValorDespesa { get; set; }
}