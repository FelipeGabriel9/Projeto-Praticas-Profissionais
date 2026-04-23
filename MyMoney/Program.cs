using Microsoft.EntityFrameworkCore;
using MyMoney.Data;
using MyMoney.Endpoints;

var builder = WebApplication.CreateBuilder(args);
builder.Services.AddDbContext<AppDbContext>(options => options.UseSqlServer(builder.Configuration.GetConnectionString("DefaultConnection")));
// Diz para usar o SQL Server, pegando a connection string de nome 'DefaultConnection' lá do appsettings.json

builder.Services.AddEndpointsApiExplorer(); // Adiciona os serviços essenciais para a interface do Swagger funcionar


builder.Services.AddSwaggerGen(); // Adiciona o gerador de arquivos Swagger

var app = builder.Build();

app.UseSwagger();
app.UseSwaggerUI();

app.MapUsuarioEndpoints();
    
app.Run();
