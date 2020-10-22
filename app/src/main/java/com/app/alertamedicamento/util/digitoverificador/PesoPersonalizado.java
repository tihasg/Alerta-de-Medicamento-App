package com.app.alertamedicamento.util.digitoverificador;

import com.app.alertamedicamento.util.exception.SysException;
import com.app.alertamedicamento.util.texto.Texto;

import java.util.ArrayList;
import java.util.List;

/**
 * Utilitario para Geração de DV pela atribuição dos pesos parametrizada.
 *
 * @author Desconhecido
 * @company Desconhecido
 */
public final class PesoPersonalizado {

    private PesoPersonalizado() {
        throw new AssertionError();
    }

    /**
     * Calcular um digito verificador a partir de uma sequencia de numeros
     * enviada e de uma sequencia de pesos.
     *
     * @param fonte Sequencia de numeros para calculo do DV
     * @param peso  Sequencia de pesos para calculo do DV
     * @return DV gerado.
     */
    public static String obterDV(String fonte, String peso, String forma) {
        List<String> pesoSplit = new ArrayList<String>();
        StringBuilder sbSplitter = new StringBuilder();
        for (int splitter = 0; splitter < peso.length(); splitter++) {
            if (splitter == peso.length() - 1) {
                sbSplitter.append(peso.charAt(splitter));
                pesoSplit.add(sbSplitter.toString());
                sbSplitter = new StringBuilder();
            } else if (peso.charAt(splitter) == '|') {
                pesoSplit.add(sbSplitter.toString());
                sbSplitter = new StringBuilder();
            } else {
                sbSplitter.append(peso.charAt(splitter));
            }
        }
        validar(fonte, pesoSplit);
        int dv = 0;
        for (int i = 0; i < fonte.length(); i++) {
            dv += Integer.valueOf(String.valueOf(fonte.charAt(i))) * Integer.parseInt(String.valueOf(pesoSplit.get(i)));
        }
        dv = dv % 11;
        if (forma.equals("caracterDireito")) {
            if (dv < 10) {
                return String.valueOf(dv);
            }
            return String.valueOf(dv - 10);
        } else if (forma.equals("mod11")) {
            dv = 11 - dv;
            if (dv > 9) dv -= 10;
            return String.valueOf(dv);
        } else {
            return null;
        }
    }

    /**
     * Verifica se a fonte possui apenas numeros.
     *
     * @param fonte Texto a ser validado
     * @param peso  Peso a ser validado
     */
    private static void validar(String fonte, List<String> peso) {
        for (int i = 0; i < peso.size(); i++) {
            try {
                Integer.valueOf(peso.get(i));
            } catch (Exception e) {
                throw new SysException("Peso informado contem caracteres nao numericos!");
            }
        }
        if (Texto.manterNumeros(fonte).length() != peso.size()) {
            throw new SysException("Texto e peso possuem tamanhos diferentes!");
        }
    }

}