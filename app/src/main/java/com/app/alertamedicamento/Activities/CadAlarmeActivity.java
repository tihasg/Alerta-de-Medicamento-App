package com.app.alertamedicamento.Activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.alertamedicamento.R;
import com.app.alertamedicamento.business.Alarme;
import com.app.alertamedicamento.business.Dispositivo;
import com.app.alertamedicamento.dao.AlarmeDB;
import com.app.alertamedicamento.util.AlarmeUtils;
import com.app.alertamedicamento.util.Funcoes;
import com.app.alertamedicamento.util.G;

import java.util.Calendar;

public class CadAlarmeActivity extends AppCompatActivity {

    private static final int TIME_DIALOG_ID = 1;
    public static final int DATE_DIALOG_ID = 2;

    private TextView dsTime = null;
    private TextView dsDataLimite = null;
    private EditText etNomeMedicamento = null;
    private EditText etDoSagem = null;
    private CheckBox chkRepeat = null;
    private EditText etHoRa = null;
    // date and time
    private int mHour = 0;
    private int mMinute = 0;
    private int mYear = 0;
    private int mMonth = 0;
    private int mDayOfMonth = 0;

    private AlarmeDB alarmeDB = null;
    private Alarme alarme = null;

    //menu da hora
    private TimePickerDialog.OnTimeSetListener mTimeSetListener =
            new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    mHour = hourOfDay;
                    mMinute = minute;
                    updateTimeDisplay();
                }
            };

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    mYear = year;
                    mMonth = month;
                    mDayOfMonth = dayOfMonth;
                    updateDateDisplay();
                }
            };

    //atualiza a tela
    private void updateTimeDisplay() {
        dsTime.setText(
                new StringBuilder()
                        .append(AlarmeUtils.pad(mHour)).append(":")
                        .append(AlarmeUtils.pad(mMinute)));
    }

    private void updateDateDisplay() {
        dsDataLimite.setText(
                new StringBuilder()
                        .append(AlarmeUtils.pad(mDayOfMonth))
                        .append("/")
                        .append(AlarmeUtils.pad(mMonth+1))
                        .append("/")
                        .append(AlarmeUtils.pad(mYear))
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_alarme);

        initComponents();
    }

    private void initComponents() {
        alarmeDB = new AlarmeDB(this);

        dsTime = (TextView) findViewById(R.id.ds_time);
        dsTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(TIME_DIALOG_ID);
            }
        });
        dsDataLimite = (TextView) findViewById(R.id.dsDataLimite);
        dsDataLimite.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        etNomeMedicamento = (EditText) findViewById(R.id.Edit_Nome_Remedio);
        etDoSagem = (EditText) findViewById(R.id.Edit_Dosagem);
        chkRepeat = (CheckBox) findViewById(R.id.chk_repeat);
        Button btnOk = (Button) findViewById(R.id.btn_ok);
        Button btnCancel = (Button) findViewById(R.id.btn_cancel);
        etHoRa = (EditText) findViewById(R.id.Edit_hora);

        //se clica em ok ou cancelar
        btnOk.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                gravar();
            }

        });

        btnCancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }

        });

        getValues();

    }

    private void gravar() {

        if (alarmeDB.existeHorario(mHour, mMinute, alarme.getId())) {
            Toast.makeText(this, "Já existe um alerta agendado para este horário!", Toast.LENGTH_SHORT).show();
        }

        if (etNomeMedicamento.getText().toString().equals("")) {
            Toast.makeText(this, "Informe o nome do medicamento!", Toast.LENGTH_SHORT).show();
        } else if (etDoSagem.getText().toString().equals("")) {
            Toast.makeText(this, "Informe a dosagem!", Toast.LENGTH_SHORT).show();
        } else if (mHour < 0) {
            Toast.makeText(this, "Informe o horário!", Toast.LENGTH_SHORT).show();
        } else
        {
            setValues();

        }

    }

    private void setValues() {

        if (alarme.getId() == 0) {
            alarme.setId(alarmeDB.generateID());
        }

        alarme.setHorario(Funcoes.getCurrentTimestamp());
        alarme.setStatus("A");
        alarme.setNomeMedicamento(etNomeMedicamento.getText().toString().trim().toUpperCase());
        alarme.setDosagem(etDoSagem.getText().toString().trim().toUpperCase());
        alarme.setHora(mHour);
        alarme.setMinuto(mMinute);
        alarme.setPostergar(Funcoes.strToInt(etHoRa.getText().toString().trim().toUpperCase()));
        alarme.setRepeat(chkRepeat.isChecked() ? "S" : "N");
        if (mYear > 0) {
            alarme.setDtLimite(Funcoes.dateToSQLDate(Funcoes.encodeDate(mYear, mMonth+1, mDayOfMonth)));
        }
        if (alarmeDB.gravar(alarme)) {

            Dispositivo.restartTaskService(getApplicationContext());

            Toast.makeText(this, "Alarme agendado com sucesso!", Toast.LENGTH_SHORT).show();

            setResult(RESULT_OK);
            finish();

        } else {
            Toast.makeText(this, "Erro ao gravar alarme!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == TIME_DIALOG_ID) {
            return new TimePickerDialog(this,
                    mTimeSetListener, mHour, mMinute, false);
        } else
        if (id == DATE_DIALOG_ID) {
            return new DatePickerDialog(this,
                    mDateSetListener, mYear, mMonth-1, mDayOfMonth);
        } else {
            return null;
        }
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        if (id == TIME_DIALOG_ID) {
            ((TimePickerDialog) dialog).updateTime(mHour, mMinute);
        } else
            if (id == DATE_DIALOG_ID) {
                ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDayOfMonth);
            }
    }

    public void getValues() {
        if (MenuActivity.resultId == 0) {
            alarme = new Alarme();
        } else {
            alarme = (Alarme) alarmeDB.get(MenuActivity.resultId);
        }

        etNomeMedicamento.setText(alarme.getNomeMedicamento());
        etDoSagem.setText(alarme.getDosagem());
        etHoRa.setText(AlarmeUtils.pad(alarme.getPostergar()));
        mHour = alarme.getHora();
        mMinute = alarme.getMinuto();
        updateTimeDisplay();
        chkRepeat.setChecked(alarme.getRepeat().equals("S"));
        if (alarme.getDtLimite() != null) {
            mDayOfMonth = Funcoes.decodeDate(alarme.getDtLimite())[0];
            mMonth = Funcoes.decodeDate(alarme.getDtLimite())[1];
            mYear = Funcoes.decodeDate(alarme.getDtLimite())[2];
        }
        updateDateDisplay();

    }

}
