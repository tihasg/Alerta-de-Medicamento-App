package com.app.alertamedicamento.dao.db;

/**
 * Estrutura da Base de Dados
 */
public enum SYS_DB {

    TABELAS(new Tupla[]{
            new Tupla(0, "TABELA", Tipo.VARCHAR),
            new Tupla(1, "ATUALIZACAO", Tipo.TIMESTAMP)

    }),

    ALARME(new Tupla[]{
            new Tupla(0, "ID", Tipo.INT),
            new Tupla(1, "HORARIO", Tipo.TIMESTAMP),
            new Tupla(2, "REPEAT", Tipo.VARCHAR, 1),
            new Tupla(3, "NOME_MEDICAMENTO", Tipo.VARCHAR, 256),
            new Tupla(4, "DOSAGEM", Tipo.VARCHAR, 256),
            new Tupla(5, "HORA", Tipo.INT),
            new Tupla(6, "MINUTO", Tipo.INT),
            new Tupla(7, "STATUS", Tipo.VARCHAR, 1),
            new Tupla(8, "POSTERGAR", Tipo.INT),
            new Tupla(9, "DT_LIMITE", Tipo.DATE),
            new Tupla(10, "CONTADOR", Tipo.INT),

    }),

    VERSAO_APP(new Tupla[]{
            new Tupla(0, "VERSAO", Tipo.INTEGER),
            new Tupla(1, "DATA_HORA", Tipo.TIMESTAMP),
            new Tupla(2, "TEXTO", Tipo.VARCHAR, 2048)

    }),;

    private Tupla[] tuplas = null;
    private String[] cols = null;

    private SYS_DB(Tupla[] tuplas) {
        this.tuplas = tuplas;
    }

    public static int getVersao() {
        return 5;
    }

    public Tupla[] getTuplas() {
        return tuplas;
    }

    public void setTuplas(Tupla[] tuplas) {
        this.tuplas = tuplas;
    }

    public String[] cols() {
        if (cols == null) {
            cols = new String[tuplas.length];

            for (int i = 0; i < cols.length; i++) {
                cols[i] = tuplas[i].getNome();
            }
        }

        return cols;
    }

    /**
     * @param whereIndex
     * @return
     */
    public String getSelectSQL(int... whereIndex) {
        String params = "";

        for (int i = 0; i < whereIndex.length; i++) {
            String op = "";
            if ((this.tuplas[whereIndex[i]].getTipo() == Tipo.VARCHAR) || (this.tuplas[whereIndex[i]].getTipo() == Tipo.CHAR)) {
                op = " like ?";
            } else {
                op = " =?";
            }

            if (params.equals("")) {
                params = this.tuplas[whereIndex[i]].getNome().concat(op);
            } else {
                params = params.concat(" AND ").concat(this.tuplas[whereIndex[i]].getNome()).concat(op);
            }
        }

        String tuplas = "";

        for (int i = 0; i < this.tuplas.length; i++) {
            if (tuplas.equals("")) {
                tuplas = this.tuplas[i].getNome();
            } else {
                tuplas = tuplas.concat(", ").concat(this.tuplas[i].getNome());
            }
        }

        if (params.equals("")) {
            return String.format("SELECT %s FROM %s", tuplas, this.name());
        } else {
            return String.format("SELECT %s FROM %s WHERE %s", tuplas, this.name(), params);
        }

    }

    /**
     * @param whereIndex
     * @return
     */
    public String getDeleteSQL(int... whereIndex) {
        String params = "";

        for (int i = 0; i < whereIndex.length; i++) {
            if (params.equals("")) {
                params = this.tuplas[whereIndex[i]].getNome().concat("=?");
            } else {
                params = params.concat(" AND ").concat(this.tuplas[whereIndex[i]].getNome()).concat("=?");
            }
        }
        return String.format("DELETE FROM %s WHERE %s;", this.name(), params);
    }

    public String getInsertSQL() {
        String tuplas = "";
        String params = "";

        for (int i = 0; i < this.tuplas.length; i++) {
            if (tuplas.equals("")) {
                tuplas = this.tuplas[i].getNome();
                params = "?";
            } else {
                tuplas = tuplas.concat(", ").concat(this.tuplas[i].getNome());
                params = params.concat(", ?");
            }
        }

        return String.format("INSERT INTO %s (%s) VALUES (%s);", this.name(),
                tuplas, params);
    }

    /**
     * @param whereIndex
     * @return
     */
    public String getUpdateSQL(int... whereIndex) {
        String tuplas = "";
        String params = "";

        for (int i = 0; i < whereIndex.length; i++) {
            if (params.equals("")) {
                params = this.tuplas[whereIndex[i]].getNome().concat("=?");
            } else {
                params = params.concat(" AND ").concat(this.tuplas[whereIndex[i]].getNome()).concat("=?");
            }
        }

        for (int i = 0; i < this.tuplas.length; i++) {
            if (!contemArray(whereIndex, i)) {
                if (tuplas.equals("")) {
                    tuplas = this.tuplas[i].getNome().concat("=?");
                } else {
                    tuplas = tuplas.concat(", ")
                            .concat(this.tuplas[i].getNome()).concat("=?");
                }
            }
        }

        return String.format("UPDATE %s SET %s WHERE %s;", this.name(), tuplas,
                params);
    }

    /**
     * @param whereIndex
     * @param value
     * @return
     */
    private boolean contemArray(int[] whereIndex, int value) {
        for (int j = 0; j < whereIndex.length; j++) {
            if (whereIndex[j] == value) {
                return true;
            }
        }
        return false;
    }

    public String getCreateSQL() {
        String tuplas = "";

        for (int i = 0; i < this.tuplas.length; i++) {
            if (tuplas.equals("")) {
                tuplas = String.format("%s %s", this.tuplas[i].getNome(), this.tuplas[i].getStrTipo());
            } else {
                tuplas = tuplas.concat(", ").concat(String.format("%s %s", this.tuplas[i].getNome(), this.tuplas[i].getStrTipo()));
            }
        }

        return String.format("CREATE TABLE %s ( %s );", this.name(), tuplas);
    }

    public String getDropSQL() {
        return String.format("DROP TABLE IF EXISTS %s;", this.name());
    }

}
