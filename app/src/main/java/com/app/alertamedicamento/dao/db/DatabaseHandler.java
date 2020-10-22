package com.app.alertamedicamento.dao.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.app.alertamedicamento.business.VersaoApp;
import com.app.alertamedicamento.dao.VersaoDB;
import com.app.alertamedicamento.util.Funcoes;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Conexao e Gerenciamento da Base de Dados.
 */
public abstract class DatabaseHandler extends SQLiteOpenHelper implements
        IDataSet {

    public static final String DATABASE_NAME = "DB_COLETOR";
    protected Cursor query = null;
    protected ContentValues values = null;
    protected Context context = null;
    protected SYS_DB _tabela = null;
    protected String _fieldID = "ID";
    protected String _uniqueField = "ID";
    protected SQLiteDatabase _db = null;

    public DatabaseHandler(Context context) {
        this(context, null, "ID", "");
    }

    protected DatabaseHandler(Context context, SYS_DB _tabela) {
        this(context, _tabela, _tabela.cols()[0], _tabela.cols()[0]);
    }

    protected DatabaseHandler(Context context, SYS_DB _tabela, String _fieldID) {
        this(context, _tabela, _fieldID, _fieldID);
    }

    protected DatabaseHandler(Context context, SYS_DB _tabela, String _fieldID, String _uniqueField) {
        super(context, DATABASE_NAME, null, SYS_DB.getVersao());
        this.context = context;
        this._tabela = _tabela;
        this._fieldID = _fieldID;
        this._uniqueField = _uniqueField;
    }

    public SYS_DB getTabela() {
        return _tabela;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(toString(), "onCreate()");

        this._db = db;

        for (int i = 0; i < SYS_DB.values().length; i++) {
            String script = SYS_DB.values()[i].getCreateSQL();
            db.execSQL(script);
            Log.i("DDL", script);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(toString(), "onUpgrade()");

        if (newVersion <= 100) {
            for (int i = 0; i < SYS_DB.values().length; i++) {
                String script = SYS_DB.values()[i].getDropSQL();
                db.execSQL(script);
                Log.i("DDL", script);
            }

            onCreate(db);
        } else if (newVersion > 100) {
        }

    }

    protected void registraScript(SQLiteDatabase db, String sql, int versao) {
        if (sql.equals("")) return;

        // Validar se existe a respectiva versao
        VersaoDB versaoDB = new VersaoDB(context);
        VersaoApp ver = (VersaoApp) versaoDB.get(versao);

        if (ver == null) {
            // Registrar versao...

            try {
                db.execSQL(sql);
                Log.i("DDL", sql);
            } catch (Exception e) {
                Log.i("regScript", e.getMessage());
            }

            ver = new VersaoApp();
            ver.setDataHora(Funcoes.getCurrentTimestamp());
            ver.setVersao(versao);

            versaoDB.gravar(ver);
        }

    }

    protected void setContentValues() {
        Log.i(toString(), "setContentValues()");

        this.values = new ContentValues();
        this.values.clear();
    }

    protected void clearValues() {
        Log.i(toString(), "clearValues()");

        if (this.values != null) {
            this.values.clear();
            this.values = null;
        }
    }

    protected Cursor getQuery(String sql) {
        Log.i(toString(), "getQuery()");

        return getReadableDatabase().rawQuery(sql, null);
    }

    protected boolean execQuery(String sql, Object... objects) {
        Log.i(toString(), "execQuery()");

        try {
            String[] values = new String[objects.length];

            for (int i = 0; i < objects.length; i++) {
                values[i] = objects[i].toString();
            }

            getWritableDatabase().execSQL(sql, values);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public synchronized SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

    @Override
    public synchronized SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    protected Cursor getQuery(String sql, Object... objects) {
        Log.i(toString(), "getQuery()");

        String[] values = new String[objects.length];

        for (int i = 0; i < objects.length; i++) {
            values[i] = objects[i].toString();
        }

        return getReadableDatabase().rawQuery(sql, values);
    }

    protected boolean insere(String table, ContentValues values) {
        Log.i(toString(), "insere()");

        return getWritableDatabase().insert(table, null, values) > -1;
    }

    protected boolean altera(String table, ContentValues values) {
        Log.i(toString(), "altera()");

        return getWritableDatabase().update(table, values, _fieldID + "=?",
                new String[]{values.getAsString(_fieldID)}) > -1;
    }

    protected boolean deleta(String table, String whereClause,
                             String... whereArgs) {
        Log.i(toString(), "deleta()");

        try {
            return getWritableDatabase().delete(table, whereClause, whereArgs) > -1;
        } catch (Exception e) {
            Log.e(toString(), e.getMessage());
            return false;
        }
    }

    protected boolean existeID(String table, int id) {
        Log.i(toString(), "existeID()");

        return existeRegistro(table, _fieldID, "=", Funcoes.getFmtValue(com.app.alertamedicamento.util.Tipo.INTEIRO, id));
    }

    protected boolean existeValue(String table, String _field, boolean flag,
                                  String... values) {
        Log.i(toString(), "existeID()");

        String[] fields = _field.split(";");

        return existeRegistro(table, fields, "like", values);
    }

    private boolean existeRegistro(String table, String[] fields,
                                   String operador, String[] values) {
        Log.i(toString(), "existeRegistro()");

        try {
            String condicao = " WHERE ";
            String value = "";
            operador = operador.trim();

            for (int i = 0; i < values.length; i++) {

                value = Funcoes.getFmtValue(com.app.alertamedicamento.util.Tipo.TEXTO, values[i].trim());
                if (operador.equals("like")) {
                    value = "'%" + value + "%'";
                } else if (operador.equals("=")) {
                    if (!Funcoes.ehInteiro(value)) {
                        value = "'" + value + "'";
                    }
                }

                if (!value.equals("")) {
                    if (i > 0) {
                        condicao = condicao + " AND ";
                    }
                    condicao = condicao
                            + String.format("%s %s %s", fields[i], operador,
                            value);
                }

            }
            query = getQuery(String
                    .format("SELECT COALESCE(COUNT(*), 0) FROM %s %s;", table,
                            condicao));
            query.moveToFirst();

            return (query.getInt(0) > 0);
        } catch (Exception e) {
            Log.e(toString(), e.getMessage());
            return false;
        } finally {
            query.close();
        }
    }

    protected boolean existeValue(String table, String _field, String value) {
        Log.i(toString(), "existeID()");

        return existeRegistro(table, _field, " like  ", Funcoes.getFmtValue(com.app.alertamedicamento.util.Tipo.TEXTO, "'" + value + "'"));
    }

    protected boolean existeRegistro(String table, String field,
                                     String operador, String value) {
        Log.i(toString(), "existeRegistro()");

        try {
            value = value.trim();
            if (operador.equals("like")) {
                value = "'%" + value + "%'";
            } else if (operador.equals("=")) {
                if (!Funcoes.ehInteiro(value)) {
                    value = "'" + value + "'";
                }
            }

            query = getQuery(String.format(
                    "SELECT COALESCE(COUNT(%s), 0) FROM %s WHERE %s %s %s;",
                    field, table, field, operador, value));
            query.moveToFirst();

            return (query.getInt(0) > 0);
        } catch (Exception e) {
            Log.e(toString(), e.getMessage());
            return false;
        } finally {
            query.close();
        }
    }

    @Override
    public Object getObjeto() {
        return new Object();
    }

    @Override
    public void mapear(Object objeto) {
        Log.i(toString(), "mapear()");

        setContentValues();
    }

    protected void put(String key, Object value) {
        if (value == null) {
            values.putNull(key);
        } else if (value instanceof String) {
            put(key, value.toString());
        } else if (value instanceof Double) {
            put(key, Double.parseDouble(value.toString()));
        } else if (value instanceof Integer) {
            put(key, Integer.parseInt(value.toString()));
        } else if (value instanceof Long) {
            put(key, Long.parseLong(value.toString()));
        } else if (value instanceof Timestamp) {
            put(key, (Timestamp) value);
        } else if (value instanceof Time) {
            put(key, (Time) value);
        } else if (value instanceof Date) {
            put(key, (Date) value);
        } else if (value instanceof byte[]) {
            put(key, (byte[]) value);
        }
    }

    private void put(String key, Timestamp value) {
        if (value != null) {
            values.put(key, value.getTime());
        } else {
            values.putNull(key);
        }
    }

    private void put(String key, Date value) {
        if (value != null) {
            values.put(key, value.getTime());
        } else {
            values.putNull(key);
        }
    }

    private void put(String key, Time value) {
        if (value != null) {
            values.put(key, value.getTime());
        } else {
            values.putNull(key);
        }
    }

    private void put(String key, byte[] value) {
        if (value != null) {
            values.put(key, value);
        } else {
            values.putNull(key);
        }
    }

    private void put(String key, String value) {
        if (value != null) {
            values.put(key, value);
        } else {
            values.putNull(key);
        }
    }

    private void put(String key, int value) {
        values.put(key, value);
    }

    private void put(String key, double value) {
        values.put(key, value);
    }

    public boolean gravar(Object objeto) {
        return gravar(objeto, true, true);
    }

    public boolean gravar(Object objeto, boolean generateID) {
        return gravar(objeto, generateID, true);
    }

    public boolean gravar(Object objeto, boolean generateID, boolean updating) {
        Log.i(toString(), "gravar()");

        boolean result = false;

        try {
            mapear(objeto);

            if (_fieldID.split(";").length == 1) {

                if (values.get(_fieldID) instanceof Integer) {

                    if (generateID && (values.getAsInteger(_fieldID) == 0)) {
                        values.put(_fieldID, generateID());
                    }

                }

                if (!values.getAsString(_uniqueField).equals("")) {

                    if (existeValue(_tabela.name(), _uniqueField,
                            values.getAsString(_uniqueField))) {
                        if (updating) {

                            if (!_fieldID.equals(_uniqueField)) {
                                Object value = getFieldID();
                                put(_fieldID, value);
                            }

                            result = altera(_tabela.name(), values);
                        }
                    } else {
                        result = insere(_tabela.name(), values);
                    }
                }

            } else {
                result = insere(_tabela.name(), values);
            }

        } catch (Exception e) {
            Log.e(toString(), e.getMessage());
        }

        return result;
    }

    private String getFieldID() {
        Log.i(toString(), "getFieldID()");

        String value = "";
        try {

            query = getQuery(String.format(
                    "SELECT %s FROM %s WHERE %s like '%s';", _fieldID,
                    _tabela.name(), _uniqueField,
                    values.getAsString(_uniqueField)));

            if (query.moveToFirst()) {
                value = query.getString(0);
            }

        } catch (Exception e) {
            value = "";
        } finally {
            query.close();
        }

        return value;
    }

    public boolean deletar(int id) {
        return deleta(_tabela.name(), String.format("%s = ?", _fieldID),
                Funcoes.getFmtValue(com.app.alertamedicamento.util.Tipo.INTEIRO, id));
    }

    public boolean deletarTodos() {
        return deleta(_tabela.name(), null);
    }

    @Override
    public Object get(int id) {
        Log.i(toString(), "get()");

        try {
            query = getQuery(String.format(_tabela.getSelectSQL().concat(" WHERE " + _fieldID + " = %s;"), id));

            if (query.moveToFirst()) {
                return getObjeto();
            }

        } catch (Exception e) {
            Log.e(toString(), e.getMessage());
            return null;
        } finally {
            query.close();
        }

        return null;
    }

    @Override
    public Object get(String value) {
        Log.i(toString(), "get()");

        try {
            query = getQuery(String.format(
                    _tabela.getSelectSQL().concat(
                            " WHERE " + _fieldID + " like '%s';"), value));

            if (query.moveToFirst()) {
                return getObjeto();
            }

        } catch (Exception e) {
            Log.e(toString(), e.getMessage());
            return null;
        } finally {
            query.close();
        }

        return null;
    }

    @Override
    public Object get(Tupla tupla, Object value) {
        Log.i(toString(), "get()");

        try {
            String operador = (tupla.getTipo().getValue().indexOf("VARCHAR") > 0) ? "like"
                    : "=";

            if (operador.equals("like")) {
                value = "'%" + value + "%'";
            } else if (operador.equals("=")) {
                value = (value == null) ? "" : value;
                if (!Funcoes.ehInteiro(value.toString())) {
                    value = "'" + value + "'";
                }
            }

            query = getQuery(String.format(
                    _tabela.getSelectSQL().concat(" WHERE %s %s %s;"),
                    tupla.getNome(), operador, value));

            if (query.moveToFirst()) {
                return getObjeto();
            }

        } catch (Exception e) {
            Log.e(toString(), e.getMessage());
            return null;
        } finally {
            query.close();
        }

        return null;
    }

    @Override
    public ArrayList<Object> getLista() {
        Log.i(toString(), "getLista()");

        ArrayList<Object> lista = new ArrayList<Object>();

        try {
            query = getQuery(_tabela.getSelectSQL());

            while (query.moveToNext()) {
                lista.add(getObjeto());
            }

        } catch (Exception e) {
            Log.e(toString(), e.getMessage());
            return null;
        } finally {
            query.close();
        }

        return lista;
    }

    @Override
    public String toString() {
        return getClass().getName() + " >> " + _tabela;
    }

    @Override
    public int generateID() {
        Log.i(toString(), "getID()");

        try {
            query = getQuery(String.format(
                    "SELECT COALESCE(MAX(%s), 0) FROM %s;", _fieldID,
                    _tabela.name()));
            query.moveToFirst();

            return (query.getInt(0) + 1);
        } catch (Exception e) {
            Log.e(toString(), e.getMessage());
            return 1;
        }

    }

    protected int getMax(String field) {
        Log.i(toString(), "getID()");

        try {
            query = getQuery(String.format(
                    "SELECT COALESCE(MAX(%s), 0) FROM %s;", field,
                    _tabela.name()));
            query.moveToFirst();

            return query.getInt(0);
        } catch (Exception e) {
            Log.e(toString(), e.getMessage());
            return 1;
        }

    }

    @Override
    public int getQtdRegistros() {
        Log.i(toString(), "getQtdRegistros()");

        try {
            query = getQuery(String.format(
                    "SELECT COALESCE(COUNT(%s), 0) FROM %s;",
                    _tabela.cols()[0], _tabela.name()));
            query.moveToFirst();

            return query.getInt(0);
        } catch (Exception e) {
            Log.e(toString(), e.getMessage());
            return 0;
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

}