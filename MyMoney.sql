CREATE SCHEMA MyMoney

----------------------------------------------------------------------------------------------

CREATE TABLE MyMoney.Usuario
(
idUsuario INT PRIMARY KEY IDENTITY,
Nome VARCHAR(70) NOT NULL,
Email VARCHAR(70) NOT NULL UNIQUE,
SenhaHash VARCHAR(400) NOT NULL,
CPF CHAR(11) NOT NULL UNIQUE,
DataCriacao DATETIME DEFAULT GETDATE(),
CONSTRAINT CK_CPF CHECK(CPF NOT LIKE '%[^0-9]%' )
)

DROP TABLE MyMoney.Usuario
SELECT * FROM MyMoney.Usuario
----------------------------------------------------------------------------------------------

CREATE TABLE MyMoney.Categoria
(
idCategoria INT PRIMARY KEY IDENTITY,
NomeCategoria VARCHAR(30) NOT NULL,
ValorDespesa MONEY NOT NULL,
idUsuario INT NOT NULL,
CONSTRAINT fkidCategoriaUsuario FOREIGN KEY (idUsuario) REFERENCES MyMoney.Usuario(idUsuario)
)

DROP TABLE MyMoney.Categoria
SELECT * FROM MyMoney.Categoria
----------------------------------------------------------------------------------------------

-- Deletado --
CREATE TABLE MyMoney.Transacoes
(
idTransacoes INT PRIMARY KEY IDENTITY,
idCategoria INT NOT NULL,
idUsuario INT NOT NULL,
Tipo VARCHAR(10) NOT NULL,
Valor MONEY NOT NULL,
Descricao VARCHAR(30) NOT NULL,
DataTransacao DATETIME DEFAULT GETDATE(),
CONSTRAINT fkidTransacoesUsuario FOREIGN KEY (idUsuario) REFERENCES MyMoney.Usuario(idUsuario),
CONSTRAINT fkidTransacoesCategoria FOREIGN KEY (idCategoria) REFERENCES MyMoney.Categoria(idCategoria)
)

DROP TABLE MyMoney.Transacoes
SELECT * FROM MyMoney.Transacoes
----------------------------------------------------------------------------------------------

CREATE TABLE MyMoney.Meta
(
idMeta INT PRIMARY KEY IDENTITY,
idUsuario INT NOT NULL,
NomeMeta VARCHAR(30) NOT NULL,
ValorObjetivo MONEY NOT NULL,
ValorAtual MONEY NOT NULL,
DataCriacao DATETIME DEFAULT GETDATE(),
CONSTRAINT fkidMetaUsuario FOREIGN KEY (idUsuario) REFERENCES MyMoney.Usuario(idUsuario)
)

DROP TABLE MyMoney.Meta
SELECT * FROM MyMoney.Meta
----------------------------------------------------------------------------------------------

CREATE TABLE MyMoney.Mensagem
(
idMensagem INT PRIMARY KEY IDENTITY,
idUsuario INT NOT NULL,
Assunto VARCHAR(40) NOT NULL,
Mensagem VARCHAR(150) NOT NULL,
DataEnvio DATETIME DEFAULT GETDATE(),
CONSTRAINT fkidUsuario FOREIGN KEY (idUsuario) REFERENCES MyMoney.Usuario(idUsuario)
)

DROP TABLE MyMoney.Mensagem
SELECT * FROM MyMoney.Mensagem