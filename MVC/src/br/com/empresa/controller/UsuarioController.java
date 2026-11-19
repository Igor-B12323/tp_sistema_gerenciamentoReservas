package br.com.empresa.controller;

import br.com.empresa.dao.UsuarioDAO;
import br.com.empresa.model.Usuario;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class UsuarioController {
    private final UsuarioDAO dao;

    public UsuarioController() {
        this.dao = new UsuarioDAO();
    }

    public List<Usuario> carregarTodos() throws SQLException {
        return dao.listarTodos();
    }

    public boolean incluir(
            String nome, String cpf, LocalDate dataAniversario, String celular, String email)
            throws Exception {

        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Falta nome do usuário!");
        }

        if (cpf == null || cpf.trim().isEmpty()) {
            throw new IllegalArgumentException("Falta CPF do usuário!");
        }

        if (celular == null || celular.trim().isEmpty()) {
            throw new IllegalArgumentException("Falta celular do usuário!");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Falta email do usuário!");
        }

        LocalDate data = (dataAniversario != null) ? dataAniversario : null;

        Usuario user = new Usuario();
        user.setNome(nome);
        user.setCpf(cpf);
        user.setDataAniversario(data);
        user.setCelular(celular);
        user.setEmail(email);

        Usuario existente = dao.existe(nome);
        if (existente == null) {
            dao.inserir(user);
            return true;
        } else return false;
    }

    public boolean alterar(
            Integer id,
            String nome,
            String cpf,
            LocalDate dataAniversario,
            String celular,
            String email)
            throws Exception {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Falta id do usuário / id inválido!");
        }

        Usuario user = new Usuario(id, nome, cpf, dataAniversario, celular, email);

        Usuario existente = dao.existe(id);
        if (existente != null) {
            dao.atualizar(user);
            return true;
        } else{
            throw new IllegalArgumentException("Usuário inexistente!");
        }
    }

    public boolean excluir(Integer id) throws Exception {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido!");
        }
        dao.excluir(id);
        return true;
    }

    public Usuario buscar(String nome) throws Exception {
        // só para termos uma lógica de negócio
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Falta nome do usuário!");
        }
        return dao.existe(nome);
    }
}
