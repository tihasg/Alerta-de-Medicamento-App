package com.app.alertamedicamento.util.formatadores;

import com.app.alertamedicamento.util.exception.SysException;
import com.app.alertamedicamento.util.texto.Texto;

/**
 * Formatador de Telefones.
 *
 * @author Desconhecido
 * @company Desconhecido
 */
final public class Telefone {

    private Telefone() {
        throw new AssertionError();
    }

    /**
     * Formatar um Telefone.
     * A formatação depende da quantidade de numeros informados.
     * Por exemplo:
     * 12345678 será formatado para 1234-5678
     * 1234567890 será formatado para (12) 3456-7890
     * 1112345678 será formatado para +11 (12) 3456-7890
     *
     * @param telefone Telefone a ser formatado.
     * @return Telefone formatado.
     */
    public static String formatar(String telefone) {
        if (telefone == null || telefone.length() < 8) {
            throw new SysException("Informe um Telefone Válido.");
        }
        String soNumeros = Texto.manterNumeros(telefone);
        StringBuilder s = new StringBuilder();
        if (soNumeros.length() == 8) {
            s.append(telefone.substring(0, 4));
            s.append("-");
            s.append(telefone.substring(4, 8));
        }
        if (soNumeros.length() == 10) {
            s.append("(");
            s.append(telefone.substring(0, 2));
            s.append(") ");
            s.append(telefone.substring(2, 6));
            s.append("-");
            s.append(telefone.substring(6, 10));
        }
        if (soNumeros.length() == 11) {
            s.append("(");
            s.append(telefone.substring(0, 2));
            s.append(") ");
            s.append(telefone.substring(2, 7));
            s.append("-");
            s.append(telefone.substring(6, 11));
        }
        if (soNumeros.length() == 12) {
            s.append("+");
            s.append(telefone.substring(0, 2));
            s.append(" (");
            s.append(telefone.substring(2, 4));
            s.append(") ");
            s.append(telefone.substring(4, 8));
            s.append("-");
            s.append(telefone.substring(8, 12));
        }
        return (s.length() == 0 ? telefone : s.toString());
    }

}