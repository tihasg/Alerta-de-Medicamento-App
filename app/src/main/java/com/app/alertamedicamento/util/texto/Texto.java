package com.app.alertamedicamento.util.texto;

import com.app.alertamedicamento.util.Const;
import com.app.alertamedicamento.util.validadores.Numeros;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utilitários para manipulação de Textos.
 *
 * @author Desconhecido
 * @company Desconhecido
 */
public class Texto {

    private Texto() {
        throw new AssertionError();
    }

    /**
     * Manter no Texto apenas os números.
     *
     * @param str Texto.
     * @return Texto contendo apenas os números.
     */
    public static String manterNumeros(String str) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            if (Numeros.isNumber(String.valueOf(str.charAt(i)))) {
                s.append(str.charAt(i));
            }
        }
        return s.toString();
    }

    /**
     * Incluir uma determinada quantidade de vezes um determinado caracter no início do texto.
     *
     * @param texto Texto que teráo caracter inserido no início.
     * @param c     Caracter que será incluído.
     * @param q     Quantidade de vezes que o caracter será incluído.
     * @return Texto com os caracteres incluídos.
     */
    public static String incluirCaracterInicio(String texto, char c, int q) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < q; i++)
            s.append(c);
        s.append(texto);
        return s.toString();
    }

    /**
     * Capitalizar a primeira letra de todas as palavras do texto.
     *
     * @param texto Texto que terá as palavras capitalizadas.
     * @return Texto com a formatação aplicada.
     */
    public static String capitalizarIniciais(String texto) {
        String[] split = texto.split(" ");
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < split.length; i++) {
            s.append(String.valueOf(split[i].charAt(0)).toUpperCase());
            s.append(split[i].substring(1, split[i].length()));
            s.append(" ");
        }
        return s.toString().trim();
    }

    /**
     * Contar a quantidade de vezes que uma palavra ocorre em um texto.
     * Não usar caracteres especiais na palavra a ser pesquisada.
     *
     * @param texto      Texto.
     * @param palavra    Palavra que será contada no texto.
     * @param ignoreCase Considerar maíusculas ou minúsculas.
     * @return Quantidade de vezes que a palavra ocorre.
     */
    public static int contarQuantidadePalavra(String texto, String palavra, boolean ignoreCase) {
        if (ignoreCase) {
            texto = texto.toLowerCase();
            palavra = palavra.toLowerCase();
        }
        Pattern padrao = Pattern.compile(palavra);
        Matcher pesquisa = padrao.matcher(texto);
        int r = 0;
        while (pesquisa.find())
            r++;
        return r;
    }

    /**
     * Trocar os caracteres acentuados por seus equivalentes sem acentuação.
     *
     * @param frase Frase que terá os caracteres trocados.
     * @return Frase com os caracteres trocados.
     */
    public static String trocarCaracteresAcentuados(String frase) {
        if (frase == null) return frase;
        return frase.replaceAll("[ãâàáä]", "a")
                .replaceAll("[êèéë]", "e")
                .replaceAll("[îìíïĩ]", "i")
                .replaceAll("[õôòóö]", "o")
                .replaceAll("[ûúùüũ]", "u")
                .replaceAll("[ÃÂÀÁÄ]", "A")
                .replaceAll("[ÊÈÉË]", "E")
                .replaceAll("[ÎÌÍĨÏ]", "I")
                .replaceAll("[ÕÔÒÓÖ]", "O")
                .replaceAll("[ÛÙÚŨÜ]", "U")
                .replace('ç', 'c')
                .replace('Ç', 'C')
                .replace('ñ', 'n')
                .replace('Ñ', 'N');
    }

    /**
     * Remove os caracteres de pontuação.
     *
     * @param frase Frase que terá as pontuações removidas.
     * @return Frase sem pontuações.
     */
    public static String removerPontuacao(String frase) {
        if (frase == null) return frase;
        return frase.replaceAll("[-!,.:;?/]", "")
                .replace("\b\t\n\f\r\"\'\\", "");
    }

    /**
     * Obter a cadeia de caracteres que forma o alfabeto brasileiro em minï¿½sculas.
     *
     * @return Array de caracteres.
     */
    public static char[] obterAlfabetoMinusculas() {
        return new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'x', 'y', 'w', 'z'};
    }

    /**
     * Obter a cadeia de caracteres que forma o alfabeto brasileiro em minï¿½sculas.
     *
     * @return Array de caracteres.
     */
    public static char[] obterAlfabetoMaiusculas() {
        return new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'X', 'Y', 'W', 'Z'};
    }

    /**
     * Comparar textos de diversas formas, de acordo com os parâmetros passados.
     *
     * @param texto1           Texto para comparação
     * @param texto2           Texto com o qual texto1 deve ser comparado
     * @param ignoraMaiuscula  Se a comparação não será "case sensitive"
     * @param ignoraEspaco     Se a comparação deve ignorar a presença de espaços
     * @param ignoraAcentuacao Se a comparação não deve levar em conta a acentuação
     *                         nos textos comparados
     * @param ignoraPontuacao  Se a comparação deve ignorar os caracteres de pontuação.
     * @return Comparação entre texto1 e texto2 na mesma forma de texto1.compareTo(texto2).
     */
    public static int comparar(String texto1, String texto2,
                               boolean ignoraMaiuscula, boolean ignoraEspaco, boolean ignoraAcentuacao,
                               boolean ignoraPontuacao) {
        if (ignoraMaiuscula) {
            texto1 = texto1.toLowerCase();
            texto2 = texto2.toLowerCase();
        }
        if (ignoraEspaco) {
            texto1 = texto1.replace(" ", "");
            texto2 = texto2.replace(" ", "");
        }
        if (ignoraAcentuacao) {
            texto1 = trocarCaracteresAcentuados(texto1);
            texto2 = trocarCaracteresAcentuados(texto2);
        }
        if (ignoraPontuacao) {
            texto1 = removerPontuacao(texto1);
            texto2 = removerPontuacao(texto2);
        }
        return texto1.compareTo(texto2);
    }

    /**
     * Completa o texto até o tamanho especificado com o caractere
     * desejado à esquerda ou à direita. Se o texto informado tiver
     * tamanho maior ou igual ao desejado, retorna o texto original.
     *
     * @param original     Texto original
     * @param caracter     Caracter usado para preenchimento
     * @param tamanhoFinal Tamanho total do texto de retorno
     * @param aoFinal      Indicador se o texto deve receber caracteres
     *                     à direita (TRUE) ou à esquerda (FALSE)
     * @return Texto preenchido com caracteres na forma solicitada
     */
    public static String completar(String original, char caracter,
                                   int tamanhoFinal, boolean aoFinal) {
        if (original.length() >= tamanhoFinal) { // Não há o que completar
            return original;
        }
        while (original.length() < tamanhoFinal) {
            if (aoFinal) {
                original = original + caracter;
            } else {
                original = caracter + original;
            }
        }
        return original;
    }

    /**
     * Remove caracteres brancos do início e do final do texto.
     *
     * @param original Texto original
     * @return Texto sem caracteres brancos no início e ao final
     */
    public static String removerBrancosExtremidades(String original) {
        int inicio = 0;
        int fim = original.length() - 1;
        while (inicio < original.length()) {
            if (original.charAt(inicio) != '\t' && original.charAt(inicio) != '\n' && original.charAt(inicio) != ' ') {
                break;
            }
            inicio++;
        }
        while (fim >= 0) {
            if (original.charAt(fim) != '\t' && original.charAt(fim) != '\n' && original.charAt(fim) != ' ') {
                break;
            }
            fim--;
        }
        if (fim < inicio) {
            return "";
        }
        return original.substring(inicio, fim);
    }

    public static String convertCharset(String value, Charset charset) {
        try {
            // Constructs a new decoder for this charset.
            CharsetDecoder decoder = charset.newDecoder();

            // Constructs a new encoder for this charset.
            CharsetEncoder encoder = charset.newEncoder();

            // Wrap the character sequence into a buffer.
            CharBuffer uCharBuffer = CharBuffer.wrap(value);

            // Encode the remaining content of a single input character buffer to a new byte buffer.
            // Converts to ISO-8859-1 bytes and stores them to the byte buffer
            ByteBuffer bbuf = encoder.encode(uCharBuffer);

            // Decode the remaining content of a single input byte buffer to a new character buffer.
            // Converts from ISO-8859-1 bytes and stores them to the character buffer
            CharBuffer cbuf = decoder.decode(bbuf);

            String s = cbuf.toString();

            return s;

        } catch (CharacterCodingException e) {
            return value;
        }
    }

    public static String convertCharset(String value) {
        return convertCharset(value, Const.DEFAULT_CHARSET);
    }

}