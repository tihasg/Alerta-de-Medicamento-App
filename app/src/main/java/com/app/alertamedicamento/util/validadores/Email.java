package com.app.alertamedicamento.util.validadores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validador de E-mails.
 */
final public class Email {

    private Email() {
        throw new AssertionError();
    }

    /**
     * Verificar se um e-mail e valido.
     *
     * @param email E-mail a ser validado.
     * @return Verdadeiro caso seja valido. Falso, caso contrario.
     */
    public static boolean isValido(String email) {
        Pattern pattern = Pattern.compile(Diversos.EMAIL, Pattern.MULTILINE);
        Matcher m = pattern.matcher(email);
        return m.matches();
    }

}