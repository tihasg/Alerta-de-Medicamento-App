package com.app.alertamedicamento.util;

import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class Const {

    public final static Locale LBR = new Locale("pt", "BR");
    public final static DecimalFormatSymbols REAL = new DecimalFormatSymbols(LBR);
    public final static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("###,###,##0.00", REAL);
    public final static String DATE_FORMAT = "dd/MM/yyyy";
    public final static String TIME_FORMAT = "hh:mm";
    public final static String TIMESTAMP_FORMAT = "dd/MM/yyyy - hh:mm";
    public static final String DEFAULT_CHARSET_NAME = "ISO-8859-1";
    public static final Charset DEFAULT_CHARSET = Charset.forName(DEFAULT_CHARSET_NAME);

}
