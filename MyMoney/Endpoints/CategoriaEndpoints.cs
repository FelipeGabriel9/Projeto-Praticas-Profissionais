using Microsoft.EntityFrameworkCore;
using MyMoney.Data;
using MyMoney.Models;

namespace MyMoney.Endpoints;

public static class CategoriaEndpoints
{
    public static void MapCategoriaEndpoints(this WebApplication app)
    {
        var grupo = app.MapGroup("/categorias"); // grupo de rotas que começam com /categorias


        //GET
        grupo.MapGet("/", async(AppDbContext db) => // OK
        {
           return await db.Categoria.ToListAsync(); 
           // retorna uma lista de categorias após ler a tabela no BD
        });


        // GET CATEGORIAS DO USUÁRIO
        grupo.MapGet("/{idUsuario}", async (int idUsuario, AppDbContext db) =>
        {
            // busca uma categoria com um determinado idUsuario no BD
            var categorias = await db.Categoria
                .Where(c => c.idUsuario == idUsuario)
                .ToListAsync();

            return Results.Ok(categorias);
        });

        //POST
        grupo.MapPost("/", async (Categoria novaCategoria, AppDbContext db) => // OK
        {
            db.Categoria.Add(novaCategoria); // Adiciona a nova categoria
            await db.SaveChangesAsync(); // Salva as alterações no banco de dados

            return Results.Ok(novaCategoria);
        });


        //PUT
        grupo.MapPut("/{idCategoria}", async (int idCategoria, Categoria categoriaAtualizada, AppDbContext db) => // OK
        {
            // Busca a categoria original no banco
            var categoria = await db.Categoria.FindAsync(idCategoria);
            // Se não achar, retorna 404
            if (categoria is null) 
                return Results.NotFound();

            // Atualiza o valor
            categoria.ValorDespesa = categoriaAtualizada.ValorDespesa;

            // Salva as alterações no banco
            await db.SaveChangesAsync();
            
            // Retorna NoContent (sucesso 204, feito sem retornar dados novos)
            return Results.NoContent();
        });


        //DELETE
        grupo.MapDelete("/{id}", async (int id, AppDbContext db) => // OK
        {
            // Busca a categoria pelo ID
            var categoria = await db.Categoria.FindAsync(id);
            // Se não achar, retorna 404
            if (categoria is null) 
                return Results.NotFound();

            // Remove a categoria da memória do contexto
            db.Categoria.Remove(categoria);
            // Efetiva a exclusão no banco de dados
            await db.SaveChangesAsync();
            // Retorna NoContent (sucesso 204)
            return Results.NoContent();
        });
    } 
}