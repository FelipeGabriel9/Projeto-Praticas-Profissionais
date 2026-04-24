using Microsoft.EntityFrameworkCore;
using MyMoney.Data;
using MyMoney.Models;

namespace MyMoney.Endpoints;

public static class TransacoesEndpoints
{
    public static void MapCategoriaEndpoints(this WebApplication app)
    {
        var grupo = app.MapGroup("/transacoes"); // grupo de rotas que começam com /transacoes


        //GET
        grupo.MapGet("/", async(AppDbContext db) =>
        {
           return await db.Transacoes.ToListAsync(); 
           // retorna uma lista de transacoes após ler a tabela no BD
        });


        //GET BY ID
        grupo.MapGet("/{id}", async (int id, AppDbContext db) =>
        {
           var transacao = await db.Transacoes.FindAsync(id);
           // busca uma transação com um determinado id no BD

           return transacao is null ? Results.NotFound() : Results.Ok(transacao);
           // se não houver uma transação com o id (variável transação é null),
           // retorna um erro (404). Caso contrário, retorna a transação 
        });


        //POST
        grupo.MapPost("/", async (Transacoes novaTransacao, AppDbContext db) =>
        {
            db.Transacoes.Add(novaTransacao); // Adiciona a nova transação
            await db.SaveChangesAsync(); // Salva as alterações no banco de dados

            return Results.Created($"/transacoes/{novaTransacao.idTransacoes}", novaTransacao); // Retorna Created (sucesso 201) e o endereço onde o item criado pode ser encontrado

        });


        //PUT
        grupo.MapPut("/{id}", async (int id, Transacoes transacaoAtualizada, AppDbContext db) =>
        {
            // Busca a transação original no banco
            var transacao = await db.Transacoes.FindAsync(id);
            // Se não achar, retorna 404
            if (transacao is null) 
                return Results.NotFound();

            // Atualiza o idCategoria
            transacao.idCategoria = transacaoAtualizada.idCategoria;
            // Atualiza o idUsuario
            transacao.idUsuario = transacaoAtualizada.idUsuario;
            // Atualiza o tipo
            transacao.Tipo = transacaoAtualizada.Tipo;
            // Atualiza o valor
            transacao.Valor = transacaoAtualizada.Valor;
            // Atualiza o descricao
            transacao.Descricao = transacaoAtualizada.Descricao;
            // Salva as alterações no banco
            await db.SaveChangesAsync();
            // Retorna NoContent (sucesso 204, feito sem retornar dados novos)
            return Results.NoContent();
        });


        //DELETE
        grupo.MapDelete("/{id}", async (int id, AppDbContext db) =>
        {
            // Busca a transação pelo ID
            var transacao = await db.Transacoes.FindAsync(id);
            // Se não achar, retorna 404
            if (transacao is null) 
                return Results.NotFound();

            // Remove a transação da memória do contexto
            db.Transacoes.Remove(transacao);
            // Efetiva a exclusão no banco de dados
            await db.SaveChangesAsync();
            // Retorna NoContent (sucesso 204)
            return Results.NoContent();
        });
    } 
}