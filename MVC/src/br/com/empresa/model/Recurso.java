package br.com.empresa.model;

public class Recurso {

    private int id;
    private String nome;
    private int capacidade;
    private String localizacao;
    private byte ehLaboratorio;

    public Recurso() {}

    public Recurso(int id, String nome, int capacidade, String localizacao, byte ehLaboratorio) {
        this.id = id;
        this.nome = nome;
        this.capacidade = capacidade;
        this.localizacao = localizacao;
        this.ehLaboratorio = ehLaboratorio;
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

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int novaCapacidade) {
        this.capacidade = novaCapacidade;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String novaLocalizacao) {
        this.localizacao = novaLocalizacao;
    }

    public byte getEhLaboratorio() {
        return ehLaboratorio;
    }

    public void setEhLaboratorio(byte novoEhLab) {
        this.ehLaboratorio = novoEhLab;
    }

    @Override
    public String toString() {
        return this.id + " - " + (this.nome != null ? this.nome : "");
    }
}
