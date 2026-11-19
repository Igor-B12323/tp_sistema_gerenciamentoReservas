package br.com.empresa.model;

import java.time.*;

public class ReservaDTO {

    private int id;
    private int idRecurso;
    private String nomeRecurso;
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private LocalTime horaInicial;
    private LocalTime horaFinal;
    private int capacidade;
    private String localizacao;
    private String nomeUsuario;
    private String email;
    private String nomeStatusReserva;

    public ReservaDTO() {}

    public ReservaDTO(
            int id,
            LocalDate dataInicial,
            LocalDate dataFinal,
            LocalTime horaInicial,
            LocalTime horaFinal,
            int idRecurso,
            String nomeRecurso,
            int capacidade,
            String localizacao,
            String nomeUsuario,
            String email,
            String nomeStatusReserva) {
        this.id = id;
        this.idRecurso = idRecurso;
        this.nomeRecurso = nomeRecurso;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.horaInicial = horaInicial;
        this.horaFinal = horaFinal;
        this.capacidade = capacidade;
        this.localizacao = localizacao;
        this.nomeUsuario = nomeUsuario;
        this.email = email;
        this.nomeStatusReserva = nomeStatusReserva;
    }

    public int getId() {
        return id;
    }

    public void setId(int novoId) {
        this.id = novoId;
    }

    public LocalDate getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(LocalDate novaDataInicial) {
        this.dataInicial = novaDataInicial;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(LocalDate novaDataFinal) {
        this.dataFinal = novaDataFinal;
    }

    public LocalTime getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(LocalTime novaHoraInicial) {
        this.horaInicial = novaHoraInicial;
    }

    public LocalTime getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(LocalTime novaHoraFinal) {
        this.horaFinal = novaHoraFinal;
    }

    public int getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(int novoId) {
        this.idRecurso = novoId;
    }

    public String getNomeRecurso() {
        return nomeRecurso;
    }

    public void setNomeRecurso(String novoNomeRecurso) {
        this.nomeRecurso = novoNomeRecurso;
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

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String novoNomeUsuario) {
        this.nomeUsuario = novoNomeUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String novoEmail) {
        this.email = novoEmail;
    }

    public String getNomeStatusReserva() {
        return nomeStatusReserva;
    }

    public void setNomeStatusReserva(String novoNomeStatusReserva) {
        this.nomeStatusReserva = novoNomeStatusReserva;
    }
}
