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
        grupo.MapPost("/", async (CadastroUsuario dados, AppDbContext db) =>
        {
            if (await db.Usuario.AnyAsync(u => u.Email == dados.Email))
                return Results.BadRequest("Email já cadastrado!");
            
            if (await db.Usuario.AnyAsync(u => u.CPF == dados.Cpf))
                return Results.BadRequest("Cpf já cadastrado!");

            var novoUsuario = new Usuario {
                Nome = dados.Nome,
                Email = dados.Email,
                SenhaHash = BCrypt.Net.BCrypt.HashPassword(dados.Senha),
                CPF = dados.Cpf,
                DataCriacao = DateTime.Now, // Pega a data atual automaticamente
            };

            db.Usuario.Add(novoUsuario);
            await db.SaveChangesAsync();

            return Results.Ok(novoUsuario);
        });

        //LOGIN
        grupo.MapPost("/login", async (LoginUsuario login, AppDbContext db) =>
{
            // Busca o usuário pelo email
            var usuario = await db.Usuario.FirstOrDefaultAsync(u => u.Email == login.Email);

            if (usuario is null) 
                return Results.Unauthorized(); // Email não existe

            // Verifica se a senha digitada é igual ao Hash do banco
            bool senhaValida = BCrypt.Net.BCrypt.Verify(login.Senha, usuario.SenhaHash);

            if (!senhaValida) 
                return Results.Unauthorized(); // Senha errada

            // Se deu certo, retorna os dados do usuário
            return Results.Ok(new DadosUsuario(usuario.idUsuario, usuario.Nome, usuario.Email));
        });


        //PUT
        grupo.MapPut("/{id}", async (int id, Usuario usuarioAtualizado, AppDbContext db) =>
        {
            // Busca o usuário original no banco
            var usuario = await db.Usuario.FindAsync(id);
            // Se não achar, retorna 404
            if (usuario is null) return Results.NotFound();

            // Atualiza o nome
            usuario.Nome = usuarioAtualizado.Nome;
            // Atualiza o email
            usuario.Email = usuarioAtualizado.Email;

            if (!string.IsNullOrWhiteSpace(usuarioAtualizado.SenhaHash))
            {
                usuario.SenhaHash = usuarioAtualizado.SenhaHash; 
            }
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