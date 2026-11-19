package br.com.empresa.model;

public class StatusReserva {

    private int id;
    private String nome;
    private String descricao;

    public StatusReserva() {

    }

    public StatusReserva(int id, String nome, String descricao) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int novoId) {
        this.id = novoId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String novoNome) {
        this.nome = novoNome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return this.id + " - " + (this.nome != null ? this.nome : "");
    }
}
