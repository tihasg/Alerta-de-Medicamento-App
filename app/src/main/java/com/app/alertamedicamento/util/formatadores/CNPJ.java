package com.app.alertamedicamento.util.formatadores;

import com.app.alertamedicamento.util.exception.SysException;
import com.app.alertamedicamento.util.texto.Texto;

/**
 * Formatador de CNPJ.
 *
 * @author Desconhecido
 * @company Desconhecido
 */
public final class CNPJ {

    private CNPJ() {
        throw new AssertionError();
    }

    /**
     * Formatar um CNPJ.
     *
     * @param cnpj CNPJ a ser formatado.
     * @return CNPJ formatado.
     */
    public static String formatar(String cnpj) {
        StringBuilder s = new StringBuilder();
        String soNumeros = Texto.manterNumeros(cnpj);
        if ((soNumeros.length() != 14) && (soNumeros.length() > 0))
            throw new SysException("Informe um CNPJ válido. Este CNPJ possui apenas " + soNumeros.length() + " números. Um CNPJ válido deve conter 15.");
        s.append(soNumeros.substring(0, 2));
        s.append(".");
        s.append(soNumeros.substring(2, 5));
        s.append(".");
        s.append(soNumeros.substring(5, 8));
        s.append("/");
        s.append(soNumeros.substring(8, 12));
        s.append("-");
        s.append(soNumeros.substring(12, 14));
        return s.toString();
    }

}