package br.com.empresa.dao;

import br.com.empresa.model.StatusReserva;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatusReservaDAO {

    public List<StatusReserva> listarTodos() throws SQLException {
        List<StatusReserva> lista = new ArrayList<>();
        String sql = "SELECT * FROM pj6.StatusReserva";

        try (Connection conexao = ConexaoBD.getConnection();
                Statement comandoSQL = conexao.createStatement();
                ResultSet registros = comandoSQL.executeQuery(sql)) {

            while (registros.next()) {
            
                var statusReservaLido =
                        new StatusReserva(
                                registros.getInt("id"),
                                registros.getString("nome"),
                                registros.getString("descricao"));
                lista.add(statusReservaLido);
            }
        }
        return lista;
    }

}
