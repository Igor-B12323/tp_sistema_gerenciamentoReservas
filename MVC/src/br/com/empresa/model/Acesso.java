package br.com.empresa.model;

import java.time.LocalDate;

public class Acesso {
    private int id;
    private String login;
    private String senha;
    private LocalDate dataCadastro;
    private int idUsuario;

    public Acesso() {}

    public Acesso(int id, String login, String senha, LocalDate dataCadastro, int idUsuario) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.dataCadastro = dataCadastro;
        this.idUsuario = idUsuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int novoId) {
        this.id = novoId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String novoLogin) {
        this.login = novoLogin;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String novoSenha) {
        this.senha = novoSenha;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate novoDataCadastro) {
        this.dataCadastro = novoDataCadastro;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int novoId) {
        this.idUsuario = novoId;
    }
}
