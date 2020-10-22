package com.app.alertamedicamento.util.fmt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Daniel Schroeder
 * @company SCHR-SISTEMAS
 */
public final class FormatString {

    private static final char[] CARACTERES_ESPECIAIS = {225, 193, 227, 195,
            226, 194, 224, 192, 233, 201, 234, 202, 237, 205, 243, 211, 245,
            213, 244, 212, 250, 218, 252, 220, 231, 199};
    private static final char[] CARACTERES_SUBSTITUTOS = {'a', 'A', 'a', 'A',
            'a', 'A', 'a', 'A', 'e', 'E', 'e', 'E', 'i', 'I', 'o', 'O', 'o',
            'O', 'o', 'O', 'u', 'U', 'u', 'U', 'c', 'C'};

    private FormatString() {
    }

    public static String substituiPontoPorVirgula(String valorParcela) {
        String retorno = "";
        retorno = valorParcela.replaceAll(",", ".");
        return retorno;
    }

    public static String formataInt(int valor, int tamanho) {
        StringBuffer sb = new StringBuffer(Integer.toString(valor));
        while (sb.length() < tamanho) {
            sb.insert(0, '0');
        }
        return sb.toString();
    }

    public static String formataLong(long valor, int tamanho) {
        StringBuffer sb = new StringBuffer(Long.toString(valor));
        while (sb.length() < tamanho) {
            sb.insert(0, '0');
        }
        return sb.toString();
    }

    public static String retiraCasaDecimal(double nmr, int casasDecimais) {
        String num = new Double(nmr).toString();
        int inteiro = (int) nmr;
        int posicao = num.indexOf(".");
        String decimais = num.substring(posicao + 1);
        String parte1 = new Integer(inteiro).toString();
        String parte2 = null;

        if (casasDecimais > decimais.length()) {
            parte2 = preencherComZeros(decimais, casasDecimais, true);
        } else {
            parte2 = decimais.substring(0, casasDecimais);
        }

        return parte1 + parte2;
    }

    public static String completaComBrancos(String valor, int tamanho) {
        if ((valor != null) && (valor.length() > tamanho)) {
            String completo = valor;
            valor = completo.substring(0, tamanho);
            return valor;
        }

        StringBuffer sb = new StringBuffer((valor == null) ? "" : valor);
        while (sb.length() < tamanho) {
            sb.append(' '); // "&nbsp;"
        }
        return sb.toString();
    }

    public static String completaComBrancos(String valor, int tamanho, boolean esquerda) {
        if (!(esquerda)) {
            return completaComBrancos(valor, tamanho);
        }

        StringBuffer sb = new StringBuffer((valor == null) ? "" : valor);
        while (sb.length() < tamanho) {
            sb.insert(0, ' ');
        }

        return sb.toString();
    }

    public static String completaComBrancos(int valor, int tamanho) {
        return completaComBrancos(new Integer(valor).toString(), tamanho);
    }

    public static String completaComBrancos(char valor, int tamanho) {
        return completaComBrancos(new Character(valor).toString(), tamanho);
    }

    public static String completaComBrancos(double valor, int tamanho) {
        if (valor > 2147483647.0D) {
            return completaComBrancos(new Double(valor).toString(), tamanho);
        }
        return completaComBrancos(new Integer((int) valor).toString(), tamanho);
    }

    public static String completaComZeros(String valor, int tamanho) {
        if ((valor != null) && (valor.length() > tamanho)) {
            String completo = valor;
            valor = completo.substring(0, tamanho);
            return valor;
        }

        StringBuffer sb = new StringBuffer((valor == null) ? "" : valor);
        while (sb.length() < tamanho) {
            sb.insert(0, '0');
        }
        return sb.toString();
    }

    public static String completaComZeros(int valor, int tamanho) {
        String val = new Integer(valor).toString();
        StringBuffer sb = new StringBuffer((val == null) ? "" : val);
        while (sb.length() < tamanho) {
            sb.insert(0, '0');
        }
        return sb.toString();
    }

    public static String completaComZeros(double valor, int tamanho) {
        String val = new Integer((int) valor).toString();
        StringBuffer sb = new StringBuffer((val == null) ? "" : val);
        while (sb.length() < tamanho) {
            sb.insert(0, '0');
        }
        return sb.toString();
    }

    public static String completaComZeros(char valor, int tamanho) {
        return completaComZeros(new Character(valor).toString(), tamanho);
    }

    public static String preencherComZeros(String valor, int casas) {
        StringBuffer sb = new StringBuffer(valor);
        while (sb.length() < casas) {
            sb.insert(0, "0");
        }
        return sb.toString();
    }

    public static String preencherComZeros(String valor, int casas, boolean direita) {
        if (!(direita)) {
            return preencherComZeros(valor, casas);
        }

        StringBuffer sb = new StringBuffer(valor);
        while (sb.length() < casas) {
            sb.append("0");
        }
        return sb.toString();
    }

    public static String preencherComZeros(int valor, int casas, boolean direita) {
        String val = new Integer(valor).toString();

        return preencherComZeros(val, casas, direita);
    }

    public static String preencherComZeros(double valor, int casas, boolean direita) {
        String val = String.valueOf(valor);
        return preencherComZeros(val, casas, direita);
    }

    public static boolean ehEmailValido(String email) {
        Pattern pat = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher match = pat.matcher(email);
        return match.matches();
    }

