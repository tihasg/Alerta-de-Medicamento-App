package com.app.alertamedicamento.util.validadores;

import java.math.BigDecimal;

/**
 * Validador de Numeros
 */
final public class Numeros {

    private Numeros() {
        throw new AssertionError();
    }

    /**
     * Verificar se o numero da String eh um Numero.
     *
     * @param numero Numero.
     * @return Verdadeiro caso seja Numero. Falso, caso contrario.
     */
    public static boolean isNumber(String numero) {
        try {
            return isBigDecimal(numero);
        } catch (RuntimeException exception) {
            return false;
        }
    }

    /**
     * Verificar se o numero da String eh um Short.
     *
     * @param numero Numero.
     * @return Verdadeiro caso seja Short. Falso, caso contrario.
     */
    public static boolean isShort(String numero) {
        try {
            Short.valueOf(numero);
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    /**
     * Verificar se o numero da String eh um inteiro.
     *
     * @param numero Numero.
     * @return Verdadeiro caso seja inteiro. Falso, caso contrario.
     */
    public static boolean isInteger(String numero) {
        try {
            Long.valueOf(numero);
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    /**
     * Verificar se o numero da String eh um Double.
     *
     * @param numero Numero.
     * @return Verdadeiro caso seja Double. Falso, caso contrario.
     */
    public static boolean isDouble(String numero) {
        try {
            Double.valueOf(numero);
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    /**
     * Verificar se o número da String e um Float.
     *
     * @param numero Numero.
     * @return Verdadeiro caso seja Float. Falso, caso contrario.
     */
    public static boolean isFloat(String numero) {
        try {
            Float.valueOf(numero);
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    /**
     * Verificar se o numero da String e um BigDecimal.
     *
     * @param numero Numero.
     * @return Verdadeiro caso seja BigDecimal. Falso, caso contrario.
     */
    public static boolean isBigDecimal(String numero) {
        try {
            new BigDecimal(numero);
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

    /**
     * Verificar se o numero da String e um Long.
     *
     * @param numero Numero.
     * @return Verdadeiro caso seja Long. Falso, caso contrario.
     */
    public static boolean isLong(String numero) {
        try {
            Long.valueOf(numero);
            return true;
        } catch (RuntimeException exception) {
            return false;
        }
    }

}