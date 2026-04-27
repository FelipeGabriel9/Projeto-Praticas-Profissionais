using System.Configuration.Assemblies;

namespace MyMoney.Models;

public class Categoria
{
	// Propriedades
	public required int idCategoria { get; set; }
	public int? idUsuario { get; set; }
	public required Usuario Usuario { get; set; }
	public required string NomeCategoria { get; set; }
	public required string Tipo { get; set; }
}