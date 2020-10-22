package com.app.alertamedicamento.dao;

import android.content.Context;
import android.util.Log;

import com.app.alertamedicamento.business.Alarme;
import com.app.alertamedicamento.dao.db.DatabaseHandler;
import com.app.alertamedicamento.dao.db.SYS_DB;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

public class AlarmeDB extends DatabaseHandler {

    public AlarmeDB(Context context) {
        super(context, SYS_DB.ALARME, "ID");
    }

    @Override
    public void mapear(Object objeto) {
        super.mapear(objeto);

        Alarme value = (Alarme) objeto;

        put(_tabela.cols()[0], value.getId());
        put(_tabela.cols()[1], value.getHorario());
        put(_tabela.cols()[2], value.getRepeat());
        put(_tabela.cols()[3], value.getNomeMedicamento());
        put(_tabela.cols()[4], value.getDosagem());
        put(_tabela.cols()[5], value.getHora());
        put(_tabela.cols()[6], value.getMinuto());
        put(_tabela.cols()[7], value.getStatus());
        put(_tabela.cols()[8], value.getPostergar());
        put(_tabela.cols()[9], value.getDtLimite());
        put(_tabela.cols()[10], value.getContador());

    }

    @Override
    public Object getObjeto() {
        Alarme value = new Alarme();

        value.setId(query.getInt(0));
        value.setHorario(new Timestamp(query.getLong(1)));
        value.setRepeat(query.getString(2));
        value.setNomeMedicamento(query.getString(3));
        value.setDosagem(query.getString(4));
        value.setHora(query.getInt(5));
        value.setMinuto(query.getInt(6));
        value.setStatus(query.getString(7));
        value.setPostergar(query.getInt(8));
        value.setDtLimite(new Date(query.getLong(9)));
        value.setContador(query.getInt(10));

        return value;
    }

    public boolean existeHorario(int mHour, int mMinute, int id) {
        Log.i(toString(), "getID()");

        try {
            query = getQuery(String.format(
                    "SELECT COALESCE(COUNT(ID), 0) FROM %s WHERE HORA = %s AND MINUTO = %s AND ID <> %s;", _tabela.name(), mHour, mMinute, id));
            query.moveToFirst();

            return (query.getInt(0) > 0);

        } catch (Exception e) {
            Log.e(toString(), e.getMessage());
            return false;
        }
    }

    public ArrayList<Object> getListaAg(int hour, int minute) {
        Log.i(toString(), "getLista()");

        ArrayList<Object> lista = new ArrayList<Object>();

        try {
            query = getQuery(String.format(
                    "SELECT * FROM %s WHERE HORA = %s AND MINUTO = %s;", _tabela.name(), hour, minute));

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

}
