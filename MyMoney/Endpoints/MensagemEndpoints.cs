using Microsoft.EntityFrameworkCore;
using MyMoney.Data;
using MyMoney.Models;

namespace MyMoney.Endpoints;

public static class MensagemEndpoints
{
    public static void MapMensagemEndpoints(this WebApplication app)
    {
        var grupo = app.MapGroup("/mensagens"); // grupo de rotas que começam com /mensagens


        //GET
        grupo.MapGet("/", async (AppDbContext db) =>
        {
            return await db.Mensagem.ToListAsync();
            // retorna uma lista de mensagens após ler a tabela no BD
        });


        //GET BY ID
        grupo.MapGet("/{id}", async (int id, AppDbContext db) =>
        {
            var mensagem = await db.Mensagem.FindAsync(id);
            // busca uma mensagem com um determinado id no BD

            return mensagem is null ? Results.NotFound() : Results.Ok(mensagem);
            // se não houver uma mensagem com o id (variável mensagem é null),
            // retorna um erro (404). Caso contrário, retorna a mensagem
        });


        //POST
        grupo.MapPost("/", async (Mensagem novaMensagem, AppDbContext db) =>
        {
            db.Mensagem.Add(novaMensagem); // Adiciona a nova mensagem
            await db.SaveChangesAsync(); // Salva as alterações no banco de dados

            return Results.Created($"/mensagens/{novaMensagem.idMensagem}", novaMensagem); // Retorna Created (sucesso 201) e o endereço onde o item criado pode ser encontrado

        });


        //PUT
        grupo.MapPut("/{id}", async (int id, Mensagem mensagemAtualizada, AppDbContext db) =>
        {
            // Busca a mensagem original no banco
            var mensagem = await db.Mensagem.FindAsync(id);
            // Se não achar, retorna 404
            if (mensagem is null)
                return Results.NotFound();

            // Atualiza o assunto da mensagem
            mensagem.Assunto = mensagemAtualizada.Assunto;
            // Atualiza a mensagem
            mensagem.Mensagem = mensagemAtualizada.Mensagem;
            
            // Salva as alterações no banco
            await db.SaveChangesAsync();
            // Retorna NoContent (sucesso 204, feito sem retornar dados novos)
            return Results.NoContent();
        });


        //DELETE
        grupo.MapDelete("/{id}", async (int id, AppDbContext db) =>
        {
            // Busca a mensagem pelo ID
            var mensagem = await db.Categoria.FindAsync(id);
            // Se não achar, retorna 404
            if (mensagem is null)
                return Results.NotFound();

            // Remove a mensagem da memória do contexto
            db.Categoria.Remove(mensagem);
            // Efetiva a exclusão no banco de dados
            await db.SaveChangesAsync();
            // Retorna NoContent (sucesso 204)
            return Results.NoContent();
        });
    }
}