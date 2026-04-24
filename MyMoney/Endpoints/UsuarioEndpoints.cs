using Microsoft.EntityFrameworkCore;
using MyMoney.Data;
using MyMoney.Models;

namespace MyMoney.Endpoints;

public static class UsuarioEndpoints
{
    public static void MapUsuarioEndpoints(this WebApplication app)
    {
        var grupo = app.MapGroup("/usuarios"); // grupo de rotas que começam com /usuarios


        //GET
        grupo.MapGet("/", async(AppDbContext db) =>
        {
           return await db.Usuario.ToListAsync(); 
           // retorna uma lista de usuarios após ler a tabela no BD
        });


        //GET BY ID
        grupo.MapGet("/{id}", async (int id, AppDbContext db) =>
        {
           var usuario = await db.Usuario.FindAsync(id);
           // busca o usuario com um determinado id no BD

           return usuario is null ? Results.NotFound() : Results.Ok(usuario);
           // se não houver um usuário com o id (variável usuario é null),
           // retorna um erro (404). Caso contrário, retorna o usuário 
        });


        //POST
        grupo.MapPost("/", async (Usuario novoUsuario, AppDbContext db) =>
        {
            // Adiciona o novo usuário
            db.Usuario.Add(novoUsuario);
            // Salva as alterações no banco de dados
            await db.SaveChangesAsync();
            // Retorna Created (sucesso 201) e o endereço onde o item criado pode ser encontrado
            return Results.Created($"/usuarios/{novoUsuario.idUsuario}", novoUsuario);
        });


        //PUT
        grupo.MapPut("/{id}", async (int id, Usuario usuarioAtualizado, AppDbContext db) =>
        {
            // Busca o usuário original no banco
            var usuario = await db.Usuario.FindAsync(id);
            // Se não achar, retorna 404
            if (usuario is null) return Results.NotFound();

            // Atualiza o nome
            usuario.nome = usuarioAtualizado.nome;
            // Atualiza o email
            usuario.email = usuarioAtualizado.email;
            // Atualiza a senha
            usuario.senhaHash = usuarioAtualizado.senhaHash;
            // Atualiza a moeda padrão
            usuario.moedaPadrao = usuarioAtualizado.moedaPadrao;
            // Atualiza o idioma
            usuario.idioma = usuarioAtualizado.idioma;
            // Salva as alterações no banco
            await db.SaveChangesAsync();
            // Retorna NoContent (sucesso 204, feito sem retornar dados novos)
            return Results.NoContent();
        });


        //DELETE
        grupo.MapDelete("/{id}", async (int id, AppDbContext db) =>
        {
            // Busca o usuário pelo ID
            var usuario = await db.Usuario.FindAsync(id);
            // Se não achar, retorna 404
            if (usuario is null) return Results.NotFound();

            // Remove o usuário da memória do contexto
            db.Usuario.Remove(usuario);
            // Efetiva a exclusão no banco de dados
            await db.SaveChangesAsync();
            // Retorna NoContent (sucesso 204)
            return Results.NoContent();
        });
    } 
}