package com.app.alertamedicamento.business;

import com.app.alertamedicamento.util.Funcoes;

import java.io.Serializable;
import java.sql.Timestamp;

public class VersaoApp implements Serializable {

    private static final long serialVersionUID = 1L;

    private int versao;
    private Timestamp dataHora;
    private String texto;

    public VersaoApp() {
        this.versao = 0;
        this.dataHora = Funcoes.getCurrentTimestamp();
        this.texto = "";
    }

    public Timestamp getDataHora() {
        return dataHora;
    }

    public void setDataHora(Timestamp dataHora) {
        this.dataHora = dataHora;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public int getVersao() {
        return versao;
    }

    public void setVersao(int versao) {
        this.versao = versao;
    }

}
