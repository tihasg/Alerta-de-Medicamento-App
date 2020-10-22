package com.app.alertamedicamento.util.fmt;

/**
 * @author Desconhecido
 * @company Desconhecido
 */
public final class StringFunctions {

    public static String ltrim(String text) {
        if (text == null || "".equals(text)) {
            return "";
        }
        while (text.charAt(0) == ' ') {
            text = text.substring(1);
        }
        return text;
    }

    public static String alltrim(String text) {
        if (text == null || "".equals(text)) {
            return "";
        }
        text = ltrim(text.trim());
        return text;
    }

    public static String clearString(String str) {
        return clearString(str, "");
    }

    public static String clearStringOld(String str) {
        String sResult = "";
        String sCaracs = "=<>-.,;/\\?";
        if (str != null) {
            for (int i = 0; i < str.length(); i++) {
                if (sCaracs.indexOf(str.substring(i, i + 1)) == -1) {
                    sResult = sResult + str.substring(i, i + 1);
                }
            }
        }
        return sResult;
    }

    public static String clearString(String str, String strnew) {
        if (str == null) {
            return "";
        }
        StringBuilder validstring = new StringBuilder();
        validstring.append(str.replaceAll("\\W", strnew));
        return validstring.toString().trim();
    }
}
