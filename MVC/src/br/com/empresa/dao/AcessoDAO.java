package br.com.empresa.dao;

import br.com.empresa.model.Acesso;
import br.com.empresa.model.Usuario;
import java.sql.*;
import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class AcessoDAO {

    public List<Acesso> listarTodos() throws SQLException {
        List<Acesso> lista = new ArrayList<>();
        String sql = "SELECT * FROM pj6.Acesso";

        try (Connection conexao = ConexaoBD.getConnection();
                Statement comandoSQL = conexao.createStatement();
                ResultSet registros = comandoSQL.executeQuery(sql)) {

            while (registros.next()) {

                LocalDate dtCad = null;
                Date campoData = registros.getDate("dataCadastro");

                if (campoData != null){
                    dtCad = campoData.toLocalDate();
                }
            
                var acessoLido =
                        new Acesso(
                                registros.getInt("id"),
                                registros.getString("login"),
                                registros.getString("senha"),
                                dtCad,
                                registros.getInt("id_usuario"));
                lista.add(acessoLido);
            }
        }
        return lista;
    }

    public List<Object[]> listarHistoricoAcessos(Integer idProcurado) throws SQLException {
        List<Object[]> lista = new ArrayList<>();
        String sql = "SELECT * FROM pj6.vw_acessoUsuario";
        if(idProcurado != null){
            sql += (" WHERE id_usuario = ?");
        }

        try (Connection conexao = ConexaoBD.getConnection();
                PreparedStatement comandoSQL = conexao.prepareStatement(sql)) {

            if (idProcurado != null) {
                comandoSQL.setInt(1, idProcurado);
            }

            try(ResultSet registros = comandoSQL.executeQuery()){

                while (registros.next()) {

                    Timestamp dataEHora = registros.getTimestamp("data_acesso");

                    Object[] acessoLido = new Object[]{        
                                    registros.getInt("id_auditoria"),
                                    (dataEHora != null) ? dataEHora.toLocalDateTime() : null,
                                    registros.getString("login_usuario"),
                                    registros.getInt("id_usuario"),
                                    registros.getString("nome_usuario")
                                    };
                    lista.add(acessoLido);
                }
            }
        }
        return lista;
    }

    public Usuario autenticar(String loginProcurado, String senhaProcurada) throws SQLException {
        String sql = "EXEC pj6.proc_autenticarAcesso @login = ?, @senha = ?";

        try (Connection conexao = ConexaoBD.getConnection();
                PreparedStatement comandoSQL = conexao.prepareStatement(sql)) {

            comandoSQL.setString(1, loginProcurado);
            comandoSQL.setString(2, senhaProcurada);

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

    public Acesso existe(int idProcurado) throws SQLException {
        String sql = "SELECT * FROM pj6.acesso WHERE id_usuario = ?";

        try (Connection conexao = ConexaoBD.getConnection();
                PreparedStatement comandoSQL = conexao.prepareStatement(sql)) {

            comandoSQL.setInt(1, idProcurado);

            try (ResultSet registros = comandoSQL.executeQuery()) {
                if (registros.next()) {
                    LocalDate campoDeData = null;
                    Date dateVal = registros.getDate("dataCadastro");
                    if (dateVal != null) {
                        campoDeData = dateVal.toLocalDate();
                    }

                    return new Acesso(
                            registros.getInt("id"),
                            registros.getString("login"),
                            registros.getString("senha"),
                            campoDeData,
                            registros.getInt("id_usuario"));
                }
            }
        }
        return null;
    }

    public void inserir(Acesso acesso) throws SQLException {
        String sql = "{call pj6.proc_inserirAcesso(?, ?, ?, ?)}";

        try (Connection conexao = ConexaoBD.getConnection();
                CallableStatement cs = conexao.prepareCall(sql)) {

            cs.setString(1, acesso.getLogin());
            cs.setString(2, acesso.getSenha());
            if (acesso.getDataCadastro() != null) {
                cs.setDate(3, java.sql.Date.valueOf(acesso.getDataCadastro()));
            } else {
                cs.setNull(3, java.sql.Types.DATE);
            }
            cs.setInt(4, acesso.getIdUsuario());

            cs.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "{call pj6.proc_excluirAcesso(?)}";

        try (Connection conexao = ConexaoBD.getConnection();
                CallableStatement cs = conexao.prepareCall(sql)) {

            cs.setInt(1, id);
            cs.executeUpdate();
        }
    }

    public void atualizar(Acesso acesso) throws SQLException {
        String sql = "{call pj6.proc_atualizarAcesso(?, ?, ?, ?)}";

        try (Connection conexao = ConexaoBD.getConnection();
                CallableStatement cs = conexao.prepareCall(sql)) {

            cs.setInt(1, acesso.getId());
            cs.setString(2, acesso.getLogin());
            cs.setString(3, acesso.getSenha());

            if (acesso.getDataCadastro() != null) {
                cs.setDate(4, java.sql.Date.valueOf(acesso.getDataCadastro()));
            } else {
                cs.setNull(4, java.sql.Types.DATE);
            }

            cs.executeUpdate();
        }
    }

}
