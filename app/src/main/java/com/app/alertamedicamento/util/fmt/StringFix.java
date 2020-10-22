package com.app.alertamedicamento.util.fmt;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Desconhecido
 * @company Desconhecido
 */
public class StringFix {

    public static String[] REPLACES = {"a", "e", "i", "o", "u", "c", "A", "E", "I", "O", "U", "C"};
    public static Pattern[] PATTERNS = null;

    /**
     * Compile Patterns
     */
    public static void compilePatterns() {
        PATTERNS = new Pattern[REPLACES.length];
        PATTERNS[0] = Pattern.compile("[âãáàä]");
        PATTERNS[1] = Pattern.compile("[éèêë]");
        PATTERNS[2] = Pattern.compile("[íìîï]");
        PATTERNS[3] = Pattern.compile("[óòôõö]");
        PATTERNS[4] = Pattern.compile("[úùûü]");
        PATTERNS[5] = Pattern.compile("[ç]");
        PATTERNS[6] = Pattern.compile("[ÂÃÁÀÄ]");
        PATTERNS[7] = Pattern.compile("[ÉÈÊË]");
        PATTERNS[8] = Pattern.compile("[ÍÌÎÏ]");
        PATTERNS[9] = Pattern.compile("[ÓÒÔÕÖ]");
        PATTERNS[10] = Pattern.compile("[ÚÙÛÜ]");
        PATTERNS[11] = Pattern.compile("[Ç]");
    }

    /**
     * Replace!
     *
     * @param text
     * @return
     */
    public static String replaceSpecial(String text) {
        if (PATTERNS == null) {
            compilePatterns();
        }

        String result = text;
        for (int i = 0; i < PATTERNS.length; ++i) {
            Matcher matcher = PATTERNS[i].matcher(result);
            result = matcher.replaceAll(REPLACES[i]);
        }
        return result;
    }
}
