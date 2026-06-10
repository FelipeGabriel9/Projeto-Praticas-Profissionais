using Microsoft.EntityFrameworkCore;
using MyMoney.Data;
using MyMoney.Models;

namespace MyMoney.Endpoints;

public static class MetaEndpoints
{
	public static void MapMetaEndpoints(this WebApplication app)
	{
		var grupo = app.MapGroup("/metas"); // grupo de rotas que come�am com /metas


		//GET
		grupo.MapGet("/", async (AppDbContext db) =>
		{
			return await db.Meta.ToListAsync();
			// retorna uma lista de metas ap�s ler a tabela no BD
		});


		//GET BY ID
		grupo.MapGet("/{idUsuario}", async (int idUsuario, AppDbContext db) =>
		{
			var metas = await db.Meta
				.Where(m => m.idUsuario == idUsuario)
				.ToListAsync();

			return metas.Count == 0
				? Results.NotFound()
				: Results.Ok(metas);
		});


		//POST
		// No seu MetaEndpoints.cs dentro do MapMetaEndpoints

		// POST
		grupo.MapPost("/", async (Meta novaMeta, AppDbContext db) =>
		{
			// Se o Android não mandou data, o servidor gera a data atual antes de salvar!
			if (novaMeta.DataCriacao == null)
			{
				novaMeta.DataCriacao = DateTime.Now;
			}

			db.Meta.Add(novaMeta); 
			await db.SaveChangesAsync(); 

			return Results.Ok(novaMeta);
		});


		//PUT
		grupo.MapPut("/{id}", async (int id, Meta metaAtualizada, AppDbContext db) =>
		{
			// Busca a meta original no banco
			var metas = await db.Meta.FindAsync(id);
			// Se n�o achar, retorna 404
			if (metas is null)
				return Results.NotFound();

            // Atualiza o idUsuario
            metas.idUsuario = metaAtualizada.idUsuario;
            // Atualiza o nome da meta
            metas.NomeMeta = metaAtualizada.NomeMeta;
			// Atualiza o valor do objetivo
			metas.ValorObjetivo = metaAtualizada.ValorObjetivo;
            // Atualiza o valor atual
            metas.ValorAtual = metaAtualizada.ValorAtual;

            // Salva as altera��es no banco
            await db.SaveChangesAsync();
			// Retorna NoContent (sucesso 204, feito sem retornar dados novos)
			return Results.NoContent();
		});


		//DELETE
		grupo.MapDelete("/{id}", async (int id, AppDbContext db) =>
		{
			// Busca a meta pelo ID
			var metas = await db.Meta.FindAsync(id);
			// Se n�o achar, retorna 404
			if (metas is null)
				return Results.NotFound();

			// Remove a meta da mem�ria do contexto
			db.Meta.Remove(metas);
			// Efetiva a exclus�o no banco de dados
			await db.SaveChangesAsync();
			// Retorna NoContent (sucesso 204)
			return Results.NoContent();
		});
	}
}