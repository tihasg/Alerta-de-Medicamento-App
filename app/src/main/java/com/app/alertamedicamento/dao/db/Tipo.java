package com.app.alertamedicamento.dao.db;

public enum Tipo {
    VARCHAR("VARCHAR"),
    SMALLINT("SMALLINT"),
    INT("INT"),
    FLOAT("FLOAT"),
    DATE("DATE"),
    TIME("TIME"),
    TIMESTAMP("TIMESTAMP"),
    CHAR("CHAR"),
    DOUBLE("DOUBLE PRECISION"),
    BLOB("BLOB"),
    BYTEA("BYTEA"),
    INTEGER("INTEGER"),;

    private String value;

    private Tipo(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value;
    }

}
