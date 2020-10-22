package com.app.alertamedicamento.util;

/**
 * @author Desconhecido
 * @company Desconhecido
 */
public enum Tipo {

    TEXTO, NUMERICO(",0.0000", "="),
    DATA,
    DATA_1,
    HORA("dd/MM/yyyy", ""),
    DATA_HORA("dd/MM/yyyy - hh:mm", "="),
    DATA_HORA_D("dd/MM/yyyy - hh:mm", "="),
    MOEDA(",0.00", "="),
    MOEDA_2(",0.00", "="),
    MOEDA_3(",0.000", "="),
    MOEDA_4(",0.0000", "="),
    QTADE(",0.000", "="),
    QTADE_0("0000", "="),
    QTADE_1(",0.0", "="),
    QTADE_2(",0.00", "="),
    QTADE_3(",0.000", "="),
    INTEIRO("", "="),
    SIM_NAO("", "="),
    X("", "="),
    CODIGO("", "="),
    CPF_CNPJ,
    CEP,
    FONE,
    GRAUS,
    PERCENTUAL,
    MM;
    private String mascara = "";
    private String operador = "like";

    private Tipo() {
        this("", "like");
    }

    private Tipo(String mascara) {
        this(mascara, "like");
    }

    private Tipo(String mascara, String operador) {
        this.mascara = mascara;
        this.operador = operador;
    }

    public String getMascara() {
        return mascara;
    }

    public String getOperador() {
        return operador;
    }
}
