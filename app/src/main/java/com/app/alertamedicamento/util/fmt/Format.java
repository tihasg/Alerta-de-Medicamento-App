package com.app.alertamedicamento.util.fmt;

import com.app.alertamedicamento.util.Funcoes;

import java.sql.Date;
import java.sql.Time;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author Desconhecido
 * @company Desconhecido
 */
public final class Format {

    /**
     * Regiao: Brasil | Idioma: Portugues
     */
    public static final Locale lBR = new Locale("pt", "BR");
    /**
     * CPF: ###.###.###-##
     */
    public static final int cpf = 314;
    /**
     * CNPJ: ##.###.###/####-##
     */
    public static final int cnpj = 232;
    /**
     * Primeiramente. Indentifica qual tipo é: cpf ou cnpj.
     * Retorna formatado conforme tipo.
     */
    public static final int cpf_cnpj = 532;
    /**
     * CEP: ##.###-###
     */
    public static final int cep = 351;
    /**
     * FONE: (##)####-####
     */
    public static final int fone = 94;
    /**
     * Retorna objeto do tipo texto
     */
    public static final int texto = 166;
    /**
     * Retorna texto minusculo
     */
    public static final int textoMinusculo = 832;
    /**
     * Retorna texto maiusculo
     */
    public static final int textoMaiusculo = 137;
    /**
     * REAL: R$ 9.999,99
     */
    public static final int real = 159;
    /**
     * DOLAR: U$ 9,999.99
     */
    public static final int dolar = 667;
    /**
     * EURO: EUR 9,999.99
     */
    public static final int euro = 348;
    /**
     * Retorna valor no formato inteiro
     */
    public static final int inteiro = 19;
    /**
     * Retorna valor no formato numerico
     */
    public static final int numerico = 448;
    /**
     * Retorna valor no formato porcentagem
     */
    public static final int porcentagem = 322;
    /**
     * Retorna valor no formato boleano
     */
    public static final int boleano = 462;
    /**
     * 1: ATIVO
     * <br>
     * 2:INATIVO
     */
    public static final int ativoInativo = 645;
    /**
     * true: SIM
     * <br>
     * false: NA�O
     */
    public static final int simNao = 851;
    /**
     * true: X
     * <br>
     * false: _
     */
    public static final int _X = 591;
    /**
     * Retorna um objeto
     */
    public static final int objeto = 751;
    /**
     * 1: F
     * <br>
     * 2: M
     */
    public static final int masculino_feminino = 56;
    /**
     * -1: DEBITO
     * <br>
     * 0: NULO
     * <br>
     * 1: CREDITO
     */
    public static final int _plano = 901;
    /**
     * 1: ENTRADA
     * <br>
     * 2: SAIDA
     */
    public static final int _entradaSaida = 302;
    /**
     * Retorna tipoPessoa:
     * 1 : FISICA
     * <br>
     * 2 : JURIDICA
     */
    public static final int tipoPessoa = 493;
    /**
     * Texto centralizado, conforme tamanho.
     */
    public static final int centralizado = 734;
    /**
     * Texto justificado a esquerda, conforme tamanho.
     */
    public static final int justificadoEsquerda = 784;
    /**
     * Texto justificado a direita, conforme tamanho.
     */
    public static final int justificadoDireita = 794;
    /**
     * Tipo O.S.
     * <p>
     * 1 - NORMAL
     * 2 - ORCAMENTO
     * 3 - GARANTIA
     * 4 - INTERNA
     */
    public static final int tipo_os = 36895;
    /**
     * Local O.S.
     * 1 - Interno
     * 2 - Externo
     */
    public static final int local_os = 92365;

    private Format() {
    }

    public static String adicEspacosEsquerda(String sTexto, int iEspacos) {
        if (sTexto == null) {
            sTexto = "";
        }
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
        if (sTexto == null) {
            sTexto = "";
        }
        if (iEspacos > sTexto.length()) {
            sTexto = sTexto + replicate(" ", iEspacos - sTexto.length());
        }
        return sTexto;
    }

    public static String adicEspacosDireita(String sTexto, int iEspacos, int tMax) {
        if (sTexto == null) {
            sTexto = "";
        }
        if (sTexto.length() > tMax && tMax > 0) {
            sTexto = sTexto.substring(0, tMax);
        }
        if (iEspacos > sTexto.length()) {
            sTexto = sTexto + replicate(" ", iEspacos - sTexto.length());
        }
        return sTexto;
    }

    public static String replicate(String texto, int Quant) {
        StringBuffer sRetorno = new StringBuffer();
        sRetorno.append("");
        for (int i = 0; i < Quant; ++i) {
            sRetorno.append(texto);
        }
        return sRetorno.toString();
    }

