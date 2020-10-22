package com.app.alertamedicamento.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Environment;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class G {

    public static final String TAGLOG = "SYS";

    public static final String DIR_BASE = "SYS";

    public static final int INTNULL = -1;

    public static final int TRUE = 1;
    public static final int FALSE = 0;

    public static final int STEXECUSAO = 1;
    public static final int STPAUSADO = 2;

    public static final int IDINCLUIR = 9001;
    public static final int IDEDITAR = 9002;
    public static final int IDDELETAR = 9003;
    public static final int IDVER = 9004;
    public static final int IDSELECIONAR = 9005;
    public static final int IDFILTRAR = 9006;
    public static final int IDORDENAR = 9007;
    public static final int IDSALVAR = 9008;
    public static final int IDCANCELAR = 9009;
    public static final int IDLIMPAR = 9010;
    public static final int IDTODOS = 9011;
    public static final int IDNENHUM = 9012;

    public static final int IDDATADIALOG = 9500;

    public static final int IDGETCODBARRAS = 9998;
    public static final int IDGETVOZ = 9999;

    public static final String EXTRASTRFILTRO = "exStrFiltro";
    public static final String EXTRACTVFILTRO = "exCtvFiltro";
    public static final String EXTRAID = "exId";
    public static final String EXTRASELECAO = "exSelecao";

    public static final String MASCDOUBLE2 = "0.00";
    public static final String MASCDATAHORA = "dd/MM/yyyy kk:mm:ss";
    public static final String MASCDATAHORADB = "yyyy-MM-dd kk:mm:ss";
    public static final String MASCDATA = "dd/MM/yyyy";
    public static final String MASCDATADB = "yyyy-MM-dd";

    public static final String ORDERDESC = " DESC";

    public static File DIR_APP = new File(Environment.getExternalStorageDirectory(), "SYS/");
    public static File DIR_UPDATE = new File(Environment.getExternalStorageDirectory(), "SYS/update/");
    public static File DIR_DOC = new File(Environment.getExternalStorageDirectory(), "SYS/doc/");
    public static File DIR_PEDIDOS = new File(Environment.getExternalStorageDirectory(), "SYS/pedidos/");
    public static File DIR_TEMP = new File(Environment.getExternalStorageDirectory(), "SYS/temp/");
    public static final File TEMP_FILE_IMG = new File(DIR_TEMP, "temp_img.png");
    public static OnClickListener listenerdimiss = new OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
            dialog.dismiss();
        }
    };

    public static String getString(Context ctx, int s) {
        return ctx.getString(s);
    }

    // Mensagens ---------------------------------------------------------------

    public static int getColor(Activity act, int cor) {
        return act.getResources().getColor(cor);
    }

    public static void pergunta(final Context ctx, final String msg, OnClickListener listenersim, OnClickListener listenernao) {
        try {
            AlertDialog alerta = new AlertDialog.Builder(ctx).create();

            alerta.setTitle("Pergunta");
            alerta.setMessage(msg);

            alerta.setButton(AlertDialog.BUTTON_POSITIVE, "Sim", listenersim);
            alerta.setButton(AlertDialog.BUTTON_NEGATIVE, "Nao", listenernao);

            alerta.show();
        } catch (Exception e) {
            addLogErro(e.getMessage());
        }
    }

    public static void mensagem(final Context ctx, final String titulo, final String msg) {
        try {
            AlertDialog alerta = new AlertDialog.Builder(ctx).create();

            alerta.setTitle(titulo);
            alerta.setMessage(msg);

            alerta.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", listenerdimiss);

            alerta.show();
        } catch (Exception e) {
            addLogErro(e.getMessage());
        }
    }

    public static void msgInformacao(Context ctx, String msg) {
        mensagem(ctx, "Informacao", msg);
    }

    public static void msgErro(Context ctx, String msg) {
        addLogErro(msg);
        mensagem(ctx, "Erro", msg);
    }

    public static void msgErro(Context ctx, String titulo, String msg) {
        msgErro(ctx, G.strAdd(titulo, strEntreAspas(msg), ": "));
    }

    public static void alertar(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

    public static void addLogErro(String msg) {
        Log.e(TAGLOG, "ERRO! " + msg);
    }

    public static void msgLista(Context ctx, String msg, String[] itens, OnClickListener listener) {
        new AlertDialog.Builder(ctx).setTitle(msg).setItems(itens, listener).show();
    }

    public static void msgLista(Context ctx, String[] itens, OnClickListener listener) {
        msgLista(ctx, "Op��es", itens, listener);
    }

    // Arquivos ----------------------------------------------------------------

    public static String getPathSdCard() {
        return Environment.getExternalStorageDirectory().toString();
    }

    public static String salvarArquivo(Context ctx, File arq, String conteudo) {
        try {
            FileOutputStream fos;

            fos = new FileOutputStream(arq);

            fos.write(conteudo.getBytes());

            fos.flush();
            fos.close();

            return arq.getAbsolutePath();
        } catch (Exception e) {
            G.msgErro(ctx, "Erro ao gerar arquivo. " + " " + strEntreAspas(arq.getName()), e.getMessage());
            return "";
        }
    }

    public static String salvarArquivo(Context ctx, String diretorio, String fileName, String conteudo) {
        try {
            String path = getPathSdCard() + strEntreChars(DIR_BASE, "/") + diretorio;

            File dir = new File(path);
            dir.mkdirs();

            File arq = new File(path, fileName);

            if (arq.exists()) {
                arq.delete();
            }

            return salvarArquivo(ctx, arq, conteudo);
        } catch (Exception e) {
            G.msgErro(ctx, "Erro ao gerar arquivo. ", e.getMessage());
            return "";
        }
    }

    public static File abrirArquivo(Context ctx, String diretorio, String fileName) {
        try {
            String path = getPathSdCard() + strEntreChars(DIR_BASE, "/") + diretorio;

            File arq = new File(path, fileName);
            if (arq.exists())
                return arq;
            else
                return null;
        } catch (Exception e) {
            G.msgErro(ctx, "Erro ao gerar arquivo. " + " " + strEntreAspas(fileName), e.getMessage());
            return null;
        }
    }

    @SuppressWarnings("resource")
    public static boolean copiarArquivo(Context ctx, String de, String para) {
        String pathde = getPathSdCard() + strEntreChars(DIR_BASE, "/") + de;
        String pathpara = getPathSdCard() + strEntreChars(DIR_BASE, "/") + para;

        FileInputStream origem;
        FileOutputStream destino;
        try {
            origem = new FileInputStream(pathde);
            destino = new FileOutputStream(pathpara);

            FileChannel fcOrigem = origem.getChannel();
            FileChannel fcDestino = destino.getChannel();

            fcOrigem.transferTo(0, fcOrigem.size(), fcDestino);

            return true;
        } catch (Exception e) {
            G.msgErro(ctx, "Erro ao gerar arquivo. " + " " + strEntreAspas(de), e.getMessage());
            return false;
        }
    }

    public static boolean moveToBkp(Context ctx, String diretorio, String fileName) {
        try {
            String path = getPathSdCard() + strEntreChars(DIR_BASE, "/") + diretorio;

            File arq = new File(path, fileName);

            if (!arq.exists())
                return true;

            String pathBkp = path + strEntreChars(DIR_BASE, "/");

            File dir = new File(pathBkp);
            dir.mkdirs();

            String extensao = fileName.substring(fileName.lastIndexOf('.'));
            String fileNameBkp = fileName.substring(0, fileName.indexOf(".")) + G.dateToStr(G.data()).replaceAll("/", ".").replaceAll(":", ".") + extensao;

            return arq.renameTo(new File(pathBkp, fileNameBkp));
        } catch (Exception e) {
            G.msgErro(ctx, "Erro ao gerar arquivo. ", e.getMessage());
            return false;
        }
    }

    // Strings -----------------------------------------------------------------

    public static String strEntreChars(String s, String sI, String sF) {
        return (sI + s + sF);
    }

    public static String strEntreChars(String s, String sIF) {
        return (strEntreChars(s, sIF, sIF));
    }

    public static String strEntreParenteses(String s) {
        return (strEntreChars(s, "(", ")"));
    }

    public static String strEntreAspas(String s) {
        return (strEntreChars(s, "'"));
    }

    public static String strEntrePorcentagem(String s) {
        return (strEntreChars(s, "%"));
    }

    public static String strEntreEspacos(String s) {
        return (strEntreChars(s, " "));
    }

    public static String strAdd(String s1, String s2, String separador) {
        String retorno = s1;

        if (G.strIsEmpty(s2))
            return retorno;

        if (!G.strIsEmpty(retorno))
            retorno += separador;

        retorno += s2;

        return retorno;
    }

    public static boolean strIsEmpty(String s) {
        return ((s == null) || (s.equals("")));
    }

    public static String strNotNull(String s) {
        if (s == null)
            return "";
        else
            return s;
    }

    // Datas -------------------------------------------------------------------

    public static Date strToDate(String format, String data) {
        SimpleDateFormat formatador = new SimpleDateFormat(format);
        try {
            return formatador.parse(data);
        } catch (Exception e) {
            return (new Date());
        }
    }

    public static Date strToDate(String data) {
        return strToDate(G.MASCDATAHORA, data);
    }

    public static String dateToStr(String format, Date data) {
        try {
            SimpleDateFormat formatador = new SimpleDateFormat(format);
            return formatador.format(data);
        } catch (Exception e) {
            return "";
        }
    }

    public static String dateToStr(Date data) {
        return dateToStr(G.MASCDATAHORA, data);
    }

    public static Date data() {
        return new Date(System.currentTimeMillis());
    }

    // NUMEROS -----------------------------------------------------------------

    public static boolean intToBool(int valor) {
        return (valor == G.TRUE);
    }

    public static int boolToInt(boolean valor) {
        if (valor)
            return G.TRUE;
        else
            return G.FALSE;
    }

    public static String formatDouble(String format, double valor) {
        NumberFormat f = new DecimalFormat(format);
        return (f.format(valor));
    }

    public static String formatDouble(double valor) {
        return (G.formatDouble(G.MASCDOUBLE2, valor));
    }

    public static Double getDoubleForEditText(EditText edit) {
        String str = edit.getText().toString();

        if (G.strIsEmpty(str))
            return 0.0;

        try {
            return (Double.parseDouble(str));
        } catch (Exception e) {
            return 0.0;
        }
    }

}
