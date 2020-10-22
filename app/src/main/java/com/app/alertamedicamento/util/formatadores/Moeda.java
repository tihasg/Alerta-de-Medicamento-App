package com.app.alertamedicamento.util.formatadores;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Formatador de Moedas.
 *
 * @author Desconhecido
 * @company Desconhecido
 */
final public class Moeda {

    /**
     * Formatar para Extenso um valor monetÃ¡rio em Real.
     *
     * @param valor Valor a ser formatado.
     * @return Valor formatado.
     */
    public static String formatar(Double valor) {
        Extenso extenso = new Extenso(valor);
        return extenso.toString();
    }

    /**
     * Converte o valor Double para a expressÃ£o da moeda em um formato padrao do local especificado
     *
     * @param valor      Double que deveser formatado
     * @param moedaLocal Indicacao do padrao de formatacao
     * @return Valor convertido (ex: 100.00 -> R$100,00)
     */
    public static String formatar(Double valor, MoedaLocal moedaLocal) {
        return new DecimalFormat(moedaLocal.formato, new DecimalFormatSymbols(moedaLocal.locale)).format(valor);
    }

    /**
     * Converte o valor Double para a expressaoo da moeda no formato especificado, ignorando o padrao do local
     *
     * @param valor      Double que deveser formatado
     * @param moedaLocal Indicacao do padrao de formatacao
     * @param formato    Formato para conversao
     * @return Valor convertido
     */
    public static String formatar(Double valor, MoedaLocal moedaLocal, String formato) {
        return new DecimalFormat(formato, new DecimalFormatSymbols(moedaLocal.locale)).format(valor);
    }

    public enum MoedaLocal {
        ESTADOS_UNIDOS(Locale.US, "Â¤ ###,###,##0.00"),
        ALEMANHA(Locale.GERMANY, "Â¤ ###,###,##0.00"),
        FRANCA(Locale.FRANCE, "Â¤ ###,###,##0.00"),
        ITALIA(Locale.ITALY, "Â¤ ###,###,##0.00"),
        REINO_UNIDO(Locale.UK, "Â¤ ###,###,##0.00"),
        BRASIL(new Locale("pt", "BR"), "Â¤ ###,###,##0.00");

        private Locale locale;
        private String formato;

        private MoedaLocal(Locale locale, String formato) {
            this.locale = locale;
            this.formato = formato;
        }

    }
}

/**
 * Classe para escrever um valor por extenso
 * Obtida em http://www.portaljava.com/forum/posts/list/33515.page
 *
 * @author Desconhecido
 */
class Extenso {
    private ArrayList<Integer> nro;
    private BigInteger num;

    private String qualificadores[][] = {
            {"centavo", "centavos"},
            {"", ""},
            {"mil", "mil"},
            {"milhão", "milhões"},
            {"bilhão", "bilhões"},
            {"trilhão", "trilhões"},
            {"quatrilhão", "quatrilhões"},
            {"quintilhão", "quintilhões"},
            {"sextilhão", "sextilhões"},
            {"septilhão", "septilhões"}
    };
    private String numeros[][] = {
            {"zero", "um", "dois", "três", "quatro", "cinco", "seis", "sete", "oito", "nove", "dez",
                    "onze", "doze", "treze", "quatorze", "quinze", "desesseis", "desessete", "dezoito", "desenove"},
            {"vinte", "trinta", "quarenta", "cinquenta", "sessenta", "setenta", "oitenta", "noventa"},
            {"cem", "cento", "duzentos", "trezentos", "quatrocentos", "quinhentos", "seiscentos",
                    "setecentos", "oitocentos", "novecentos"}
    };


    /**
     * Construtor
     */
    public Extenso() {
        nro = new ArrayList<Integer>();
    }


    /**
     * Construtor
     *
     * @param dec valor para colocar por extenso
     */
    public Extenso(BigDecimal dec) {
        this();
        setNumber(dec);
    }


    /**
     * Construtor para colocar o objeto por extenso
     *
     * @param dec valor para colocar por extenso
     */
    public Extenso(double dec) {
        this();
        setNumber(dec);
    }

