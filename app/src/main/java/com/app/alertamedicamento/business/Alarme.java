package com.app.alertamedicamento.business;

import android.app.AlarmManager;

import com.app.alertamedicamento.util.Funcoes;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

public class Alarme {

    private int id = 0;
    private Timestamp horario = null;
    private String repeat = "S"; // S:Sim|N:Não
    private String nomeMedicamento = "";
    private String dosagem = "";
    private int hora = 0;
    private int minuto = 0;
    private String status = "A"; // A:Ativo|I:Inativo|P:Postergado
    private int postergar = 24;
    private Date dtLimite = null;
    private int contador = 0;

    public Alarme() {
        horario = Funcoes.getCurrentTimestamp();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRepeat() {
        return repeat;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    public String getNomeMedicamento() {
        return nomeMedicamento;
    }

    public void setNomeMedicamento(String nomeMedicamento) {
        this.nomeMedicamento = nomeMedicamento;
    }

    public Timestamp getHorario() {
        return horario;
    }

    public void setHorario(Timestamp horario) {
        this.horario = horario;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Alarme: " + id
                + ", "
                + nomeMedicamento
                + ", "
                + dosagem
                + ", "
                + hora+":"+minuto
                + ", "
                + "Repetir: " + repeat
                + ", "
                + "Intervalo: " + postergar
                + ", "
                + status
                ;
    }

    public int getHora() {
        return hora;
    }

    public int getMinuto() {
        return minuto;
    }

    public int getPostergar() {
        return postergar;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    public void setPostergar(int postergar) {
        this.postergar = postergar;
    }

    public Calendar getCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        calendar.set(Calendar.HOUR_OF_DAY, hora);
        calendar.set(Calendar.MINUTE, minuto);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.setTimeInMillis(calendar.getTimeInMillis() + (AlarmManager.INTERVAL_DAY + 1));
        }

        return calendar;
    }

    public Date getDtLimite() {
        if (dtLimite == null) {
            dtLimite = Funcoes.getCurrentDate();
        }
        return dtLimite;
    }

    public void setContador(int contador) {
        this.contador = contador;
    }

    public int getContador() {
        return contador;
    }

    public void setDtLimite(Date dtLimite) {
        this.dtLimite = dtLimite;
    }
}
