package br.com.empresa.controller;

import br.com.empresa.dao.StatusReservaDAO;
import br.com.empresa.model.StatusReserva;
import java.sql.SQLException;
import java.util.List;

public class StatusReservaController {

    private final StatusReservaDAO dao;

    public StatusReservaController() {
        this.dao = new StatusReservaDAO();
    }

    public List<StatusReserva> listarTodos() throws SQLException {
        return dao.listarTodos();
    }

}