    public static String formatarData(Date data) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",
                new Locale("pt", "BR"));
        if (data != null) {
            return sdf.format(data);
        }
        return "";
    }

    public static String formatar(String pString, String pMascara) {
        return formatar(pString, pMascara, false);
    }

    public static String formatar(int pInteiro, String pMascara) {
        return formatar(Integer.toString(pInteiro), pMascara, false);
    }

    public static String formatar(long pLong, String pMascara, boolean pAlinhadoDireita) {
        return formatar(Long.toString(pLong), pMascara, pAlinhadoDireita);
    }

    public static String formatar(double pDouble, String pMascara, boolean pAlinhadoDireita) {
        int casasdecimais = pMascara.length() - pMascara.indexOf(44) - 1;
        String texto = Long.toString(Math.round(pDouble * Math.pow(10.0D, casasdecimais)));
        texto = preencherComZeros(texto, casasdecimais + 1);
        if (pDouble != 0.0D) {
            return formatar(texto, pMascara, pAlinhadoDireita);
        }
        return "0,00";
    }

    public static String formatar(String pString, String pMascara, boolean pAlinhadoDireita) {
        if ((pString == null) || (pString.equals("")) || (pString.substring(0, 1).equals("-"))) {
            return "";
        }
        if ((pMascara == null) || (pMascara.equals(""))) {
            pMascara = "X";
        }

        StringBuffer temp = new StringBuffer("");
        int contMasc = (pAlinhadoDireita) ? pMascara.length() - 1 : 0;
        int cont = (pAlinhadoDireita) ? pString.length() - 1 : 0;
        char charMasc = pMascara.charAt(contMasc);

        while ((cont < pString.length()) && (cont >= 0)) {
            if ((charMasc != '9') && (charMasc != 'X') && (charMasc != 'A')) {
                if (pAlinhadoDireita) {
                    temp.insert(0, charMasc);
                } else {
                    temp.append(charMasc);
                }
            } else {
                char c = pString.charAt(cont);
                if (charMasc == '9') {
                    if ((c >= '0') && (c <= '9')) {
                        if (pAlinhadoDireita) {
                            temp.insert(0, c);
                        } else {
                            temp.append(c);
                        }
                    } else if (pAlinhadoDireita) {
                        ++contMasc;
                    } else {
                        --contMasc;
                    }
                } else if (charMasc == 'A') {
                    if (((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z'))
                            || (c == ' ')) {
                        if (pAlinhadoDireita) {
                            temp.insert(0, c);
                        } else {
                            temp.append(c);
                        }
                    } else if (pAlinhadoDireita) {
                        ++contMasc;
                    } else {
                        --contMasc;
                    }

                } else if (pAlinhadoDireita) {
                    temp.insert(0, c);
                } else {
                    temp.append(c);
                }

                if (pAlinhadoDireita) {
                    --cont;
                } else {
                    ++cont;
                }
            }

            if (pAlinhadoDireita) {
                --contMasc;
            } else {
                ++contMasc;
            }
            if ((contMasc < 0) || (contMasc >= pMascara.length())) {
                charMasc = 'X';
            } else {
                charMasc = pMascara.charAt(contMasc);
            }
        }

        return temp.toString();
    }

    /**
     * @param pString
     * @param pMascara
     * @return
     */
    public static String desformatar(String pString, String pMascara) {
        int i = 0;
        if (pString.length() != 0) {
            if (pMascara.length() == 0) {
                return pString;
            }
            StringBuffer temp = new StringBuffer();
            while (true) {
                if ((pMascara.charAt(i) == '9') || (pMascara.charAt(i) == 'X')
                        || (pMascara.charAt(i) == 'A')) {
                    temp.append(pString.charAt(i));
                }
                ++i;
                if (i >= pString.length()) {
                    return temp.toString();
                }
            }
        }
        return "";
    }

    /**
     * @param pString
     * @param pMascara
     * @return
     */
    public static String desformatarEsquerda(String pString, String pMascara) {
        return desformatar(pString, pMascara.substring(0, pString.length()));
    }

    /**
     * @param pString
     * @param pMascara
     * @return
     */
    public static String desformatarDireita(String pString, String pMascara) {
        return desformatar(pString, pMascara.substring(pMascara.length() - pString.length()));
    }

    /**
     * @param pCadeia
     * @return
     */
    public static String desnulificar(String pCadeia) {
        return ((pCadeia == null) ? "" : pCadeia.trim());
    }

    /**
     * @param origem
     * @return
     */
    public static String trocarEspacoEmBrancoPorSubscrito(String origem) {
        StringTokenizer st = new StringTokenizer(origem);
        String destino = "";
        while (st.hasMoreTokens()) {
            destino = destino + "_" + st.nextToken();
        }
        return destino;
    }

    /**
     * @param pStr
     * @return
     */
    public static String removerAcentos(String pStr) {
        StringBuffer str = new StringBuffer(pStr);
        for (int i = 0; i < pStr.length(); ++i) {
            char c = pStr.charAt(i);
            for (int j = 0; j < CARACTERES_ESPECIAIS.length; ++j) {
                if (c != CARACTERES_ESPECIAIS[j]) {
                    continue;
                }
                str.setCharAt(i, CARACTERES_SUBSTITUTOS[j]);
                break;
            }
        }
        return str.toString();
    }
}
