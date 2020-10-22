package com.app.alertamedicamento.dao.db;

import java.util.ArrayList;

public interface IDataSet {

    public void mapear(Object objeto);

    public Object getObjeto();

    public Object get(Tupla tupla, Object value);

    public boolean gravar(Object objeto);

    public Object get(int id);

    public Object get(String value);

    public ArrayList<Object> getLista();

    public int generateID();

    public int getQtdRegistros();

}
