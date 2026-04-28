using Microsoft.EntityFrameworkCore;
using MyMoney.Models;

namespace MyMoney.Data
{
    public class AppDbContext: DbContext
    {
        public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) { }

        public DbSet<Usuario> Usuario { get; set; } = null!;
        public DbSet<Categoria> Categoria { get; set; } = null!;
        public DbSet<Transacoes> Transacoes { get; set; } = null!;
        public DbSet<Meta> Meta { get; set; } = null!;

        public DbSet<Mensagem> Mensagem { get; set; } = null!;

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            
            modelBuilder.HasDefaultSchema("MyMoney"); // Define o schema das tabelas

            // Configuração da tabela Usuario
            modelBuilder.Entity<Usuario>().HasKey(c => c.idUsuario);
            
            modelBuilder.Entity<Usuario>().Property(c => c.nome).IsRequired().HasColumnType("varchar(50)");
            modelBuilder.Entity<Usuario>().Property(c => c.email).IsRequired().HasColumnType("varchar(50)");
            modelBuilder.Entity<Usuario>().Property(c => c.senhaHash).IsRequired().HasColumnType("varchar(300)");
            modelBuilder.Entity<Usuario>().Property(c => c.moedaPadrao).IsRequired().HasColumnType("char(3)");
            modelBuilder.Entity<Usuario>().Property(c => c.dataCriacao).IsRequired().HasColumnType("datetime");
            modelBuilder.Entity<Usuario>().Property(c => c.idioma).IsRequired().HasColumnType("varchar(15)");

            // Configuração da tabela Categoria
            modelBuilder.Entity<Categoria>().HasKey(c => c.idCategoria);
            modelBuilder.Entity<Categoria>().Property(c => c.NomeCategoria).IsRequired().HasColumnType("varchar(30)");
            modelBuilder.Entity<Categoria>().Property(c => c.Tipo).IsRequired().HasColumnType("varchar(10)");
            modelBuilder.Entity<Categoria>().HasOne(c => c.Usuario).WithMany().HasForeignKey(c => c.idUsuario).IsRequired(true);

            // Configuração da tabela Transações
            modelBuilder.Entity<Transacoes>().HasKey(c => c.idTransacoes);
            modelBuilder.Entity<Transacoes>().HasOne(c => c.Categoria).WithMany().HasForeignKey(c => c.idCategoria).IsRequired(false);
            modelBuilder.Entity<Transacoes>().HasOne(c => c.Usuario).WithMany().HasForeignKey(c => c.idUsuario).IsRequired(true);
            modelBuilder.Entity<Transacoes>().Property(c => c.Tipo).IsRequired().HasColumnType("varchar(10)");
            modelBuilder.Entity<Transacoes>().Property(c => c.Valor).IsRequired().HasColumnType("decimal(18,2)");
            modelBuilder.Entity<Transacoes>().Property(c => c.Descricao).IsRequired().HasColumnType("varchar(30)");
            modelBuilder.Entity<Transacoes>().Property(c => c.DataTransacao).IsRequired().HasColumnType("datetime");
        
            // Configuração da tabela Meta
            modelBuilder.Entity<Meta>().HasKey(c => c.idMeta);
            modelBuilder.Entity<Meta>().HasOne(c => c.Usuario).WithMany().HasForeignKey(c => c.idUsuario).IsRequired(true);
            modelBuilder.Entity<Meta>().Property(c => c.NomeMeta).IsRequired().HasColumnType("varchar(20)");
            modelBuilder.Entity<Meta>().Property(c => c.ValorObjetivo).IsRequired().HasColumnType("decimal(18,2)");
            modelBuilder.Entity<Meta>().Property(c => c.ValorAtual).IsRequired().HasColumnType("decimal(18,2)");
            modelBuilder.Entity<Meta>().Property(c => c.DataCriacao).IsRequired().HasColumnType("datetime");

            // Configuração da tabela Mensagem
            modelBuilder.Entity<Mensagem>().HasKey(c => c.idMensagem);
            modelBuilder.Entity<Mensagem>().HasOne(c => c.Usuario).WithMany().HasForeignKey(c => c.idUsuario).IsRequired(true);
            modelBuilder.Entity<Mensagem>().Property(c => c.Assunto).IsRequired().HasColumnType("varchar(15)");
            modelBuilder.Entity<Mensagem>().Property(c => c.mensagem).IsRequired().HasColumnType("varchar(50)");
            modelBuilder.Entity<Mensagem>().Property(c => c.DataEnvio).IsRequired().HasColumnType("datetime"); 
        }
    }
}