package com.app.alertamedicamento.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Funcoes {

    public final static Locale LBR = new Locale("pt", "BR");
    public static final DecimalFormatSymbols FORMAT_SYMBOLS = new DecimalFormatSymbols(LBR);
    public static final String MSK_CNPJ = "##.###.###/####-##";
    public static final String MSK_CPF = "###.###.###-##";
    public static final String MSK_DATA = "##/##/####";
    public static final String MSK_HORA = "##:##:##";
    public static final String MSK_MOEDA = "###,###,##0.00";
    public static final String MSK_MOEDA_3 = "###,###,###0.000";
    public static final String MSK_MOEDA_4 = "###,###,####0.0000";
    public static final String MSK_QTDADE_0 = "####0";
    public static final String MSK_QTDADE_1 = "####,#0000.0";
    public static final String MSK_QTDADE_2 = "####,##0000.00";
    public static final String MSK_QTDADE_3 = "####,###0000.000";
    public static final String MSK_QTDADE = "####,###0.000";
    public static final String MSK_NUMERICO = "####,####0.0000";
    public static final String FMT_DT_HR = "dd/MM/yyyy hh:mm:ss";
    public static final String FMT_DT = "dd/MM/yyyy";
    public static final String FMT_HR = "hh:mm:ss";
    public static final String FMT_HR1 = "hh:mm";
    public static final String MSK_CEP = "#####-####";
    public static final String FMT_DT_HR_DT = "dd/MM/yyyy";
    public static final String FMT_DT_HR1 = "yyyy-MM-dd HH:mm:ss"; // AAAA-MM-DD 00:00:00.000000
    public static final String FMT_DT_HR3 = "yyyy-MM-dd HH:mm:ss.SSSSSS"; // AAAA-MM-DD 00:00:00.000000
    public static final String FMT_DT_HR2 = "dd/MM/yyyy HH:mm:ss.SSSSSS"; // AAAA-MM-DD 00:00:00.000000
    private static final String MSK_FONE_DDD_COMUM = "(##)####-####";

    //public static final String MSK_NUMERICO = "####,####0.0000";
    private static final String MSK_FONE_DDI = "+#############";
    private static final String MSK_FONE_0000 = "####-####";
    private static final String MSK_FONE_9 = "(##)#####-####";
    private static final String MSK_FONE_0X00 = "#### ### ####";
    private static final String MSK_FONE = "(##)####-####";

    public static int converter(String str) {
        int convrtr = 0;
        if (str == null) {
            str = "0";
        } else if ((str.trim()).equals("null")) {
            str = "0";
        } else if (str.equals("")) {
            str = "0";
        }
        try {
            convrtr = Integer.parseInt(str);
        } catch (Exception e) {
        }
        return convrtr;
    }

    public static String hashMD5(String value) {
        String hashword = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(value.getBytes());
            BigInteger hash = new BigInteger(1, md5.digest());
            hashword = hash.toString(16);
        } catch (NoSuchAlgorithmException nsae) {
            // ignore
        }
        return hashword;
    }

    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Date dateToSQLDate(java.util.Date date) {
        if (date != null) {
            return new Date(date.getTime());
        } else {
            return null;
        }
    }

    public static Time getCurrentTime() {
        return new Time(System.currentTimeMillis());
    }

    public static Date getCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    public static String timeStampToStrDate(Timestamp tVal) {
        if (tVal == null) {
            return null;
        }
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(tVal);
        String sRetorno = strZero("" + cal.get(Calendar.DATE), 2);
        sRetorno += "/" + strZero("" + (cal.get(Calendar.MONTH) + 1), 2);
        sRetorno += "/" + (cal.get(Calendar.YEAR));
        return sRetorno;
    }

    public static String getFmtData(Timestamp value, String FMT) {
        if (value != null && FMT != null) {
            FMT = (FMT.equals("") ? "dd/MM/yyyy" : FMT);
            SimpleDateFormat sdf = new SimpleDateFormat(FMT, LBR);
            return sdf.format(value);
        } else {
            return null;
        }
    }

    public static String getFmtHora(Timestamp value, String FMT) {
        if (value != null && FMT != null) {
            FMT = (FMT.equals("") ? "hh:mm" : FMT);
            SimpleDateFormat sdf = new SimpleDateFormat(FMT, LBR);
            return sdf.format(value);
        } else {
            return null;
        }
    }

    public static String timeStampToStr(Timestamp value) {
        if (value != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(FMT_DT_HR, LBR);
            return sdf.format(value);
        } else {
            return null;
        }
    }

    public static String strZero(String text, int times) {
        String ret = null;
        try {

            if (text == null) {
                ret = replicate("0", times);
            } else {
                ret = replicate("0", times - text.trim().length());
                ret += text.trim();
            }

            if (text.length() > times) {
                return text.substring(0, times);
            }

        } catch (Exception e) {
        }
        return ret;
    }

    public static String replicate(String text, int times) {
        StringBuilder ret = new StringBuilder("");
        try {
            for (int i = 0; i < times; i++) {
                ret.append(text);
            }
        } catch (Exception e) {
        }
        return ret.toString();
    }

    public static BigDecimal strToBd(Object vlr) {
        BigDecimal retorno = null;
        if (vlr == null) {
            retorno = new BigDecimal(0);
        } else {
            retorno = strCurrencyToBigDecimal(vlr.toString());
        }
        return retorno;
    }

    public static BigDecimal strCurrencyToBigDecimal(String sVal) {
        BigDecimal bigRetorno = new BigDecimal("0");
        if (sVal == null) {
            return new BigDecimal("0");
        }
        int iPosPonto = sVal.indexOf(46);
        if (iPosPonto > -1) {
            sVal = sVal.substring(0, iPosPonto) + sVal.substring(iPosPonto + 1);
        }
        char[] cVal = sVal.toCharArray();
        int iPos = sVal.indexOf(",");
        if (iPos >= 0) {
            cVal[iPos] = '.';
        }
        sVal = new String(cVal);
        try {
            bigRetorno = new BigDecimal(sVal.trim());
        } catch (Exception localException) {
        }
        return bigRetorno;
    }

    public static String setMascara(String sVal, String sMasc) {
        if (sVal == null) {
            return "";
        }
        String texto = "";
        int i2 = 0;
        if ((sVal.length() > 0) & (sMasc.length() > 0)
                & (sMasc.length() > sVal.length())) {
            char[] va = sVal.toCharArray();
            char[] ma = sMasc.toCharArray();
            for (int i = 0; i < sVal.length(); i++) {
                if (ma[i2] == '#') {
                    texto += va[i];
                } else {
                    texto += ma[i2];

                    if (!Character.isDigit(ma[i2])) {
                        texto += va[i];
                        i2++;
                    }
                }
                i2++;
            }
        } else {
            texto = sVal;
        }
        return texto;
    }

    public static BigDecimal strDecimalToBigDecimal(int iDec, String sVal) {
        double deVal = 0.0D;
        BigDecimal bigRet = null;
        try {
            if (sVal == null) {
                sVal = "0";
            }
            deVal = Float.parseFloat(sVal);
            deVal = arredDouble(deVal, iDec);
        } finally {
            bigRet = new BigDecimal(deVal);
        }
        return bigRet;
    }

    public static double arredDouble(double deValor, int iDec) {
        BigDecimal bdValor = null;
        try {
            bdValor = new BigDecimal(deValor);
            bdValor = bdValor.setScale(iDec, 4);
            deValor = bdValor.doubleValue();
        } finally {
            bdValor = null;
        }
        return deValor;
    }

    public static float arredFloat(float fValor, int iDec) {
        BigDecimal bdValor = null;
        try {
            bdValor = new BigDecimal(fValor);
            bdValor = bdValor.setScale(iDec, 4);
            fValor = bdValor.floatValue();
        } finally {
            bdValor = null;
        }
        return fValor;
    }

    public static void espera(int iSec) {
        long iIni = getSeconds();
        long iFim = getSeconds();
        while (iFim - iIni < iSec) {
            iFim = getSeconds();
        }
    }

    public static long getSeconds() {
        java.util.Date dtHora = new java.util.Date();
        return (dtHora.getTime() / 1000L);
    }

    public static boolean ehInteiro(String sNum) {
        boolean bRetorno = false;
        for (int i = 0; i < sNum.length(); ++i) {
            if ("0123456789-".indexOf(sNum.charAt(i)) == -1) {
                bRetorno = false;
                break;
            }
            bRetorno = true;
        }
        return bRetorno;
    }

    public static boolean ehNumero(String sNum) {
        boolean bRetorno = false;
        for (int i = 0; i < sNum.length(); ++i) {
            if ("0123456789.".indexOf(sNum.charAt(i)) == -1) {
                bRetorno = false;
                break;
            }
            bRetorno = true;
        }
        return bRetorno;
    }

    public static int[] decodeDate(java.util.Date dtSel) {
        GregorianCalendar gcSel = new GregorianCalendar();
        int[] iRetorno = new int[3];
        try {
            gcSel.setTime(dtSel);
            iRetorno[0] = gcSel.get(Calendar.DAY_OF_MONTH);
            iRetorno[1] = gcSel.get(Calendar.MONTH);
            iRetorno[2] = gcSel.get(Calendar.YEAR);
        } finally {
            gcSel = null;
        }
        return iRetorno;
    }

    public static java.util.Date encodeDate(int iAno, int iMes, int iDia) {
        java.util.Date dtRetorno = new java.util.Date();
        GregorianCalendar gcSel = new GregorianCalendar();
        try {
            gcSel.setTime(dtRetorno);
            gcSel.set(1, iAno);
            gcSel.set(2, iMes - 1);
            gcSel.set(5, iDia);
            dtRetorno = gcSel.getTime();
        } finally {
            gcSel = null;
        }
        return dtRetorno;
    }

    public static java.util.Date strTimeToDate(String sTime) {
        java.util.Date retorno = null;
        try {
            int hora = Integer.parseInt(sTime.substring(0, 2)) * 60 * 60 * 1000;
            int minuto = Integer.parseInt(sTime.substring(3, 5)) * 60 * 1000;
            int segundo = Integer.parseInt(sTime.substring(6, 8)) * 1000;
            retorno = new java.util.Date(hora + minuto + segundo);
        } catch (Exception e) {
            retorno = null;
        }
        return retorno;
    }

    public static java.util.Date encodeTime(java.util.Date dtSel, int iHora,
                                            int iMinuto, int iSegundo, int iMilesegundo) {
        java.util.Date dtRetorno = dtSel;
        GregorianCalendar gcSel = new GregorianCalendar();
        try {
            gcSel.setTime(dtSel);
            gcSel.set(11, iHora);
            gcSel.set(12, iMinuto);
            gcSel.set(13, iSegundo);
            gcSel.set(14, iMilesegundo);
            dtSel = gcSel.getTime();
        } finally {
            gcSel = null;
        }
        return dtRetorno;
    }

    public static long getNumDias(java.util.Date dt1, java.util.Date dt2) {
        long lResult = 0L;
        long lDias1 = 0L;
        long lDias2 = 0L;
        GregorianCalendar cal1 = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar();
        cal1.setTime(dt1);
        cal1.set(cal1.get(1), cal1.get(2), cal1.get(5), 0, 0, 0);
        cal2.setTime(dt2);
        cal2.set(cal2.get(1), cal2.get(2), cal2.get(5), 0, 0, 0);

        lDias1 = cal1.getTimeInMillis();
        lDias2 = cal2.getTimeInMillis();

        lResult = (lDias2 - lDias1) / 86400000L;
        return lResult;
    }

    public static long getNumDiasAbs(java.util.Date dt1, java.util.Date dt2) {
        long lResult = 0L;
        long lDias1 = 0L;
        long lDias2 = 0L;
        GregorianCalendar cal1 = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar();
        cal1.setTime(dt1);
        cal1.set(cal1.get(1), cal1.get(2), cal1.get(5), 0, 0, 0);
        cal2.setTime(dt2);
        cal2.set(cal2.get(1), cal2.get(2), cal2.get(5), 0, 0, 0);

        lDias1 = cal1.getTimeInMillis();
        lDias2 = cal2.getTimeInMillis();
        if (lDias1 > lDias2) {
            lResult = (lDias1 - lDias2) / 86400000L;
        } else {
            lResult = (lDias2 - lDias1) / 86400000L;
        }
        return lResult;
    }

    public static java.util.Date getDataFimMes(int iMes, int iAno) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(5, 1);
        cal.set(2, iMes);
        cal.set(1, iAno);
        int iUltimoDia = cal.getActualMaximum(5);
        cal.set(5, iUltimoDia);
        return cal.getTime();
    }

    public static java.util.Date getDataIniMes(int iMes, int iAno) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(5, 1);
        cal.set(2, iMes);
        cal.set(1, iAno);
        return cal.getTime();
    }

    public static String trimFinal(String sVal) {
        char[] cVal = sVal.toCharArray();
        String sRetorno = sVal;
        for (int i = sVal.length() - 1; i >= 0; --i) {
            if (cVal[i] != ' ') {
                sRetorno = sVal.substring(0, i + 1);
                break;
            }
        }
        return sRetorno;
    }

    public static char getPontoDec() {
        return ',';
    }

    public static String setPontoDec(String sVal) {
        int iLocal = (sVal.indexOf(46) >= 0) ? sVal.indexOf(46) : sVal
                .indexOf(44);
        if (iLocal >= 0) {
            char[] cVal = new char[sVal.length()];
            cVal = sVal.toCharArray();
            cVal[iLocal] = getPontoDec();
            sVal = new String(cVal);
        }
        return sVal;
    }

    public static String substringByChar(String sVal, char cVal, boolean bOrient) {
        String sRetorno = "";
        sVal = copy(sVal, 0, sVal.length());
        char[] cStr = sVal.toCharArray();
        int i;
        if (bOrient) {
            for (i = 0; i < sVal.length(); ++i) {
                if (cStr[i] == cVal) {
                    break;
                }
                sRetorno = sRetorno + cStr[i];
            }
        } else {
            for (i = sVal.length() - 1; i >= 0; --i) {
                if (cStr[i] == cVal) {
                    break;
                }
                sRetorno = cStr[i] + sRetorno;
            }
        }
        return sRetorno;
    }

    public static String copy(String sTmp, int iPos, int iTam) {
        if (iTam == 0) {
            return sTmp;
        }
        if (sTmp == null) {
            sTmp = "";
        }
        if (sTmp.length() < iTam + 1) {
            sTmp = sTmp + replicate(" ", iTam - sTmp.length());
        } else {
            sTmp = sTmp.substring(iPos, iTam);
        }
        return sTmp;
    }

    public static String copy(String sTmp, int iTam) {
        return copy(sTmp, 0, iTam);
    }

    public static String limpaString(String sTexto) {
        String sResult = "";
        String sCaracs = "- .,;/\\";
        if (sTexto != null) {
            for (int i = 0; i < sTexto.length(); ++i) {
                if (sCaracs.indexOf(sTexto.substring(i, i + 1)) == -1) {
                    sResult = sResult + sTexto.substring(i, i + 1);
                }
            }
        }
        return sResult;
    }

    public static int contaMeses(java.util.Date dDataIni,
                                 java.util.Date dDataFim) {
        int iMeses = 0;
        try {
            iMeses = 0;
            GregorianCalendar cIni = new GregorianCalendar();
            GregorianCalendar cFim = new GregorianCalendar();
            cIni.setTime(dDataIni);
            cFim.setTime(dDataFim);
            iMeses = 1 + cFim.get(2) + 12 * (cFim.get(1) - cIni.get(1) - 1)
                    + 12 - cIni.get(2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iMeses;
    }

    public static String dateToStrExtenso(java.util.Date data) {
        String sRet = "";
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(data);
        int iDia = 0;
        int iAno = 0;
        int iMes = 0;
        if (data != null) {
            iDia = cal.get(5);
            iMes = cal.get(2) + 1;
            iAno = cal.get(1);
        }
        sRet = strZero(new StringBuffer().append(iDia).toString(), 2) + " de "
                + strMes(iMes).toLowerCase() + " de " + iAno;
        return sRet;
    }

    public static String strMes(int iMes) {
        String sRet = "";
        switch (iMes) {
            case 1:
                sRet = "Janeiro";
                break;
            case 2:
                sRet = "Fevereiro";
                break;
            case 3:
                sRet = "Março";
                break;
            case 4:
                sRet = "Abril";
                break;
            case 5:
                sRet = "Maio";
                break;
            case 6:
                sRet = "Junho";
                break;
            case 7:
                sRet = "Julho";
                break;
            case 8:
                sRet = "Agosto";
                break;
            case 9:
                sRet = "Setembro";
                break;
            case 10:
                sRet = "Outubro";
                break;
            case 11:
                sRet = "Novembro";
                break;
            case 12:
                sRet = "Dezembro";
        }

        return sRet;
    }

    public static Timestamp strToTimeStamp1(String source) throws ParseException {
        if (source.equals("") || source.equals("null")) {
            return null;
        }

        DateFormat df = new SimpleDateFormat(FMT_DT_HR1);
        return new Timestamp(df.parse(source).getTime());
    }

    public static Time strTimetoTime(String strtime) {
        Time time = null;
        try {
            strtime = StringFunctions.clearString(strtime);
            int hours = Integer.parseInt(strtime.substring(0, 2));
            int minutes = Integer.parseInt(strtime.substring(2, 4));
            int seconds = 0;
            if (strtime.length() > 4) {
                seconds = Integer.parseInt(strtime.substring(4));
            }
            Calendar cal = new GregorianCalendar();
            cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, hours);
            cal.set(Calendar.MINUTE, minutes);
            cal.set(Calendar.SECOND, seconds);
            time = new Time(cal.getTimeInMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    public static String doubleToStrCurrency(Double valor, String simbolo,
                                             int casasDecimais, boolean pos) {
        String retorno = "";
        BigDecimal bd = new BigDecimal(valor);
        bd.setScale(casasDecimais);
        if (pos) {
            retorno = simbolo + " " + bd.toString().replace(".", ",");
        } else {
            retorno = bd.toString().replace(".", ",") + " " + simbolo;
        }
        return retorno;
    }

    public static String doubleToStrCurExtenso(double dVal, String[] sMoeda) {
        String sRet = "";
        String[] sVals = String.valueOf(dVal).split("\\.");
        String sTmp = intToStrExtenso(new Integer(sVals[0]).intValue());
        if (!(sTmp.trim().equals(""))) {
            sRet = sTmp;
            if (sRet.substring(0, 2).indexOf("um") == 0) {
                sRet = sRet + " " + sMoeda[2];
            } else if (sRet.substring(sRet.length() - 2).indexOf("�o") == 0) {
                sRet = sRet + " de " + sMoeda[3];
            } else if (sRet.substring(sRet.length() - 3).indexOf("�es") == 0) {
                sRet = sRet + " de " + sMoeda[3];
            } else {
                sRet = sRet + " " + sMoeda[3];
            }
        }
        if ((!(sVals[1].equals(""))) && (new Integer(sVals[1]).intValue() > 0)) {
            if (!(sRet.equals(""))) {
                sTmp = " e ";
            } else {
                sTmp = " ";
            }
            sTmp = sTmp + intToStrExtenso(new Integer(sVals[1]).intValue());
            if (!(sTmp.trim().equals(""))) {
                if (sTmp.substring(0, 2).indexOf("um") < 4) {
                    sRet = sRet + sTmp + " " + sMoeda[0];
                } else {
                    sRet = sRet + sTmp + " " + sMoeda[1];
                }
            }
        }
        return sRet;
    }

    public static String intToStrExtenso(int iVal) {
        String sRet = "";
        int iTmp = 0;
        String[][] sNomes = {
                {"", "um", "dois", "tr�s", "quatro", "cinco", "seis", "sete",
                        "oito", "nove", "dez", "onze", "doze", "treze",
                        "quatorze", "quinze", "dezesseis", "dezesete",
                        "dezoito", "dezenove"},
                {"", "", "vinte", "trinta", "quarenta", "cinquenta",
                        "sessenta", "setenta", "oitenta", "noventa"},
                {"", "cem", "duzentos", "trezentos", "quatrocentos",
                        "quinhentos", "seiscentos", "setecentos", "oitocentos",
                        "novecentos"}};
        if (iVal == 1000000000) {
            sRet = sRet + sNomes[0][1] + " bilh�o";
            iVal = 0;
        }
        if (iVal > 999999999) {
            iTmp = iVal / 1000000000;
            sRet = sRet + intToStrExtenso(iTmp) + " bilh�es";
            iVal -= iTmp * 1000000000;
            sRet = sRet + ((iVal > 0) ? " e " : "");
        }
        if (iVal == 1000000) {
            sRet = sRet + sNomes[0][1] + " milh�o";
            iVal = 0;
        }
        if (iVal > 999999) {
            iTmp = iVal / 1000000;
            sRet = sRet + intToStrExtenso(iTmp) + " milh�es";
            iVal -= iTmp * 1000000;
            sRet = sRet + ((iVal > 0) ? " e " : "");
        }
        if (iVal > 999) {
            iTmp = iVal / 1000;
            sRet = sRet + ((iTmp > 1) ? intToStrExtenso(iTmp) + " mil" : "mil");
            iVal -= iTmp * 1000;

            if (iVal != 0) {
                int iCent = iVal / 100;
                int iDez = (iVal - (iCent * 100)) / 10;
                int iUnid = iVal - (iDez * 10 + iCent * 100);
                if ((iCent == 0) || ((iDez == 0) && (iUnid == 0))) {
                    sRet = sRet + " e ";
                } else {
                    sRet = sRet + " ";
                }
            }
        }
        if (iVal > 99) {
            iTmp = iVal / 100;
            sRet = sRet + sNomes[2][iTmp];
            iVal -= iTmp * 100;
            if ((sRet.equals("cem")) && (iVal > 0)) {
                sRet = "cento";
            }
            sRet = sRet + ((iVal > 0) ? " e " : "");
        }
        if (iVal > 19) {
            iTmp = iVal / 10;
            sRet = sRet + sNomes[1][iTmp];
            iVal -= iTmp * 10;
            sRet = sRet + ((iVal > 0) ? " e " : "");
        }
        if (iVal > 0) {
            iTmp = iVal;
            sRet = sRet + sNomes[0][iTmp];
        }
        return sRet;
    }

    public static char tiraAcento(char cKey) {
        char cTmp = cKey;

        if (contido(cTmp, "����")) {
            cTmp = 'a';
        } else if (contido(cTmp, "����")) {
            cTmp = 'A';
        } else if (contido(cTmp, "���")) {
            cTmp = 'e';
        } else if (contido(cTmp, "���")) {
            cTmp = 'E';
        } else if (contido(cTmp, "���")) {
            cTmp = 'i';
        } else if (contido(cTmp, "���")) {
            cTmp = 'I';
        } else if (contido(cTmp, "����")) {
            cTmp = 'o';
        } else if (contido(cTmp, "����")) {
            cTmp = 'O';
        } else if (contido(cTmp, "���")) {
            cTmp = 'u';
        } else if (contido(cTmp, "���")) {
            cTmp = 'U';
        } else if (contido(cTmp, "�")) {
            cTmp = 'c';
        } else if (contido(cTmp, "�")) {
            cTmp = 'C';
        }
        return cTmp;
    }

    public static String tiraAcentos(String sTexto) {
        String sRet = "";
        char[] cVals = sTexto.toCharArray();
        for (int i = 0; i < cVals.length; ++i) {
            cVals[i] = tiraAcento(cVals[i]);
        }
        sRet = new String(cVals);
        return sRet;
    }

    public static boolean contido(char cTexto, String sTexto) {
        boolean bRetorno = false;
        for (int i = 0; i < sTexto.length(); ++i) {
            if (cTexto == sTexto.charAt(i)) {
                bRetorno = true;
                break;
            }
        }
        return bRetorno;
    }

    public static BigDecimal transValorInv(String sVal) {
        BigDecimal bigRet = new BigDecimal("0.00");
        if (sVal == null) {
            return bigRet;
        }
        if (sVal.length() < 3) {
            sVal = "0" + replicate("0", 2 - sVal.length()) + sVal;
        }
        sVal = sVal.substring(0, sVal.length() - 2) + "."
                + sVal.substring(sVal.length() - 2);
        bigRet = new BigDecimal(sVal);
        return bigRet;
    }

    public static String transValor(String sValor, int iTam, int iDec,
                                    boolean bZeroEsq) {
        if (sValor == null) {
            sValor = "0";
        }

        String sDec = "";
        String sResult = sValor;
        for (int i = 0; i < sValor.length(); ++i) {
            if ((sValor.substring(i, i + 1).equals(".") | sValor.substring(i,
                    i + 1).equals(","))) {
                sResult = sValor.substring(0, i);
                sDec = sValor.substring(i + 1, sValor.length());
                if (sDec.length() < iDec) {
                    sDec = sDec + replicate("0", iDec - sDec.length());
                    break;
                }
                if (sDec.length() <= iDec) {
                    break;
                }
                sDec = sDec.substring(0, iDec);
                break;
            }
        }

        if ((sDec.trim().equals("") & iDec > 0)) {
            sDec = replicate("0", iDec);
        }
        if (sResult.length() > iTam - iDec) {
            sResult = sResult.substring(sResult.length() - (iTam - iDec) - 1,
                    iTam - iDec);
        }
        if ((bZeroEsq) && (sResult.length() < iTam - iDec)) {
            sResult = replicate("0", iTam - iDec - sResult.length()) + sResult;
        }

        return sResult + sDec;
    }

    public static String tiraChar(String sVal, String sChar) {
        String sRetorno = sVal;
        sVal = sVal.trim();
        int iPos = sVal.indexOf(sChar);
        if (iPos >= 0) {
            if (iPos < sVal.length() - 1) {
                sRetorno = sVal.substring(0, iPos) + sVal.substring(iPos + 1);
            } else {
                sRetorno = sVal.substring(0, iPos);
            }
        }
        return sRetorno;
    }

    public static String tiraString(String sTexto, String sParc) {
        String sRetorno = sTexto;
        int iPos = 0;
        if (!(sParc.equals(""))) {
            while (iPos > -1) {
                iPos = sRetorno.indexOf(sParc);
                if (iPos > -1) {
                    sRetorno = sRetorno.substring(0, iPos)
                            + sRetorno.substring(iPos + sParc.length(),
                            sRetorno.length());
                }
            }
        }
        return sRetorno;
    }

    public static String verData(String sData) {
        if (sData.length() < 10) {
            return "";
        }
        char[] cDate = sData.toCharArray();
        if (!(Character.isDigit(cDate[0]))) {
            return "";
        }
        if (!(Character.isDigit(cDate[1]))) {
            return "";
        }
        if (cDate[2] != '/') {
            return "";
        }
        if (!(Character.isDigit(cDate[3]))) {
            return "";
        }
        if (!(Character.isDigit(cDate[4]))) {
            return "";
        }
        if (cDate[5] != '/') {
            return "";
        }
        if (!(Character.isDigit(cDate[6]))) {
            return "";
        }
        if (!(Character.isDigit(cDate[7]))) {
            return "";
        }
        if (!(Character.isDigit(cDate[8]))) {
            return "";
        }
        if (!(Character.isDigit(cDate[9]))) {
            return "";
        }
        if (!(validaData(sData))) {
            return "";
        }
        return sData;
    }

    public static boolean validaData(String data) {
        boolean retorno = true;
        GregorianCalendar cal = null;
        if (data.length() < 10) {
            return false;
        }
        int ano = Integer.parseInt(data.substring(6, 10));
        int mes = Integer.parseInt(data.substring(3, 5));
        int dia = Integer.parseInt(data.substring(0, 2));

        cal = new GregorianCalendar(ano, mes - 1, dia);

        if ((mes > 12) | (ano == 0)) {
            retorno = false;
        } else if ((mes != (cal.get(Calendar.MONTH) + 1)) | (dia == 0)) {
            retorno = false;
        } else if ((ano != (cal.get(Calendar.YEAR))) | (mes == 0)) {
            retorno = false;
        }
        return retorno;
    }

    public static boolean validaTime(String time) {
        boolean retorno = true;
        if (time.length() != 8 && time.length() != 5) {
            return false;
        }

        int hora = Integer.parseInt(time.substring(0, 2));
        int minuto = Integer.parseInt(time.substring(3, 5));
        int segundo = time.length() == 5 ? 0 : Integer.parseInt(time.substring(
                6, 8));

        if ((hora > 23) || (hora < 0)) {
            retorno = false;
        } else if ((minuto > 59) || (minuto < 0)) {
            retorno = false;
        } else if ((segundo > 59) || (segundo < 0) && time.length() == 8) {
            retorno = false;
        }
        return retorno;
    }

    public static double strToDouble(String sVal) {
        try {
            sVal = (sVal == null || sVal.equals("")) ? "0.0" : sVal;
            sVal = sVal.replace(getPontoDec(), '.');
            int iPos = sVal.lastIndexOf('.');
            int iPosTmp = -1;
            if (iPos >= 0) {
                while (iPos != (iPosTmp = sVal.indexOf('.'))) {
                    sVal = sVal.substring(0, iPosTmp) + sVal.substring(iPosTmp + 1);
                    iPos--;
                }
            }
            return Double.parseDouble(sVal);
        } catch (Exception e) {
            return 0;
        }
    }

    public static String formatDouble(Double value, String mascara) {
        if (value == null) {
            return "0";
        } else {
            return new DecimalFormat(mascara).format(value);
        }
    }

    public static String getFmtValue(Tipo tipo, Object value) {
        value = (value == null ? "" : value);
        value = (value.equals("null") ? "" : value);

        String retorno = "";

        switch (tipo) {
            case MOEDA: {
                value = (value.equals("")) ? "0.0" : value;
                retorno = new DecimalFormat(MSK_MOEDA, FORMAT_SYMBOLS).format(value);
                break;
            }
            case MOEDA_2: {
                value = (value.equals("")) ? "0.0" : value;
                retorno = new DecimalFormat(MSK_MOEDA, FORMAT_SYMBOLS).format(value);
                break;
            }
            case MOEDA_3: {
                value = (value.equals("")) ? "0.0" : value;
                retorno = new DecimalFormat(MSK_MOEDA_3, FORMAT_SYMBOLS).format(value);
                break;
            }
            case MOEDA_4: {
                value = (value.equals("")) ? "0.0" : value;
                retorno = new DecimalFormat(MSK_MOEDA_4, FORMAT_SYMBOLS).format(value);
                break;
            }
            case NUMERICO: {
                value = (value.equals("")) ? "0.0" : value;
                retorno = new DecimalFormat(MSK_NUMERICO, FORMAT_SYMBOLS).format(value);
                break;
            }
            case QTADE: {
                value = (value.equals("")) ? "0.0" : value;
                retorno = new DecimalFormat(MSK_QTDADE, FORMAT_SYMBOLS).format(value);
                break;
            }
            case QTADE_0: {
                value = (value.equals("")) ? "0" : value;
                retorno = new DecimalFormat(MSK_QTDADE_0, FORMAT_SYMBOLS).format(value);
                break;
            }
            case QTADE_1: {
                value = (value.equals("")) ? "0.0" : value;
                retorno = new DecimalFormat(MSK_QTDADE_1, FORMAT_SYMBOLS).format(value);
                break;
            }
            case QTADE_2: {
                value = (value.equals("")) ? "0.0" : value;
                retorno = new DecimalFormat(MSK_QTDADE_2, FORMAT_SYMBOLS).format(value);
                break;
            }
            case QTADE_3: {
                value = (value.equals("")) ? "0.0" : value;
                retorno = new DecimalFormat(MSK_QTDADE_3, FORMAT_SYMBOLS).format(value);
                break;
            }
            case INTEIRO: {
                value = (value.equals("")) ? "0" : value;
                retorno = NumberFormat.getIntegerInstance(LBR).format(value).replace(".", "");
                break;
            }
            case SIM_NAO: {
                int val = Funcoes.strToInt(value.toString());
                retorno = (val == 0 ? "NAO" : "SIM");
                break;
            }
            case GRAUS: {
                retorno = String.valueOf(value) + "�";
                break;
            }
            case MM: {
                retorno = String.valueOf(value) + "mm";
                break;
            }
            case PERCENTUAL: {
                retorno = String.valueOf(value) + "%";
                break;
            }
            case X: {
                int val = Funcoes.strToInt(value.toString());
                retorno = (val == 0 ? "-" : "X");
                break;
            }
            case CODIGO: {
                retorno = (value.equals("") || value.toString().equals("0")) ? "" : NumberFormat.getIntegerInstance(LBR).format(value).replace(".", "");
                break;
            }
            case DATA: {
                try {
                    //retorno = (value.equals("")) ? "" : SimpleDateFormat.getDateInstance(SimpleDateFormat.DEFAULT, LBR).format(value);

                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    retorno = df.format(value);
                    if (retorno.equals("31/12/1969")) {
                        retorno = "";
                    }
                } catch (Exception e) {
                    retorno = "";
                }
                break;
            }
            case DATA_1: {
                retorno = (value.equals("")) ? "" : SimpleDateFormat.getDateInstance(SimpleDateFormat.DEFAULT, LBR).format(value);
                if (retorno.equals("31/12/1969")) {
                    retorno = "";
                }
                break;
            }
            case HORA: {
                retorno = (value.equals("")) ? "" : SimpleDateFormat.getTimeInstance(SimpleDateFormat.MEDIUM, LBR).format(value);
                break;
            }
            case DATA_HORA: {
            /*try {
                if (((Timestamp)value).getTime() == 0) {
					retorno = "";
					break;
				}
			retorno = (value.equals("")) ? "" : SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.DEFAULT, SimpleDateFormat.DEFAULT, LBR).format(value);
			} catch (Exception e) {
				retorno = "";
			}
			break;*/

                try {
                    //retorno = (value.equals("")) ? "" : SimpleDateFormat.getDateInstance(SimpleDateFormat.DEFAULT, LBR).format(value);

                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                    retorno = df.format(value);
                    if (retorno.equals("31/12/1969")) {
                        retorno = "";
                    }
                } catch (Exception e) {
                    retorno = "";
                }
                break;

            }
            case DATA_HORA_D: {
			/*try {
				if (((Timestamp)value).getTime() == 0) {
					retorno = "";
					break;
				}
			retorno = (value.equals("")) ? "" : SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.DEFAULT, SimpleDateFormat.DEFAULT, LBR).format(value);
			} catch (Exception e) {
				retorno = "";
			}
			break;*/

                try {
                    //retorno = (value.equals("")) ? "" : SimpleDateFormat.getDateInstance(SimpleDateFormat.DEFAULT, LBR).format(value);

                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                    retorno = df.format(value);
                    if (retorno.equals("31/12/1969")) {
                        retorno = "";
                    }
                } catch (Exception e) {
                    retorno = "";
                }
                break;

            }
            case CPF_CNPJ: {
                retorno = limpaString(value.toString());
                if (retorno.length() == 11) {
                    retorno = setMascara(retorno, MSK_CPF);
                } else if (retorno.length() == 14) {
                    retorno = setMascara(retorno, MSK_CNPJ);
                }
                break;
            }
            case CEP: {
                retorno = limpaString(value.toString());
                if (retorno.length() >= 8) {
                    retorno = setMascara(retorno, MSK_CEP);
                }
                break;
            }
            case FONE: {

                retorno = limpaString(value.toString());

                if (retorno.length() == 8) {
                    retorno = setMascara(retorno, MSK_FONE_0000);
                } else if (retorno.length() == 10) {
                    retorno = setMascara(retorno, MSK_FONE_DDD_COMUM);
                } else if (retorno.length() == 11) {
                    retorno = setMascara(retorno, MSK_FONE_9);
                } else {
                    retorno = setMascara(retorno, MSK_FONE_DDI);
                }

                break;
            }
            default: {
                retorno = value.toString();
                break;
            }
        }

        return retorno;
    }

    public static String ArrayToStr(Object[] values) {
        String retorno = "";
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                if (retorno.length() > 0) {
                    retorno = retorno.concat(",");
                }
                values[i] = (values[i] == null) ? "" : values[i];
                retorno = retorno.concat(values[i].toString());
            }
        }
        return retorno;
    }

    public static String[] StrToArray(String value, String separator) {
        if ((value != null) && (!value.equals(""))) {
            return value.split(separator);
        } else {
            return null;
        }
    }

    public static String[] ArrayObjectToStrArray(ArrayList<Object> list) {
        String[] ret = new String[list.size()];

        for (int i = 0; i < ret.length; i++) {
            if (list.get(i) != null) {
                ret[i] = list.get(i).toString();
            }
        }

        return ret;
    }

    public static String adicEspacosEsquerda(String sTexto, int iEspacos) {
        if (iEspacos > sTexto.length()) {
            sTexto = replicate(" ", iEspacos - sTexto.length()) + sTexto;
        }
        return sTexto;
    }

    public static String adicEspacosEsquerda(String sTexto, int iEspacos, int tMax) {
        if (sTexto == null) {
            sTexto = "";
        }
        if (sTexto.length() > tMax) {
            sTexto = sTexto.substring(0, tMax);
        }
        if (iEspacos > sTexto.length()) {
            sTexto = replicate(" ", iEspacos - sTexto.length()) + sTexto;
        }
        return sTexto;
    }

    public static String adicEspacosDireita(String sTexto, int iEspacos) {
        if (iEspacos > sTexto.length()) {
            sTexto = sTexto + replicate(" ", iEspacos - sTexto.length());
        }
        return sTexto;
    }

    public static String adicEspacosDireita(String sTexto, int iEspacos, int tMax) {
        if (sTexto.length() > tMax) {
            sTexto = sTexto.substring(0, tMax);
        }
        if (iEspacos > sTexto.length()) {
            sTexto = sTexto + replicate(" ", iEspacos - sTexto.length());
        }
        return sTexto;
    }

    public static String alinhaDir(int iValor, int iTam) {
        return alinhaDir(iValor, iTam);
    }

    public static String alinhaCentro(int iValor, int iTam) {
        return alinhaCentro(iValor, iTam);
    }

    public static String alinhaCentro(String sVal, int iTam) {
        int iTamIni = 0;
        String sRetorno = "";
        if (sVal == null) {
            sVal = "";
        }
        iTamIni = sVal.length();
        if (iTamIni > iTam) {
            sVal = sVal.substring(0, iTam);
        } else if (iTam == sVal.length()) {
            return sVal;
        } else {
            sRetorno = "";
            for (int i = iTamIni; i < iTam; ++i) {
                sRetorno = sRetorno + ' ';
            }
        }
        return sRetorno.substring(sRetorno.length() / 2) + sVal + sRetorno.substring(sRetorno.length() / 2);
    }

    public static String alinhaDir(String sVal, int iTam) {
        int iTamIni = 0;
        String sRetorno = "";
        if (sVal == null) {
            sVal = "";
        }
        iTamIni = sVal.length();
        if (iTamIni > iTam) {
            sRetorno = sVal.substring(0, iTam);
        } else if (iTam == sVal.length()) {
            return sVal;
        } else {
            sRetorno = sVal;
            for (int i = iTamIni; i < iTam; ++i) {
                sRetorno = ' ' + sRetorno;
            }
        }
        return sRetorno;
    }

    public static String concatena(Object... values) {
        String retorno = "";

        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                retorno = retorno + getFmtValue(Tipo.TEXTO, values[i]) + ";";
            }
        }

        return retorno;
    }

    public static Date strToDate(String source, String parse) throws ParseException {
        DateFormat df = new SimpleDateFormat(parse);
        Date date = dateToSQLDate(df.parse(source));
        return date;
    }

    public static Date strToDateYYYYMMDD(String source) throws ParseException {
        return strToDate(source, "yyyy-MM-dd");
    }

    public static Date strToDateDDMMYYYY(String source) throws ParseException {
        return strToDate(source, "dd/MM/yyyy");
    }

    public static Timestamp strToTimeStamp(String source) throws ParseException {
        if (source.equals("")) {
            return null;
        }
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        return new Timestamp(df.parse(source).getTime());
    }

    public static int strToInt(String value) {
        value = (value == null || value.equals("")) ? "0" : value;
        value = value.replace(".", "");
        value = value.trim();

        return Integer.parseInt(value);
    }

    public static boolean contemArray(int[] whereIndex, int value) {
        for (int j = 0; j < whereIndex.length; j++) {
            if (whereIndex[j] == value) {
                return true;
            }
        }
        return false;
    }

    public static Date strToDate(String source) throws ParseException {
        if (source.indexOf("/") > 0) {
            return strToDateDDMMYYYY(source);
        } else {
            return strToDateYYYYMMDD(source);
        }
    }

    public static Timestamp strToDateTime(String _strDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
            java.util.Date parsedDate = dateFormat.parse(_strDate.concat(".000"));
            Timestamp timestamp = new Timestamp(parsedDate.getTime());

            return timestamp;
        } catch (Exception e) { //this generic but you can control another types of exception
            // look the origin of excption
            return null;
        }
    }
}
