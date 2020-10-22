package com.app.alertamedicamento.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.alertamedicamento.R;
import com.app.alertamedicamento.business.Alarme;

import java.util.List;

public class AlarmeAgAdapter extends ArrayAdapter<Object> {

    private int resource = 0;

    public AlarmeAgAdapter(Context context, int _resource, List<Object> _objects) {
        super(context, _resource, _objects);
        this.resource = _resource;
    }

    public void populateDataForRow(View parentView, Alarme item, int position) {
        View infoView = parentView.findViewById(R.id.row_alarme_ag);
        infoView.setMinimumHeight(52);

        if (item != null) {
            TextView tvMedicamento = (TextView) infoView.findViewById(R.id.tvMedicamento);
            TextView tvDosagem = (TextView) infoView.findViewById(R.id.tvDosagem);

            tvMedicamento.setText(item.getNomeMedicamento());
            tvDosagem.setText(item.getDosagem());

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
