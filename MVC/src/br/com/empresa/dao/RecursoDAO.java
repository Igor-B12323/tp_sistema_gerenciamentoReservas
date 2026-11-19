package br.com.empresa.dao;

import br.com.empresa.model.Recurso;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecursoDAO {

    public List<Recurso> listarTodos() throws SQLException {
        List<Recurso> lista = new ArrayList<>();
        String sql = "SELECT * FROM pj6.Recurso ORDER BY nome";

        try (Connection conexao = ConexaoBD.getConnection();
                Statement comandoSQL = conexao.createStatement();
                ResultSet registros = comandoSQL.executeQuery(sql)) {

            while (registros.next()) {

                var recursoLido =
                        new Recurso(
                                registros.getInt("id"),
                                registros.getString("nome"),
                                registros.getInt("capacidade"),
                                registros.getString("localizacao"),
                                registros.getByte("ehLaboratorio"));
                lista.add(recursoLido);
            }
        }
        return lista;
    }

    public Recurso existe(int idProcurado) throws SQLException {
        String sql = "SELECT * FROM pj6.recurso WHERE id = ?";

        try (Connection conexao = ConexaoBD.getConnection();
                PreparedStatement comandoSQL = conexao.prepareStatement(sql)) {

            comandoSQL.setInt(1, idProcurado);

            try (ResultSet registros = comandoSQL.executeQuery()) {
                if (registros.next()) {
                    return new Recurso(
                            registros.getInt("id"),
                            registros.getString("nome"),
                            registros.getInt("capacidade"),
                            registros.getString("localizacao"),
                            registros.getByte("ehLaboratorio"));
                }
            }
        }
        return null;
    }

    public Recurso existe(String nomeProcurado) throws SQLException {
        String sql = "SELECT * FROM pj6.recurso WHERE nome LIKE ?";

        try (Connection conexao = ConexaoBD.getConnection();
                PreparedStatement comandoSQL = conexao.prepareStatement(sql)) {

            comandoSQL.setString(1, "%" + nomeProcurado + "%");

            try (ResultSet registros = comandoSQL.executeQuery()) {
                if (registros.next()) {
                    return new Recurso(
                            registros.getInt("id"),
                            registros.getString("nome"),
                            registros.getInt("capacidade"),
                            registros.getString("localizacao"),
                            registros.getByte("ehLaboratorio"));
                }
            }
        }
        return null;
    }

    public void inserir(Recurso recurso) throws SQLException {
        String sql = "{call pj6.proc_inserirRecurso (?, ?, ?, ?)}";

        try (Connection conexao = ConexaoBD.getConnection();
                CallableStatement cs = conexao.prepareCall(sql)) {

            cs.setString(1, recurso.getNome());
            cs.setInt(2, recurso.getCapacidade());
            cs.setString(3, recurso.getLocalizacao());
            cs.setByte(4, recurso.getEhLaboratorio());

            cs.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "{call pj6.proc_excluirRecurso (?)}";

        try (Connection conexao = ConexaoBD.getConnection();
                CallableStatement cs = conexao.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.executeUpdate();
        }
    }

    public void atualizar(Recurso recurso) throws SQLException {
        String sql = "{call pj6.proc_atualizarRecurso (?, ?, ?, ?, ?)}";

        try (Connection conexao = ConexaoBD.getConnection();
                CallableStatement cs = conexao.prepareCall(sql)) {

            cs.setInt(1, recurso.getId());
            cs.setString(2, recurso.getNome());
            cs.setInt(3, recurso.getCapacidade());
            cs.setString(4, recurso.getLocalizacao());
            cs.setByte(5, recurso.getEhLaboratorio());

            cs.executeUpdate();
        }
    }
}
