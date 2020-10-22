package com.app.alertamedicamento.util.formatadores;

import com.app.alertamedicamento.util.exception.SysException;
import com.app.alertamedicamento.util.validadores.Numeros;

/**
 * Formatador de CPF.
 *
 * @author Desconhecido
 * @company Desconhecido
 */
final public class CPF {

    private CPF() {
        throw new AssertionError();
    }

    /**
     * Obter um CPF qualquer e formata-lo. Qualquer caracter diferente de
     * numeros sera ignorado. Portanto, um CPF do tipo 1111c11b11122a sera
     * formatado para 111.111.111-11
     *
     * @param cpf Numero do CPF.
     * @return CPF formatado.
     */
    public static String formatar(String cpf) {
        String cpfSoNumeros = limpar(cpf);
        // Verificar tamanho do CPF.
        if (cpfSoNumeros.length() != 11)
            throw new SysException("CPF inválido. Tamanho de um CPF válido é 11. Este CPF possui " + cpfSoNumeros.length() + " números.");
        StringBuilder sb = new StringBuilder();
        sb.append(cpfSoNumeros.substring(0, 3));
        sb.append(".");
        sb.append(cpfSoNumeros.substring(3, 6));
        sb.append(".");
        sb.append(cpfSoNumeros.substring(6, 9));
        sb.append("-");
        sb.append(cpfSoNumeros.substring(9, 11));
        return sb.toString();
    }

    /**
     * Limpar o CPF mantendo somente os números. Não verifica se é um CPF válido.
     *
     * @param cpf CPF que deve ser limpado.
     * @return CPF apenas com números.
     */
    public static String limpar(String cpf) {
        if (cpf == null)
            throw new SysException("O CPF informado é nulo.");
        if ("".equals(cpf))
            throw new SysException("O CPF informado é vazio.");
        char[] chars = cpf.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int indice = 0; indice < chars.length; indice++) {
            if (Numeros.isInteger(String.valueOf(chars[indice]))) {
                sb.append(chars[indice]);
            }
        }
        return sb.toString();
    }
}