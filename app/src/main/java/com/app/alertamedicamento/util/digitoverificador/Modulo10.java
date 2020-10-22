package com.app.alertamedicamento.util.digitoverificador;

import com.app.alertamedicamento.util.exception.SysException;
import com.app.alertamedicamento.util.texto.Texto;

/**
 * Utilitário para Geração de DV pelo método Módulo 10.
 *
 * @author Desconhecido
 * @company Desconhecido
 */
public final class Modulo10 {

    private Modulo10() {
        throw new AssertionError();
    }

    /**
     * Calcular um dígito verificador a partir de uma sequência de numeros
     * enviada.
     *
     * @param fonte Sequencia de numeros para cálculo do DV
     * @return DV gerado.
     */
    public static String obterDV(String fonte) {
        validarFonte(fonte);
        int pesoAlternado = 2;
        int dv = 0;
        String parcela;
        for (int i = fonte.length() - 1; i >= 0; i--) {
            parcela = String.valueOf(Integer.parseInt(fonte.substring(i, i + 1)) * pesoAlternado);
            for (int j = 0; j < parcela.length(); j++) {
                dv += Integer.parseInt(parcela.substring(j, j + 1));
            }
            if (pesoAlternado == 2) {
                pesoAlternado--;
            } else {
                pesoAlternado++;
            }
        }
        dv = dv % 10;
        if (dv == 0) {
            return "0";
        }
        return String.valueOf(10 - dv);
    }

    /**
     * Verifica se a fonte possui apenas números.
     *
     * @param fonte Texto a ser validado
     */
    private static void validarFonte(String fonte) {
        if (Texto.manterNumeros(fonte).length() != fonte.length()) {
            throw new SysException("Texto informado contém caracteres não numéricos!");
        }
    }

    /**
     * Calcular um dígito verificador com a quantidade de casas indicadas
     * a partir de uma sequencia de numérios enviada.
     *
     * @param fonte             Sequência de numeros para calculo do DV
     * @param quantidadeDigitos Quantidade de dígitos a serem retornados
     * @return DV gerado.
     */
    public static String obterDV(String fonte, int quantidadeDigitos) {
        if (quantidadeDigitos > 1) {
            String parcial = obterDV(fonte);
            return parcial + obterDV(fonte + parcial, --quantidadeDigitos);
        } else {
            return obterDV(fonte);
        }
    }

    /**
     * Calcular um digito verificador a partir de uma sequencia de numeros
     * enviada.
     * O maior peso usado atinge a base, retorna a 2
     *
     * @param fonte   Sequencia de numeros para calculo do DV
     * @param base    Valor da base que se deseja usar para o calculo do DV
     * @param subZero Caracter que deve substituir o resultado quando o resto for 0
     * @param subUm   Caracter que deve substituir o resultado quando o resto for 1
     * @return DV gerado.
     */
    public static String obterDVBaseParametrizada(String fonte, int base,
                                                  char subZero, char subUm) {
        validarFonte(fonte);
        int peso = 2;
        int dv = 0;
        for (int i = fonte.length() - 1; i >= 0; i--) {
            dv += Integer.parseInt(fonte.substring(i, i + 1)) * peso;
            if (peso == base - 1) {
                peso = 2;
            } else {
                peso++;
            }
        }
        dv = dv % 10;
        if (dv > 1) {
            return String.valueOf(10 - dv);
        } else if (dv == 1) {
            return String.valueOf(subUm);
        }
        return String.valueOf(subZero);

    }

    /**
     * Calcular um digito verificador usando o modulo 10, com
     * a quantidade de casas indicadas a partir de uma sequÃªncia de
     * numeros enviada.
     *
     * @param fonte             Sequencia de numeros para calculo do DV
     * @param base              Valor da base que se deseja usar para o cÃ¡lculo do DV
     * @param subZero           Caracter que deve substituir o resultado quando o resto for 0
     * @param subUm             Caracter que deve substituir o resultado quando o resto for 1
     * @param quantidadeDigitos Quantidade de dÃ­gitos a serem retornados
     * @return DV gerado.
     */
    public static String obterDVBaseParametrizada(String fonte, int base,
                                                  char subZero, char subUm, int quantidadeDigitos) {
        if (quantidadeDigitos > 1) {
            String parcial = obterDVBaseParametrizada(fonte, base, subZero, subUm);
            return parcial + obterDVBaseParametrizada(fonte + parcial, base, subZero, subUm, --quantidadeDigitos);
        } else {
            return obterDVBaseParametrizada(fonte, base, subZero, subUm);
        }
    }

    /**
     * Calcular um digito verificador a partir de uma sequencia de numeros
     * enviada e uma constante a ser acrescida ao somatÃ³rio.
     * O maior peso usado atinge a base, retorna a 2
     *
     * @param fonte     Sequencia de numeros para culculo do DV
     * @param base      Valor da base que se deseja usar para o calculo do DV
     * @param subZero   Caracter que deve substituir o resultado quando o resto for 0
     * @param subUm     Caracter que deve substituir o resultado quando o resto for 1
     * @param constante Valor que deve ser acrescido ao somatÃ³rio durante o cÃ¡lculo
     * @return DV gerado.
     */
    public static String obterDVBaseParametrizadaComConstante(String fonte,
                                                              int base, char subZero, char subUm, int constante) {
        validarFonte(fonte);
        int peso = 2;
        int dv = constante;
        for (int i = fonte.length() - 1; i >= 0; i--) {
            dv += Integer.parseInt(fonte.substring(i, i + 1)) * peso;
            if (peso == base - 1) {
                peso = 2;
            } else {
                peso++;
            }
        }
        dv = dv % 10;
        if (dv > 1) {
            return String.valueOf(10 - dv);
        } else if (dv == 1) {
            return String.valueOf(subUm);
        }
        return String.valueOf(subZero);
    }

}

