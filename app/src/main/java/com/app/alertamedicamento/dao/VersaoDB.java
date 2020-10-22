package com.app.alertamedicamento.dao;

import android.content.Context;

import com.app.alertamedicamento.business.VersaoApp;
import com.app.alertamedicamento.dao.db.DatabaseHandler;
import com.app.alertamedicamento.dao.db.SYS_DB;

import java.sql.Timestamp;

public class VersaoDB extends DatabaseHandler {

    public VersaoDB(Context context) {
        super(context, SYS_DB.VERSAO_APP, "VERSAO", "VERSAO");
    }

    @Override
    public void mapear(Object objeto) {
        super.mapear(objeto);

        VersaoApp value = (VersaoApp) objeto;

        put(_tabela.cols()[0], value.getVersao());
        put(_tabela.cols()[1], value.getDataHora());
        put(_tabela.cols()[2], value.getTexto());

    }

    @Override
    public Object getObjeto() {
        VersaoApp value = new VersaoApp();

        value.setVersao(query.getInt(0));
        value.setDataHora(new Timestamp(query.getLong(1)));
        value.setTexto(query.getString(2));

        return value;
    }

    public void AtualizarDB() {
    }

}
