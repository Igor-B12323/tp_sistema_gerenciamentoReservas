-- 1. Criação do Schema
CREATE SCHEMA pj6;
GO

-- 2. Tabela de Usuários
CREATE TABLE pj6.usuario (
    id INT IDENTITY(1,1) PRIMARY KEY,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    nome VARCHAR(100) NOT NULL,
    dataAniversario DATE NOT NULL,
    celular VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL
);
GO

-- 3. Tabela de Acessos (Login)
CREATE TABLE pj6.acesso (
    id INT IDENTITY(1,1) PRIMARY KEY,
    login VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(50) NOT NULL,
    dataCadastro DATE NOT NULL,
    id_usuario INT NOT NULL,
    CONSTRAINT fk_acesso_usuario FOREIGN KEY (id_usuario) REFERENCES pj6.usuario(id)
);
GO

-- 4. Tabela de Auditoria de Acessos
CREATE TABLE pj6.auditoria (
    id INT IDENTITY(1,1) PRIMARY KEY,
    id_acesso INT NOT NULL,
    dataAcesso DATETIME NOT NULL DEFAULT GETDATE(),
    CONSTRAINT fk_auditoria_acesso FOREIGN KEY (id_acesso) REFERENCES pj6.acesso(id)
);
GO

-- 5. Tabela de Recursos (Salas e Laboratórios)
CREATE TABLE pj6.recurso (
    id INT IDENTITY(1,1) PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    capacidade INT NOT NULL,
    localizacao VARCHAR(100) NOT NULL,
    ehLaboratorio BIT NOT NULL
);
GO

-- 6. Tabela de Status de Reserva
CREATE TABLE pj6.statusReserva (
    id INT IDENTITY(1,1) PRIMARY KEY,
    nome VARCHAR(50) NOT NULL UNIQUE
);
GO

-- 7. Tabela de Reservas
CREATE TABLE pj6.reserva (
    id INT IDENTITY(1,1) PRIMARY KEY,
    dataInicial DATE NOT NULL,
    dataFinal DATE NOT NULL,
    horaInicial TIME(0) NOT NULL,
    horaFinal TIME(0) NOT NULL,
    id_recurso INT NOT NULL,
    id_usuario INT NOT NULL,
    id_statusreserva INT NOT NULL,
    CONSTRAINT fk_reserva_recurso FOREIGN KEY (id_recurso) REFERENCES pj6.recurso(id),
    CONSTRAINT fk_reserva_usuario FOREIGN KEY (id_usuario) REFERENCES pj6.usuario(id),
    CONSTRAINT fk_reserva_status FOREIGN KEY (id_statusreserva) REFERENCES pj6.statusReserva(id)
);
GO

-- 8. Dados iniciais obrigatórios para a Trigger e Login inicial
INSERT INTO pj6.statusReserva (nome) VALUES 
('Disponível'),
('Ocupado'),
('Reservado'),
('Bloqueado');
GO

-- Usuário e Acesso padrão para primeiro login de quem baixar o projeto
INSERT INTO pj6.usuario (cpf, nome, dataAniversario, celular, email) 
VALUES ('000.000.000-00', 'Administrador', '2000-01-01', '(00) 00000-0000', 'admin@sistema.com');

INSERT INTO pj6.acesso (login, senha, dataCadastro, id_usuario) 
VALUES ('admin', 'admin123', GETDATE(), 1);
GO
