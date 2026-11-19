package br.com.empresa.controller;

import br.com.empresa.dao.AcessoDAO;
import br.com.empresa.model.Acesso;
import br.com.empresa.model.Usuario;
import java.sql.SQLException;
import java.time.*;
import java.util.Date;
import java.util.List;

public class AcessoController {
    private final AcessoDAO dao;

    public AcessoController() {
        this.dao = new AcessoDAO();
    }

    public List<Acesso> listarTodos() throws SQLException {
        return dao.listarTodos();
    }

    public List<Object[]> historicoAcessos(Integer idProcurado) throws SQLException {
        return dao.listarHistoricoAcessos(idProcurado);
    }

    public boolean incluir(
            String login, String senha, Integer id_usuario)
            throws Exception {

        // lógica de negócio sobre os dados vindos da View
        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("Falta login do acesso!");
        }

        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Falta senha do acesso!");
        }

        if (id_usuario == null) {
            throw new IllegalArgumentException("Falta id do usuário!");
        }

        Date data = new Date();

        LocalDate dataCadastro = LocalDate.ofInstant(data.toInstant(), ZoneId.systemDefault());

        Acesso ace = new Acesso();
        ace.setLogin(login);
        ace.setSenha(senha);
        ace.setDataCadastro(dataCadastro);
        ace.setIdUsuario(id_usuario);

        Acesso existente = dao.existe(id_usuario);
        if (existente == null) {
            dao.inserir(ace);
            return true;
        } else return false;
    }

    public boolean alterar(
            Integer id, String login, String senha, LocalDate dataCadastro, Integer id_usuario)
            throws Exception {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Falta id do acesso!");
        }

        Acesso ace = new Acesso();

        ace.setId(id);
        ace.setLogin(login);
        ace.setSenha(senha);
        ace.setDataCadastro(dataCadastro);

        Acesso existente = dao.existe(id_usuario);
        if (existente != null) {
            dao.atualizar(ace);
            return true;
        } else return false;
    }

    public boolean excluir(Integer id) throws Exception {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido!");
        }
        dao.excluir(id);
        return true;
    }

    public Usuario autenticarAcesso(String login, String senha) throws Exception {
        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("Falta login do acesso!");
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new IllegalArgumentException("Falta senha do acesso!");
        }
        return dao.autenticar(login, senha);
    }

    public Acesso buscar(Integer id_usuario) throws Exception{
        if(id_usuario == null){
            throw new IllegalArgumentException("Falta id do usuário!");
        }
        return dao.existe(id_usuario);
    }
}

