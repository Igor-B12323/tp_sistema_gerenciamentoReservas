package br.com.empresa.model;

import java.time.*;

public class Reserva {

    private int id;
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private LocalTime horaInicial;
    private LocalTime horaFinal;
    private int idRecurso;
    private int idUsuario;
    private int idStatusReserva;

    public Reserva() {}

    public Reserva(
            int id,
            LocalDate dataInicial,
            LocalDate dataFinal,
            LocalTime horaInicial,
            LocalTime horaFinal,
            int idRecurso,
            int idUsuario,
            int idStatusReserva) {
        this.id = id;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.horaInicial = horaInicial;
        this.horaFinal = horaFinal;
        this.idRecurso = idRecurso;
        this.idUsuario = idUsuario;
        this.idStatusReserva = idStatusReserva;
    }

    public int getId() {
        return id;
    }

    public void setId(int novoId) {
        this.id = novoId;
    }

    public LocalDate getDataInicial(){
        return dataInicial;
    }

    public void setDataInicial(LocalDate novaDataInicial){
        this.dataInicial = novaDataInicial;
    }

    public LocalDate getDataFinal(){
        return dataFinal;
    }

    public void setDataFinal(LocalDate novaDataFinal){
        this.dataFinal = novaDataFinal;
    }

    public LocalTime getHoraInicial(){
        return horaInicial;
    }

    public void setHoraInicial(LocalTime novaHoraInicial){
        this.horaInicial = novaHoraInicial;
    }

    public LocalTime getHoraFinal(){
        return horaFinal;
    }

    public void setHoraFinal(LocalTime novaHoraFinal){
        this.horaFinal = novaHoraFinal;
    }

    public int getIdRecurso() {
        return idRecurso;
    }

    public void setIdRecurso(int novoIdRecurso) {
        this.idRecurso = novoIdRecurso;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int novoIdUsuario) {
        this.idUsuario = novoIdUsuario;
    }

    public int getIdStatusReserva() {
        return idStatusReserva;
    }

    public void setIdStatusReserva(int novoIdStatusReserva) {
        this.idStatusReserva = novoIdStatusReserva;
    }
}
