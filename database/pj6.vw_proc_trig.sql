-- ============================================================================
-- PROCEDURES
-- ============================================================================

CREATE OR ALTER PROCEDURE pj6.proc_inserirUsuario
    @nome VARCHAR(100),
    @cpf VARCHAR(14),
    @dataAniversario DATE,
    @celular VARCHAR(20),
    @email VARCHAR(100)
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        INSERT INTO pj6.usuario (cpf, nome, dataAniversario, celular, email)
        VALUES (@cpf, @nome, @dataAniversario, @celular, @email);
    END TRY
    BEGIN CATCH
        THROW;
    END CATCH
END;
GO

CREATE OR ALTER PROCEDURE pj6.proc_excluirUsuario
    @id INT
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        DELETE FROM pj6.usuario
        WHERE id = @id;
    END TRY
    BEGIN CATCH
        THROW;
    END CATCH
END;
GO

CREATE OR ALTER PROCEDURE pj6.proc_atualizarUsuario
    @id INT,
    @nome VARCHAR(100) = NULL,
    @cpf VARCHAR(14) = NULL,
    @dataAniversario DATE = NULL,
    @celular VARCHAR(20) = NULL,
    @email VARCHAR(100) = NULL
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        UPDATE pj6.usuario
        SET
            cpf = ISNULL(@cpf, cpf),
            nome = ISNULL(@nome, nome),
            dataAniversario = ISNULL(@dataAniversario, dataAniversario),
            celular = ISNULL(@celular, celular),
            email = ISNULL(@email, email)
        WHERE id = @id;
    END TRY
    BEGIN CATCH
        THROW;
    END CATCH
END;
GO

CREATE OR ALTER PROCEDURE pj6.proc_inserirRecurso
    @nome VARCHAR(100),
    @capacidade INT,
    @localizacao VARCHAR(100),
    @ehLaboratorio BIT
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        INSERT INTO pj6.recurso (nome, capacidade, localizacao, ehLaboratorio)
        VALUES (@nome, @capacidade, @localizacao, @ehLaboratorio);
    END TRY
    BEGIN CATCH
        THROW;
    END CATCH
END;
GO

CREATE OR ALTER PROCEDURE pj6.proc_excluirRecurso
    @id INT
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        DELETE FROM pj6.recurso
        WHERE id = @id;
    END TRY
    BEGIN CATCH
        THROW;
    END CATCH
END;
GO

CREATE OR ALTER PROCEDURE pj6.proc_atualizarRecurso
    @id INT,
    @nome VARCHAR(100) = NULL,
    @capacidade INT = NULL,
    @localizacao VARCHAR(100) = NULL,
    @ehLaboratorio BIT = NULL
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        UPDATE pj6.recurso
        SET
            nome = ISNULL(@nome, nome),
            capacidade = ISNULL(@capacidade, capacidade),
            localizacao = ISNULL(@localizacao, localizacao),
            ehLaboratorio = ISNULL(@ehLaboratorio, ehLaboratorio)
        WHERE id = @id;
    END TRY
    BEGIN CATCH
        THROW;
    END CATCH
END;
GO

CREATE OR ALTER PROCEDURE pj6.proc_inserirReserva
    @dataInicial DATE,
    @dataFinal DATE,
    @horaInicial TIME(0),
    @horaFinal TIME(0),
    @idRecurso INT,
    @idUsuario INT,
    @idStatusReserva INT
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        INSERT INTO pj6.reserva (
            dataInicial,
            dataFinal,
            horaInicial,
            horaFinal,
            id_recurso,
            id_usuario,
            id_statusreserva
        )
        VALUES (
            @dataInicial,
            @dataFinal,
            @horaInicial,
            @horaFinal,
            @idRecurso,
            @idUsuario,
            @idStatusReserva
        );
    END TRY
    BEGIN CATCH
        THROW;
    END CATCH
END;
GO

CREATE OR ALTER PROCEDURE pj6.proc_excluirReserva
    @id INT
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        DELETE FROM pj6.reserva
        WHERE id = @id;
    END TRY
    BEGIN CATCH
        THROW;
    END CATCH
END;
GO

CREATE OR ALTER PROCEDURE pj6.proc_atualizarReserva
    @id INT,
    @dataInicial DATE = NULL,
    @dataFinal DATE = NULL,
    @horaInicial TIME(0) = NULL,
    @horaFinal TIME(0) = NULL,
    @idRecurso INT = NULL,
    @idUsuario INT = NULL,
    @idStatusReserva INT = NULL
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        UPDATE pj6.reserva
        SET
            dataInicial = ISNULL(@dataInicial, dataInicial),
            dataFinal = ISNULL(@dataFinal, dataFinal),
            horaInicial = ISNULL(@horaInicial, horaInicial),
            horaFinal = ISNULL(@horaFinal, horaFinal),
            id_recurso = ISNULL(@idRecurso, id_recurso),
            id_usuario = ISNULL(@idUsuario, id_usuario),
            id_statusreserva = ISNULL(@idStatusReserva, id_statusreserva)
        WHERE id = @id;
    END TRY
    BEGIN CATCH
        THROW;
    END CATCH
