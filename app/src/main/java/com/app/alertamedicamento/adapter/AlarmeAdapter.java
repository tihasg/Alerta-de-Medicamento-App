package com.app.alertamedicamento.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.alertamedicamento.R;
import com.app.alertamedicamento.business.Alarme;
import com.app.alertamedicamento.util.AlarmeUtils;
import com.app.alertamedicamento.util.Funcoes;
import com.app.alertamedicamento.util.Tipo;

import java.util.List;

public class AlarmeAdapter extends ArrayAdapter<Object> {

    private int resource = 0;

    public AlarmeAdapter(Context context, int _resource, List<Object> _objects) {
        super(context, _resource, _objects);
        this.resource = _resource;
    }

    public void populateDataForRow(View parentView, Alarme item, int position) {
        View infoView = parentView.findViewById(R.id.row_alarme);
        infoView.setMinimumHeight(48);

        if (item != null) {
            TextView txtCodigo = (TextView) infoView.findViewById(R.id.txtCodigo);
            TextView txtDescricao = (TextView) infoView.findViewById(R.id.txtDescricao);
            TextView txtPostergar = (TextView) infoView.findViewById(R.id.txtPostergar);

            txtCodigo.setText(AlarmeUtils.getFormattedTime(item.getHora(), item.getMinuto()));
            txtDescricao.setText(item.getNomeMedicamento() + "\n" + item.getDosagem());
            txtPostergar.setText((item.getRepeat().equals("S") ? "Intervalo: " + item.getPostergar() + " hora's" : "")
            + "\n Termina em: " + (Funcoes.getFmtValue(Tipo.DATA, item.getDtLimite())));

            if (item.getStatus().equals("I")) {
                txtDescricao.setTextColor(Color.GRAY);
            } else
            if (item.getStatus().equals("P")) {
                txtDescricao.setTextColor(Color.RED);
            } else {
                txtDescricao.setTextColor(Color.WHITE);
            }

        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewGroup parentView;

        Alarme item = (Alarme) getItem(position);

        if (convertView == null) {
            parentView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
            vi.inflate(resource, parentView, true);
        } else {
            parentView = (LinearLayout) convertView;
        }

        populateDataForRow(parentView, item, position);

        return parentView;
    }

}
