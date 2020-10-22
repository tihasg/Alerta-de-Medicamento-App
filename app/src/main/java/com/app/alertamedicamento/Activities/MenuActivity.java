package com.app.alertamedicamento.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import com.app.alertamedicamento.R;
import com.app.alertamedicamento.adapter.AlarmeAdapter;
import com.app.alertamedicamento.business.Dispositivo;
import com.app.alertamedicamento.dao.AlarmeDB;
import com.app.alertamedicamento.util.FuncoesAndroid;
import com.app.alertamedicamento.util.SendTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MenuActivity extends AppCompatActivity {

    public static int resultId = 0;
    private ListView listView = null;
    private TextView textView = null;
    private TextView tvTotalizador = null;
    private TextClock textClock = null;
    private Button btCadastro = null;
    private ArrayList<Object> lista = null;
    private AlarmeAdapter adapter = null;
    private AsyncTask<Integer, String, Integer> consulta = null;
    int txtResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        initComponents();

    }

    private void initComponents() {

        tvTotalizador = (TextView) findViewById(R.id.tvTotalizador);
        listView = (ListView) findViewById(R.id.listView);

        txtResultado= AlertaActivity.id1;
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
        Date data = new Date();
        String dataFormatada = formataData.format(data);
        String dat= AlertaActivity.dat;

        if  (AlertaActivity.verificar.equals("false")&&dataFormatada.equals(dat)){
            new AlarmeDB(MenuActivity.this).deletar(txtResultado);
            Toast.makeText(MenuActivity.this, "Alarme removido!", Toast.LENGTH_SHORT).show();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {

                resultId = listView.getItemAtPosition(position).hashCode();

                AlertDialog.Builder msg = new AlertDialog.Builder(MenuActivity.this);
                msg.setMessage("Opções do registro:");
                msg.setPositiveButton("Editar alarme?", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        try {

                            Intent data = new Intent(MenuActivity.this, CadAlarmeActivity.class);
                            data.putExtra("id", resultId);
                            setResult(Activity.RESULT_OK, data);

                            startActivityForResult(data, 999);

                        } catch (Exception e) {
                            Toast.makeText(MenuActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                msg.setNegativeButton("Excluir alarme?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        AlertDialog.Builder msg = new AlertDialog.Builder(MenuActivity.this);
                        msg.setMessage("Confirmar exclusão?");
                        msg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {

                                new AlarmeDB(MenuActivity.this).deletar(resultId);

                                Dispositivo.restartTaskService(getApplicationContext());

                                Toast.makeText(MenuActivity.this, "Alarme removido!", Toast.LENGTH_SHORT).show();
                                consultar();

                            }
                        });

                        msg.setNegativeButton("Não", null);

                        msg.show();

                    }
                });

                msg.setNeutralButton("Voltar", null);


                msg.show();

            }
        });

        btCadastro = (Button) findViewById(R.id.btCadastro);
        btCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultId = 0;
                startActivityForResult(new Intent(MenuActivity.this, CadAlarmeActivity.class), 999);

            }
        });

        consultar();

    }

    @Override
    public void onBackPressed(){
        FuncoesAndroid.fecharApp(this, true);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Dispositivo._minimize) {
            Dispositivo._minimize = false;
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 999) {
            consultar();
        }

    }

    public void manual(View view) {
        Dispositivo.PdfFileName = "manual.pdf";
        startActivity(new Intent(MenuActivity.this, PdfViewActivity.class));
    }

    public void contato(View view) {
        Dispositivo.PdfFileName = "contato.pdf";
        startActivity(new Intent(MenuActivity.this, PdfViewActivity.class));
    }

    private void consultar() {

        new SendTask(this, "Atualizando lista de horários.") {

            @Override
            protected Integer doInBackground(Integer... params) {

                lista = new AlarmeDB(getContext()).getLista();

                setMsg("");

                return super.doInBackground(params);
            }

            @Override
            protected void onProgressUpdate(String... values) {
                adapter = new AlarmeAdapter(getContext(), R.layout.row_alarme, lista);
                listView.setAdapter(adapter);
                FuncoesAndroid.setListViewHeightBasedOnChildren(listView);
                resultId = 0;
                if (lista.size() > 0) {
                    tvTotalizador.setText(lista.size() + " REGISTROS DE ALARME...");
                } else {
                    tvTotalizador.setText("NENHUM ALARME CADASTRADO!");
                }
            }

        }.execute();

    }

}

