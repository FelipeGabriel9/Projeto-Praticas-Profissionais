using Microsoft.EntityFrameworkCore;
using MyMoney.Data;
using MyMoney.Models;

namespace MyMoney.Endpoints;

public static class MetaEndpoints
{
	public static void MapMetaEndpoints(this WebApplication app)
	{
		var grupo = app.MapGroup("/metas"); // grupo de rotas que começam com /metas


		//GET
		grupo.MapGet("/", async (AppDbContext db) =>
		{
			return await db.Meta.ToListAsync();
			// retorna uma lista de metas após ler a tabela no BD
		});


		//GET BY ID
		grupo.MapGet("/{id}", async (int id, AppDbContext db) =>
		{
			var metas = await db.Meta.FindAsync(id);
			// busca uma meta adeterminado id no BD

			return metas is null ? Results.NotFound() : Results.Ok(metas);
			// se não houver uma meta com o id (variável meta é null),
			// retorna um erro (404). Caso contrário, retorna a meta
		});


		//POST
		grupo.MapPost("/", async (Meta novaMeta, AppDbContext db) =>
		{
			db.Meta.Add(novaMeta); // Adiciona a nova meta
			await db.SaveChangesAsync(); // Salva as alterações no banco de dados

			return Results.Created($"/metas/{novaMeta.idMeta}", novaMeta); // Retorna Created (sucesso 201) e o endereço onde o item criado pode ser encontrado

		});


		//PUT
		grupo.MapPut("/{id}", async (int id, Meta metaAtualizada, AppDbContext db) =>
		{
			// Busca a meta original no banco
			var metas = await db.Meta.FindAsync(id);
			// Se não achar, retorna 404
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

            // Salva as alterações no banco
            await db.SaveChangesAsync();
			// Retorna NoContent (sucesso 204, feito sem retornar dados novos)
			return Results.NoContent();
		});


		//DELETE
		grupo.MapDelete("/{id}", async (int id, AppDbContext db) =>
		{
			// Busca a meta pelo ID
			var metas = await db.Meta.FindAsync(id);
			// Se não achar, retorna 404
			if (metas is null)
				return Results.NotFound();

			// Remove a meta da memória do contexto
			db.Meta.Remove(metas);
			// Efetiva a exclusão no banco de dados
			await db.SaveChangesAsync();
			// Retorna NoContent (sucesso 204)
			return Results.NoContent();
		});
	}
}