    public static String adicionaEspacos(String sTexto, int iTamanho) {
        int iTamIni = 0;
        String sRetorno = "";
        if (sTexto == null) {
            sTexto = "";
        }
        iTamIni = sTexto.length();
        if (iTamIni > iTamanho) {
            sRetorno = sTexto.substring(0, iTamanho);
        } else {
            sRetorno = sTexto;
            for (int i = iTamIni; i < iTamanho; ++i) {
                sRetorno = sRetorno + ' ';
            }
        }
        return sRetorno;
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

    public static String alinhaDir(int iValor, int iTam) {
        return alinhaDir(iValor, iTam);
    }

    public static String alinhaDir(String sVal, int iTam, String flagP) {
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
                sRetorno = flagP + sRetorno;
            }
        }
        return sRetorno;
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

    public static Object getValue(Object value, Integer tipo) {
        if (value == null) {
            return "";
        } else {
            switch (tipo) {
                case cpf: /*CPF*/
                    return Funcoes.setMascara(value.toString(), "###.###.###-##");
                case cnpj: /*CNPJ*/
                    return Funcoes.setMascara(value.toString(), "##.###.###/####-##");
                case cpf_cnpj: /*CPF_CNPJ*/
                    if (value.toString().length() == 11) {
                        return Funcoes.setMascara(value.toString(), "###.###.###-##");
                    } else if (value.toString().length() == 14) {
                        return Funcoes.setMascara(value.toString(), "##.###.###/####-##");
                    } else {
                        return value;
                    }
                case cep: /*CEP*/
                    return Funcoes.setMascara(value.toString(), "##.###-###");
                case fone: /*FONE*/
                    return Funcoes.setMascara(value.toString(), "(##)####-####");
                case texto: /*TEXTO*/
                    return value.toString();
                case textoMaiusculo: /*TEXTO MAIUSCULO*/
                    return value.toString().toUpperCase();
                case textoMinusculo: /*TEXTO MINUSCULO*/
                    return value.toString().toLowerCase();
                case real: /*REAL*/
                    return NumberFormat.getCurrencyInstance(lBR).format(value);
                case dolar: /*DOLAR*/
                    return NumberFormat.getCurrencyInstance(Locale.US).format(value);
                case euro: /*EURO*/
                    return NumberFormat.getCurrencyInstance(Locale.GERMANY).format(value);
                case inteiro: /*INTEIRO*/
                    return NumberFormat.getIntegerInstance(lBR).format(value);
                case numerico: /*NUMERICO*/
                    return NumberFormat.getNumberInstance(lBR).format(value);
                case porcentagem: /*PORCENTAGEM*/
                    return NumberFormat.getPercentInstance(lBR).format(((Double) value) / 100
                    );
                case boleano: /*BOLEANO*/
                    return Boolean.valueOf(value.toString());
                case ativoInativo: /*ATIVO_INATIVO*/
                    if ((Boolean) value) {
                        return "ATIVO";
                    } else {
                        return "INATIVO";
                    }
                case simNao: /*SIM_NÃO*/
                    if ((Boolean) value) {
                        return "SIM";
                    } else {
                        return "NÃO";
                    }
                case _X: /*_X*/
                    if ((Boolean) value) {
                        return "X";
                    } else {
                        return " ";
                    }
                case masculino_feminino: /*MASCULINO_FEMININO*/
                    if (Integer.parseInt(value.toString()) == 1) {
                        return "F";
                    } else {
                        return "M";
                    }
                case tipoPessoa: /*TIPO_PESSOA*/
                    if (Integer.parseInt(value.toString()) == 1) {
                        return "FISICA";
                    } else {
                        return "JURIDICA";
                    }
                case _entradaSaida: /*ENTRADA_SAIDA*/
                    if (Integer.parseInt(value.toString()) == 1) {
                        return "ENTRADA";
                    } else {
                        return "SAIDA";
                    }
                case tipo_os: /*Tipo O.S.*/
                    return value;
                case local_os: /*Local O.S.*/
                    return value;
                case _plano: /*PLANO CONTA*/
                    if (Integer.parseInt(value.toString()) == -1) {
                        return "DEBITO";
                    } else if (Integer.parseInt(value.toString()) == 1) {
                        return "CREDITO";
                    } else {
                        return "NULO";
                    }
                default:
                    if (value instanceof Date) {
                        return SimpleDateFormat.getDateInstance(SimpleDateFormat.DEFAULT, lBR).format(value);
                    } else if (value instanceof Time) {
                        return SimpleDateFormat.getTimeInstance(SimpleDateFormat.MEDIUM, lBR).format(value);
                    } else {
                        return value;
                    }
            }
        }
    }

    public static String getValue(Object value, Integer tipo, int tamanho) {
        return Funcoes.adicEspacosDireita(String.valueOf(getValue(value, tipo)), tamanho);
    }

    public static String getValue(Object value, Integer tipo, int tamanho, int alinhamento) {
        switch (alinhamento) {
            case centralizado:
                return Funcoes.alinhaCentro(String.valueOf(getValue(value, tipo)), tamanho);
            case justificadoDireita:
                return Funcoes.alinhaDir(String.valueOf(getValue(value, tipo)), tamanho);
            default:
                return Funcoes.adicEspacosDireita(String.valueOf(getValue(value, tipo)), tamanho);
        }
    }
}
