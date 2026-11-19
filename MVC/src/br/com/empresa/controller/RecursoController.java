package br.com.empresa.controller;

import br.com.empresa.dao.RecursoDAO;
import br.com.empresa.model.Recurso;
import java.sql.SQLException;
import java.util.List;

public class RecursoController {
    private final RecursoDAO dao;

    public RecursoController() {
        this.dao = new RecursoDAO();
    }

    public List<Recurso> carregarTodos() throws SQLException {
        return dao.listarTodos();
    }

    public boolean incluir(String nome, Integer capacidade, String localizacao, byte ehLaboratorio)
            throws Exception {

        // lógica de negócio sobre os dados vindos da View
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Falta nome do recurso!");
        }

        if (capacidade == null) {
            throw new IllegalArgumentException("Falta capacidade do recurso!");
        }

        if (localizacao == null || localizacao.trim().isEmpty()) {
            throw new IllegalArgumentException("Falta localização do recurso!");
        }

        if (ehLaboratorio != 0 && ehLaboratorio != 1) {
            throw new IllegalArgumentException("O valor deve ser 1(true) ou 0(false)");
        }

        Recurso rec = new Recurso();
        rec.setNome(nome);
        rec.setCapacidade(capacidade);
        rec.setLocalizacao(localizacao);
        rec.setEhLaboratorio(ehLaboratorio);

        // Verifica se já existe para decidir se pode inserir
        Recurso existente = dao.existe(nome);
        if (existente == null) {
            dao.inserir(rec);
            return true;
        } else {
            throw new IllegalArgumentException("Já existe um recurso cadastrado com este nome!");
        }
    }

    public boolean alterar(
            Integer id, String nome, Integer capacidade, String localizacao, byte ehLaboratorio)
            throws Exception {

        // lógica de negócio sobre os dados vindos da View
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Falta id do recurso!");
        }

        Recurso rec = new Recurso(id, nome, capacidade, localizacao, ehLaboratorio);

        Recurso existente = dao.existe(id);
        if (existente != null) {
            dao.atualizar(rec);
            return true;
        } else {
            throw new IllegalArgumentException("Não existe um recurso cadastrado com este nome!");
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

    public Recurso buscar(String nome) throws Exception {
        // só para termos uma lógica de negócio
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Falta nome do recurso!");
        }
        return dao.existe(nome);
    }
}
