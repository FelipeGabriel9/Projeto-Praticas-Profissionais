using Microsoft.EntityFrameworkCore;
using MyMoney.Models;

namespace MyMoney.Data
{
    public class AppDbContext: DbContext
    {
        public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) { }

        public DbSet<Usuario> Usuario { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            // Configuração da tabela Usuario
            modelBuilder.HasDefaultSchema("API");
            modelBuilder.Entity<Usuario>().HasKey(c => c.idUsuario);
            modelBuilder.Entity<Usuario>().Property(c => c.nome).IsRequired().HasColumnType("varchar(50)");
            modelBuilder.Entity<Usuario>().Property(c => c.email).IsRequired().HasColumnType("varchar(50)");
            modelBuilder.Entity<Usuario>().Property(c => c.senhaHash).IsRequired().HasColumnType("varchar(300)");
            modelBuilder.Entity<Usuario>().Property(c => c.moedaPadrao).IsRequired().HasColumnType("char(3)");
            modelBuilder.Entity<Usuario>().Property(c => c.dataCriacao).IsRequired().HasColumnType("datetime");
            modelBuilder.Entity<Usuario>().Property(c => c.idioma).IsRequired().HasColumnType("varchar(15)");
        }
    }
}