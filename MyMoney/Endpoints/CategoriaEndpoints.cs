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
        grupo.MapGet("/", async(AppDbContext db) =>
        {
           return await db.Categoria.ToListAsync(); 
           // retorna uma lista de categorias após ler a tabela no BD
        });


        //GET BY ID
        grupo.MapGet("/{id}", async (int id, AppDbContext db) =>
        {
           var categoria = await db.Categoria.FindAsync(id);
           // busca uma categoria com um determinado id no BD

           return categoria is null ? Results.NotFound() : Results.Ok(categoria);
           // se não houver uma categoria com o id (variável categoria é null),
           // retorna um erro (404). Caso contrário, retorna a categoria 
        });


        //POST
        grupo.MapPost("/", async (Categoria novaCategoria, AppDbContext db) =>
        {
            db.Categoria.Add(novaCategoria); // Adiciona a nova categoria
            await db.SaveChangesAsync(); // Salva as alterações no banco de dados

            return Results.Created($"/categorias/{novaCategoria.idCategoria}", novaCategoria); // Retorna Created (sucesso 201) e o endereço onde o item criado pode ser encontrado

        });


        //PUT
        grupo.MapPut("/{id}", async (int id, Categoria categoriaAtualizada, AppDbContext db) =>
        {
            // Busca a categoria original no banco
            var categoria = await db.Categoria.FindAsync(id);
            // Se não achar, retorna 404
            if (categoria is null) 
                return Results.NotFound();

            // Atualiza o nome da categoria
            categoria.NomeCategoria = categoriaAtualizada.NomeCategoria;
            // Atualiza o tipo
            categoria.Tipo = categoriaAtualizada.Tipo;
            // Atualiza o idUsuario
            categoria.idUsuario = categoriaAtualizada.idUsuario;
            // Salva as alterações no banco
            await db.SaveChangesAsync();
            // Retorna NoContent (sucesso 204, feito sem retornar dados novos)
            return Results.NoContent();
        });


        //DELETE
        grupo.MapDelete("/{id}", async (int id, AppDbContext db) =>
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