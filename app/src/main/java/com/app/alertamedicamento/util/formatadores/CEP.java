package com.app.alertamedicamento.util.formatadores;

import com.app.alertamedicamento.util.exception.SysException;

/**
 * Formatador de CEP.
 *
 * @author Desconhecido
 * @company Desconhecido
 */
final public class CEP {

    private CEP() {
        throw new AssertionError();
    }

    /**
     * Realizar a formatação do CEP passado.
     * A formatação é do tipo XX.XXX-XXX.
     *
     * @param cep CEP a ser formatado.
     * @param s   Se é uma formatação simples.
     * @return CEP formatado.
     */
    public static String formatar(String cep, boolean s) {
        if ("".equals(cep) || cep.length() != 8) {
            throw new SysException("Informe um CEP válido.");
        }
        if (s) {
            return new StringBuilder().append(cep.substring(0, 5)).append("-").append(cep.substring(5, 8)).toString();
        } else {
            return new StringBuilder().append(cep.substring(0, 2)).append(".").append(cep.substring(2, 5)).append("-").append(cep.substring(5, 8)).toString();
        }
    }

}