    final public void setNumber(BigDecimal dec) {
        // Converte para inteiro arredondando os centavos
        num = dec
                .setScale(2, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .toBigInteger();

        // Adiciona valores
        nro.clear();
        if (num.equals(BigInteger.ZERO)) {
            // Centavos
            nro.add(Integer.valueOf(0));
            // Valor
            nro.add(Integer.valueOf(0));
        } else {
            // Adiciona centavos
            addRemainder(100);

            // Adiciona grupos de 1000
            while (!num.equals(BigInteger.ZERO)) {
                addRemainder(1000);
            }
        }
    }

    final public void setNumber(double dec) {
        setNumber(new BigDecimal(dec));
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        int ct;

        for (ct = nro.size() - 1; ct > 0; ct--) {
            if (buf.length() > 0 && !ehGrupoZero(ct)) {
                buf.append(" e ");
            }
            buf.append(numToString(((Integer) nro.get(ct)).intValue(), ct));
        }
        if (buf.length() > 0) {
            if (ehUnicoGrupo())
                buf.append(" de ");
            while (buf.toString().endsWith(" "))
                buf.setLength(buf.length() - 1);
            if (ehPrimeiroGrupoUm())
                buf.insert(0, "");
            if (nro.size() == 2 && ((Integer) nro.get(1)).intValue() == 1) {
                buf.append(" real");
            } else {
                buf.append(" reais");
            }
            if (((Integer) nro.get(0)).intValue() != 0) {
                buf.append(" e ");
            }
        }
        if (((Integer) nro.get(0)).intValue() != 0) {
            buf.append(numToString(((Integer) nro.get(0)).intValue(), 0));
        }
        return buf.toString();
    }

    private boolean ehPrimeiroGrupoUm() {
        if (((Integer) nro.get(nro.size() - 1)).intValue() == 1)
            return true;
        return false;
    }

    private void addRemainder(int divisor) {
        BigInteger[] newNum = num.divideAndRemainder(BigInteger.valueOf(divisor));
        nro.add(Integer.valueOf(newNum[1].intValue()));
        num = newNum[0];
    }

    // FIXME Quando Ã© Um milhÃ£o, ele coloca, por exemplo, UM MILHÃƒO E CEM MIL DE REAIS... Este "DE" nÃ£o existe.
    private boolean ehUnicoGrupo() {
        if (nro.size() <= 3)
            return false;
        if (!ehGrupoZero(1) && !ehGrupoZero(2))
            return false;
        boolean hasOne = false;
        for (int i = 3; i < nro.size(); i++) {
            if (((Integer) nro.get(i)).intValue() != 0) {
                if (hasOne)
                    return false;
                hasOne = true;
            }
        }
        return true;
    }

    boolean ehGrupoZero(int ps) {
        if (ps <= 0 || ps >= nro.size())
            return true;
        return ((Integer) nro.get(ps)).intValue() == 0;
    }

    private String numToString(int numero, int escala) {
        int unidade = (numero % 10);
        int dezena = (numero % 100); //* nao pode dividir por 10 pois verifica de 0..19
        int centena = (numero / 100);
        StringBuffer buf = new StringBuffer();

        if (numero != 0) {
            if (centena != 0) {
                if (dezena == 0 && centena == 1) {
                    buf.append(numeros[2][0]);
                } else {
                    buf.append(numeros[2][centena]);
                }
            }

            if ((buf.length() > 0) && (dezena != 0)) {
                buf.append(" e ");
            }
            if (dezena > 19) {
                dezena /= 10;
                buf.append(numeros[1][dezena - 2]);
                if (unidade != 0) {
                    buf.append(" e ");
                    buf.append(numeros[0][unidade]);
                }
            } else if (centena == 0 || dezena != 0) {
                buf.append(numeros[0][dezena]);
            }

            buf.append(" ");
            if (numero == 1) {
                buf.append(qualificadores[escala][0]);
            } else {
                buf.append(qualificadores[escala][1]);
            }
        }

        return buf.toString();
    }

}