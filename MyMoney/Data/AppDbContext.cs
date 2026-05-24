using Microsoft.EntityFrameworkCore;
using MyMoney.Models;

namespace MyMoney.Data
{
    public class AppDbContext: DbContext
    {
        public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) { }

        public DbSet<Usuario> Usuario { get; set; } = null!;
        public DbSet<Categoria> Categoria { get; set; } = null!;
        public DbSet<Meta> Meta { get; set; } = null!;
        public DbSet<Mensagem> Mensagem { get; set; } = null!;

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            
            modelBuilder.HasDefaultSchema("MyMoney"); // Define o schema das tabelas

            // Configuração da tabela Usuario
            modelBuilder.Entity<Usuario>().HasKey(u => u.idUsuario);
            modelBuilder.Entity<Usuario>().Property(u => u.nome).IsRequired().HasColumnType("varchar(70)");
            modelBuilder.Entity<Usuario>().Property(u => u.email).IsRequired().HasColumnType("varchar(70)");
            modelBuilder.Entity<Usuario>().HasIndex(u => u.email).IsUnique();
            modelBuilder.Entity<Usuario>().Property(u => u.senhaHash).IsRequired().HasColumnType("varchar(400)");
            modelBuilder.Entity<Usuario>().Property(u => u.cpf).IsRequired().HasColumnType("char(11)");
            modelBuilder.Entity<Usuario>().HasIndex(u => u.cpf).IsUnique();
            modelBuilder.Entity<Usuario>().Property(u => u.dataCriacao).IsRequired().HasColumnType("datetime");

            // Configuração da tabela Categoria
            modelBuilder.Entity<Categoria>().HasKey(c => c.idCategoria);
            modelBuilder.Entity<Categoria>().Property(c => c.NomeCategoria).HasColumnType("varchar(30)");
            modelBuilder.Entity<Categoria>().Property(c => c.NomeCategoria).IsRequired().HasColumnType("varchar(30)");
            modelBuilder.Entity<Categoria>().HasIndex(c => c.NomeCategoria).IsUnique();
            modelBuilder.Entity<Categoria>().Property(c => c.ValorDespesa).IsRequired().HasColumnType("money");
            modelBuilder.Entity<Categoria>().HasOne<Usuario>().WithMany().HasForeignKey(c => c.idUsuario);

            // Configuração da tabela Meta
            modelBuilder.Entity<Meta>().HasKey(m => m.idMeta);
            modelBuilder.Entity<Meta>().HasOne<Usuario>().WithMany().HasForeignKey(c => c.idUsuario);
            modelBuilder.Entity<Meta>().Property(m => m.NomeMeta).HasColumnType("varchar(30)");
            modelBuilder.Entity<Meta>().HasIndex(m => m.NomeMeta).IsUnique();          
            modelBuilder.Entity<Meta>().Property(m => m.ValorObjetivo).IsRequired().HasColumnType("money");
            modelBuilder.Entity<Meta>().Property(m => m.ValorAtual).IsRequired().HasColumnType("money");
            modelBuilder.Entity<Meta>().Property(m => m.DataCriacao).IsRequired().HasColumnType("datetime");

            // Configuração da tabela Mensagem
            modelBuilder.Entity<Mensagem>().HasKey(m => m.idMensagem);
            modelBuilder.Entity<Mensagem>().HasOne<Usuario>().WithMany().HasForeignKey(c => c.idUsuario);
            modelBuilder.Entity<Mensagem>().Property(m => m.Assunto).IsRequired().HasColumnType("varchar(40)");
            modelBuilder.Entity<Mensagem>().Property(m => m.mensagem).IsRequired().HasColumnType("varchar(150)");
            modelBuilder.Entity<Mensagem>().Property(m => m.DataEnvio).IsRequired().HasColumnType("datetime"); 
        }
    }
}