package br.com.empresa.model;

import java.time.LocalDate;

public class Usuario {

    private int id;
    private String cpf;
    private String nome;
    private LocalDate dataAniversario;
    private String celular;
    private String email;

    public Usuario() {}

    public Usuario(
            int id,
            String nome,
            String cpf,
            LocalDate dataAniversario,
            String celular,
            String email) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.dataAniversario = dataAniversario;
        this.celular = celular;
        this.email = email;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public void setId(int novoId) {
        this.id = novoId;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String novoCpf) {
        this.cpf = novoCpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String novoNome) {
        this.nome = novoNome;
    }

    public LocalDate getDataAniversario() {
        return dataAniversario;
    }

    public void setDataAniversario(LocalDate novoDataAniversario) {
        this.dataAniversario = novoDataAniversario;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String novoCelular) {
        this.celular = novoCelular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String novoEmail) {
        this.email = novoEmail;
    }

    @Override
    public String toString() {
        // Retorna "ID - Nome" no JComboBox (ex: "1 - João Silva")
        return this.id + " - " + (this.nome != null ? this.nome : "");
    }
}

