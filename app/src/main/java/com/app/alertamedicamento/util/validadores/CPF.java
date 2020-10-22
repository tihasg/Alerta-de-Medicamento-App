package com.app.alertamedicamento.util.validadores;

import com.app.alertamedicamento.util.digitoverificador.Modulo11;
import com.app.alertamedicamento.util.texto.Texto;

/**
 * Validador de CNPJ.
 */
final public class CPF {

    private CPF() {
        throw new AssertionError();
    }

    /**
     * Verificar se um CPF e valido.
     *
     * @param cpf CPF a ser verificado.
     * @return Verdadeiro caso seja valido. Falso, caso contrario.
     */
    public static boolean isValido(String cpf) {
        cpf = Texto.manterNumeros(cpf);
        if (cpf.length() != 11)
            return false;
        String numDig = cpf.substring(0, 9);
        return gerarDigitoVerificador(numDig).equals(cpf.substring(9, 11));
    }

    /**
     * Gerar um CPF arbitrário.
     * Código obtido de http://www.javafree.org/artigo/851371/Validacao-de-CPF.html.
     * Todos os direitos são do autor do código.
     *
     * @return CPF gerado arbitrariamente.
     */
    public static String gerar() {
        StringBuilder iniciais = new StringBuilder();
        Integer numero;
        for (int i = 0; i < 9; i++) {
            numero = Integer.valueOf((int) (Math.random() * 10));
            iniciais.append(numero.toString());
        }
        return iniciais.toString() + gerarDigitoVerificador(iniciais.toString());
    }

    /**
     * Método que calcula o dígito verificador, observando se está correto.
     * Código obtido de http://www.javafree.org/artigo/851371/Validacao-de-CPF.html.
     * Todos os direitos são do autor do código.
     *
     * @param num
     * @return Dígito verificador.
     */
    public static String gerarDigitoVerificador(String num) {
        return Modulo11.obterDV(num, false, 2);
    }

}