package br.com.empresa.dao;

import br.com.empresa.model.Usuario;
import java.sql.*;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public List<Usuario> listarTodos() throws SQLException {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM pj6.usuario ORDER BY nome";

        try (Connection conexao = ConexaoBD.getConnection();
                Statement comandoSQL = conexao.createStatement();
                ResultSet registros = comandoSQL.executeQuery(sql)) {

            while (registros.next()) {
                LocalDate campoDeData = null;
                Date dateVal = registros.getDate("dataAniversario");
                if (dateVal != null) {
                    campoDeData = dateVal.toLocalDate();
                }
                Usuario usuarioLido =
                        new Usuario(
                                registros.getInt("id"),
                                registros.getString("nome"),
                                registros.getString("cpf"),
                                campoDeData,
                                registros.getString("celular"),
                                registros.getString("email"));
                lista.add(usuarioLido);
            }
        }
        return lista;
    }

    public Usuario existe(String nomeProcurado) throws SQLException {
        String sql = "SELECT * FROM pj6.usuario WHERE nome LIKE ?";

        try (Connection conexao = ConexaoBD.getConnection();
                PreparedStatement comandoSQL = conexao.prepareStatement(sql)) {

            comandoSQL.setString(1, "%" + nomeProcurado + "%");

            try (ResultSet registros = comandoSQL.executeQuery()) {
                if (registros.next()) {
                    LocalDate campoDeData = null;
                    Date dateVal = registros.getDate("dataAniversario");
                    if (dateVal != null) {
                        campoDeData = dateVal.toLocalDate();
                    }

                    return new Usuario(
                            registros.getInt("id"),
                            registros.getString("nome"),
                            registros.getString("cpf"),
                            campoDeData,
                            registros.getString("celular"),
                            registros.getString("email"));
                }
            }
        }
        return null;
    }

    public Usuario existe(int idProcurado) throws SQLException {
        String sql = "SELECT * FROM pj6.usuario WHERE id = ?";

        try (Connection conexao = ConexaoBD.getConnection();
                PreparedStatement comandoSQL = conexao.prepareStatement(sql)) {

            comandoSQL.setInt(1, idProcurado);

            try (ResultSet registros = comandoSQL.executeQuery()) {
                if (registros.next()) {
                    LocalDate campoDeData = null;
                    Date dateVal = registros.getDate("dataAniversario");
                    if (dateVal != null) {
                        campoDeData = dateVal.toLocalDate();
                    }

                    return new Usuario(
                            registros.getInt("id"),
                            registros.getString("nome"),
                            registros.getString("cpf"),                          
                            campoDeData,
                            registros.getString("celular"),
                            registros.getString("email"));
                }
            }
        }
        return null;
    }

    public void inserir(Usuario usuario) throws SQLException {
        String sql = "{call pj6.proc_inserirUsuario(?, ?, ?, ?, ?)}";

        try (Connection conexao = ConexaoBD.getConnection();
                CallableStatement cs = conexao.prepareCall(sql)) {

            cs.setString(1, usuario.getNome());
            cs.setString(2, usuario.getCpf());

            if (usuario.getDataAniversario() != null) {
                cs.setDate(3, Date.valueOf(usuario.getDataAniversario()));
            } else {
                cs.setNull(3, Types.DATE);
            }

            cs.setString(4, usuario.getCelular());
            cs.setString(5, usuario.getEmail());

            cs.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "{call pj6.proc_excluirUsuario(?)}";

        try (Connection conexao = ConexaoBD.getConnection();
                CallableStatement cs = conexao.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.executeUpdate();
        }
    }

    public void atualizar(Usuario usuario) throws SQLException {
        String sql = "{call pj6.proc_atualizarUsuario(?, ?, ?, ?, ?, ?)}";

        try (Connection conexao = ConexaoBD.getConnection();
                CallableStatement cs = conexao.prepareCall(sql)) {

            cs.setInt(1, usuario.getId());
            cs.setString(2, usuario.getNome());
            cs.setString(3, usuario.getCpf());

            if (usuario.getDataAniversario() != null) {
                cs.setDate(4, Date.valueOf(usuario.getDataAniversario()));
            } else {
                cs.setNull(4, Types.DATE);
            }

            cs.setString(5, usuario.getCelular());
            cs.setString(6, usuario.getEmail());

            cs.executeUpdate();
        }
    }
}
