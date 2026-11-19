package br.com.empresa.controller;

import br.com.empresa.dao.ReservaDAO;
import br.com.empresa.model.Reserva;
import br.com.empresa.model.ReservaDTO;
import java.sql.SQLException;
import java.time.*;
import java.util.List;

public class ReservaController {

    private final ReservaDAO dao;

    public ReservaController() {
        this.dao = new ReservaDAO();
    }

    public List<Reserva> listarTodos() throws SQLException {
        return dao.listarTodas();
    }

    public List<ReservaDTO> listarParaTabela() throws SQLException {
        return dao.listarParaTabela();
    }

    public boolean incluir(
            LocalDate dataInicial,
            LocalDate dataFinal,
            LocalTime horaInicial,
            LocalTime horaFinal,
            Integer idRecurso,
            Integer idUsuario,
            Integer idStatusReserva)
            throws Exception {

        // lógica de negócio sobre os dados vindos da View
        if (dataInicial == null) {
            throw new IllegalArgumentException("Falta data inicial da reserva!");
        }

        if (dataFinal == null) {
            throw new IllegalArgumentException("Falta data final da reserva!");
        }

        if (horaInicial == null) {
            throw new IllegalArgumentException("Falta hora inicial da reserva!!");
        }

        if (horaFinal == null) {
            throw new IllegalArgumentException("Falta hora final da reserva!");
        }

        if (idRecurso == null) {
            throw new IllegalArgumentException("Falta id do recurso reservado!");
        }

        if (idUsuario == null) {
            throw new IllegalArgumentException("Falta id do usuário que realizou a reserva!");
        }

        if (idStatusReserva == null) {
            throw new IllegalArgumentException("Falta o id do status da reserva!");
        }

        Reserva res = new Reserva();
        res.setDataInicial(dataInicial);
        res.setDataFinal(dataFinal);
        res.setHoraInicial(horaInicial);
        res.setHoraFinal(horaFinal);
        res.setIdRecurso(idRecurso);
        res.setIdUsuario(idUsuario);
        res.setIdStatusReserva(idStatusReserva);

        Reserva existente = dao.existe(dataInicial, dataFinal, horaInicial, horaFinal, idRecurso);
        if (existente == null) {
            dao.inserir(res);
            return true;
        } else {
            return false;
        }
    }

    public boolean alterar(
            Integer id,
            LocalDate dataInicial,
            LocalDate dataFinal,
            LocalTime horaInicial,
            LocalTime horaFinal,
            Integer idRecurso,
            Integer idUsuario,
            Integer idStatusReserva)
            throws Exception {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Falta id do recurso!");
        }

        Reserva res =
                new Reserva(
                        id,
                        dataInicial,
                        dataFinal,
                        horaInicial,
                        horaFinal,
                        idRecurso,
                        idUsuario,
                        idStatusReserva);

        Reserva existente = dao.existe(id);
        if (existente != null) {
            dao.atualizar(res);
            return true;
        } else {
            return false;
        }
    }

    public boolean excluir(Integer id) throws Exception {

        // só para termos uma lógica de negócio
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido!");
        }
        dao.excluir(id);
        return true;
    }

    public Reserva buscar(Integer id) throws Exception {

        if (id == null) {
            throw new IllegalArgumentException("Falta id da reserva!");
        }
        return dao.existe(id);
    }

    public List<ReservaDTO> consultarReservas(
            Integer idRecurso,
            String nomeRecurso,
            LocalDate data,
            LocalTime hora,
            String nomeUsuario,
            String nomeStatus)
            throws Exception {

        return dao.consultar(data, hora, idRecurso, nomeRecurso, nomeUsuario, nomeStatus);
    }
}
