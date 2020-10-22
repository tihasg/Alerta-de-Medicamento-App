package com.app.alertamedicamento.dao.db;

import java.io.Serializable;

public class Tupla implements Serializable {

    private static final long serialVersionUID = 1L;

    private final int indice;
    private final String nome;
    private final Tipo tipo;
    private final int tamanho;
    private final boolean requerido;
    private final boolean unico;

    /**
     * @param indice
     * @param nome
     * @param tipo
     */
    public Tupla(int indice, String nome, Tipo tipo) {
        this(indice, nome, tipo, 0, false, false);
    }

    /**
     * @param indice
     * @param nome
     * @param tipo
     * @param tamanho
     */
    public Tupla(int indice, String nome, Tipo tipo, int tamanho) {
        this(indice, nome, tipo, tamanho, false, false);
    }

    /**
     * @param indice
     * @param nome
     * @param tipo
     * @param requerido
     */
    public Tupla(int indice, String nome, Tipo tipo, boolean requerido) {
        this(indice, nome, tipo, 0, requerido, false);
    }

    /**
     * @param indice
     * @param nome
     * @param tipo
     * @param tamanho
     * @param requerido
     * @param unico
     */
    public Tupla(int indice, String nome, Tipo tipo, int tamanho,
                 boolean requerido, boolean unico) {
        this.indice = indice;
        this.nome = nome;
        this.tipo = tipo;
        this.tamanho = tamanho;
        this.requerido = requerido;
        this.unico = unico;
    }

    /**
     * @param indice
     * @param nome
     * @param tipo
     * @param tamanho
     * @param requerido
     */
    public Tupla(int indice, String nome, Tipo tipo, int tamanho,
                 boolean requerido) {
        this(indice, nome, tipo, tamanho, requerido, false);
    }

    public int getIndice() {
        return indice;
    }

    public String getNome() {
        return nome;
    }

    public int getTamanho() {
        return tamanho;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public boolean isRequerido() {
        return requerido;
    }

    public boolean isUnico() {
        return unico;
    }

    public String getStrTipo() {
        String tipo = "";
        tipo = this.tipo.getValue();
        if (this.tamanho > 0) {
            tipo = tipo.concat("(" + this.tamanho + ")");
        }
        if (this.requerido) {
            tipo = tipo.concat(" NOT NULL");
        }
        return tipo;
    }

    public String getStrOperador() {
        String value = "";

        if (tipo == Tipo.INTEGER || tipo == Tipo.INT) {
            value = "=";
        } else {
            value = "LIKE";
        }

        return value;
    }
}
