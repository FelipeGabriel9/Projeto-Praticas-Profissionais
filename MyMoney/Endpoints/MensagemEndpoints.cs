using Microsoft.EntityFrameworkCore;
using MyMoney.Data;
using MyMoney.Models;

namespace MyMoney.Endpoints;

public static class MensagemEndpoints
{
    public static void MapMensagemEndpoints(this WebApplication app)
    {
        var grupo = app.MapGroup("/mensagens"); // grupo de rotas que come�am com /mensagens


        //GET
        grupo.MapGet("/", async (AppDbContext db) =>
        {
            return await db.Mensagem.ToListAsync();
            // retorna uma lista de mensagens ap�s ler a tabela no BD
        });


        //GET BY ID
        grupo.MapGet("/{id}", async (int id, AppDbContext db) =>
        {
            var mensagem = await db.Mensagem.FindAsync(id);
            // busca uma mensagem com um determinado id no BD

            return mensagem is null ? Results.NotFound() : Results.Ok(mensagem);
            // se n�o houver uma mensagem com o id (vari�vel mensagem � null),
            // retorna um erro (404). Caso contr�rio, retorna a mensagem
        });


        //POST
        grupo.MapPost("/", async (Mensagem novaMensagem, AppDbContext db) =>
        {
            db.Mensagem.Add(novaMensagem); // Adiciona a nova mensagem
            await db.SaveChangesAsync(); // Salva as altera��es no banco de dados

            return Results.Created($"/mensagens/{novaMensagem.idMensagem}", novaMensagem); // Retorna Created (sucesso 201) e o endere�o onde o item criado pode ser encontrado

        });


        //PUT
        grupo.MapPut("/{id}", async (int id, Mensagem mensagemAtualizada, AppDbContext db) =>
        {
            // Busca a mensagem original no banco
            var mensagem = await db.Mensagem.FindAsync(id);
            // Se n�o achar, retorna 404
            if (mensagem is null)
                return Results.NotFound();

            // Atualiza o assunto da mensagem
            mensagem.Assunto = mensagemAtualizada.Assunto;
            // Atualiza a mensagem
            mensagem.mensagem = mensagemAtualizada.mensagem;
            
            // Salva as altera��es no banco
            await db.SaveChangesAsync();
            // Retorna NoContent (sucesso 204, feito sem retornar dados novos)
            return Results.NoContent();
        });


        //DELETE
        grupo.MapDelete("/{id}", async (int id, AppDbContext db) =>
        {
            // Busca a mensagem pelo ID
            var mensagem = await db.Categoria.FindAsync(id);
            // Se n�o achar, retorna 404
            if (mensagem is null)
                return Results.NotFound();

            // Remove a mensagem da mem�ria do contexto
            db.Categoria.Remove(mensagem);
            // Efetiva a exclus�o no banco de dados
            await db.SaveChangesAsync();
            // Retorna NoContent (sucesso 204)
            return Results.NoContent();
        });
    }
}