END;
GO

CREATE OR ALTER PROCEDURE pj6.proc_inserirAcesso
    @login VARCHAR(100),
    @senha VARCHAR(50),
    @dataCadastro DATE,
    @id_usuario INT
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        INSERT INTO pj6.acesso (login, senha, dataCadastro, id_usuario)
        VALUES (@login, @senha, @dataCadastro, @id_usuario);
    END TRY
    BEGIN CATCH
        THROW;
    END CATCH
END;
GO

CREATE OR ALTER PROCEDURE pj6.proc_excluirAcesso
    @id INT
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        DELETE FROM pj6.acesso
        WHERE id = @id;
    END TRY
    BEGIN CATCH
        THROW;
    END CATCH
END;
GO

CREATE OR ALTER PROCEDURE pj6.proc_atualizarAcesso
    @id INT,
    @login VARCHAR(100) = NULL,
    @senha VARCHAR(50) = NULL,
    @dataCadastro DATE = NULL
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        UPDATE pj6.acesso
        SET
            login = ISNULL(@login, login),
            senha = ISNULL(@senha, senha),
            dataCadastro = ISNULL(@dataCadastro, dataCadastro)
        WHERE id = @id;
    END TRY
    BEGIN CATCH
        THROW;
    END CATCH
END;
GO

CREATE OR ALTER PROCEDURE pj6.proc_autenticarAcesso
    @login VARCHAR(100),
    @senha VARCHAR(50)
AS
BEGIN
    SET NOCOUNT ON;
    DECLARE @acesso INT;

    BEGIN TRY
        SELECT @acesso = id
        FROM pj6.acesso
        WHERE login = @login AND senha = @senha;

        IF @acesso IS NOT NULL
        BEGIN
            SELECT *
            FROM pj6.acesso a
            INNER JOIN pj6.usuario u ON a.id_usuario = u.id
            WHERE a.id = @acesso;

            INSERT INTO pj6.auditoria (id_acesso, dataAcesso)
            VALUES (@acesso, GETDATE());

            SELECT
                a.id AS idAcesso,
                a.login,
                u.id AS idUsuario,
                u.nome AS nomeUsuario
            FROM pj6.acesso a
            INNER JOIN pj6.usuario u ON u.id = a.id_usuario
            WHERE a.id = @acesso;
        END
        ELSE
        BEGIN
            THROW 50001, 'Login ou senha inválidos!', 1;
        END
    END TRY
    BEGIN CATCH
        THROW;
    END CATCH
END;
GO

-- ============================================================================
-- VIEWS
-- ============================================================================

CREATE OR ALTER VIEW pj6.vw_dadosReservaRecurso
AS
SELECT
    res.id AS idReserva,
    rec.nome AS nomeRecurso,
    rec.capacidade,
    rec.localizacao,
    res.dataInicial,
    res.dataFinal,
    res.horaInicial,
    res.horaFinal,
    sr.nome AS statusReserva,
    u.nome AS nomeUsuario,
    u.email
FROM pj6.recurso AS rec
INNER JOIN pj6.reserva AS res
    ON res.id_recurso = rec.id
INNER JOIN pj6.statusReserva AS sr
    ON res.id_statusreserva = sr.id
INNER JOIN pj6.usuario AS u
    ON res.id_usuario = u.id;
GO

CREATE OR ALTER VIEW pj6.vw_acessoUsuario
AS
SELECT
    au.id AS id_auditoria,
    au.dataAcesso AS data_acesso,
    a.login AS login_usuario,
    u.id AS id_usuario,
    u.nome AS nome_usuario
FROM pj6.auditoria AS au
INNER JOIN pj6.acesso AS a
    ON au.id_acesso = a.id
INNER JOIN pj6.usuario AS u
    ON u.id = a.id_usuario;
GO

-- ============================================================================
-- TRIGGERS
-- ============================================================================

CREATE OR ALTER TRIGGER pj6.tg_validarReserva_after_ins_upd
ON pj6.reserva
AFTER INSERT, UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    IF EXISTS (
        SELECT 1
        FROM pj6.reserva AS r
        INNER JOIN inserted AS i
            ON r.id_recurso = i.id_recurso
        WHERE r.id <> i.id
          AND r.id_statusreserva IN (SELECT id FROM pj6.statusReserva WHERE nome IN ('Ocupado', 'Reservado', 'Bloqueado'))
          AND i.id_statusreserva IN (SELECT id FROM pj6.statusReserva WHERE nome IN ('Ocupado', 'Reservado', 'Bloqueado'))
          AND (i.dataInicial <= r.dataFinal AND i.dataFinal >= r.dataInicial)
          AND (i.horaInicial < r.horaFinal AND i.horaFinal > r.horaInicial)
    )
    BEGIN
        THROW 50002, 'Já existe reserva para esta sala / sala indisponível', 1;
    END
END;
GO
