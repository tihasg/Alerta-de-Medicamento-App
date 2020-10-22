package com.app.alertamedicamento.util.digitoverificador;

import com.app.alertamedicamento.util.exception.SysException;
import com.app.alertamedicamento.util.texto.Texto;

/**
 * Utilitario para Geracao de DV pela atribuicao dos pesos de acordo com a posicao
 * dos numeros na sequencia numerica informada.
 *
 * @author Desconhecido
 * @company Desconhecido
 */
public final class PesoPosicional {

    public PesoPosicional() {
        throw new AssertionError();
    }

    /**
     * Calcular um digito verificador a partir de uma sequencia de nÃºmeros
     * enviada.
     *
     * @param fonte Sequencia de numeros para calculo do DV
     * @return DV gerado.
     */
    public static String obterDV(String fonte) {
        validarFonte(fonte);
        int dv = 0;
        for (int i = 0; i < fonte.length(); i++) {
            dv += Integer.parseInt(fonte.substring(i, i + 1)) * (i + 1);
        }
        return String.valueOf(dv % 9);
    }

    /**
     * Verifica se a fonte possui apenas numeros.
     *
     * @param fonte Texto a ser validado
     */
    private static void validarFonte(String fonte) {
        if (Texto.manterNumeros(fonte).length() != fonte.length()) {
            throw new SysException("Texto informado contem caracteres nao numericos!");
        }
    }
}
