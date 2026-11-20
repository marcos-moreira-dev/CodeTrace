package com.mmdev.codetrace.symbols;

import java.util.List;
import java.util.Objects;

/**
 * Small, curated keyboard reference. These are physical-key hints, not
 * commands for inserting text into the typing editor.
  * <p><strong>Engineering rationale.</strong> The symbol palette provides reference glyphs for a chosen keyboard layout without inventing new syntax rules. Keeping the catalogue separate allows copy/paste assistance while the typing model remains authoritative for every expected character.</p>
*/
public final class SymbolCatalog {
    public enum Layout {
        LATAM("Español latinoamericano"),
        US("Inglés (EE. UU.)");

        private final String label;
        Layout(String label) { this.label = label; }
        public String label() { return label; }
    }

    public record Symbol(String glyph, String name, String latinAmericanKeys,
                         String usKeys) {
        public Symbol {
            Objects.requireNonNull(glyph);
            Objects.requireNonNull(name);
            Objects.requireNonNull(latinAmericanKeys);
            Objects.requireNonNull(usKeys);
        }

        public String keys(Layout layout) {
            return layout == Layout.LATAM ? latinAmericanKeys : usKeys;
        }
    }

    private static final List<Symbol> SYMBOLS = List.of(
        new Symbol("{", "Llave de apertura", "Mayús + tecla [ (junto a Ñ)", "Mayús + ["),
        new Symbol("}", "Llave de cierre", "Mayús + tecla ] (junto a Enter)", "Mayús + ]"),
        new Symbol("[", "Corchete de apertura", "Tecla [ (junto a Ñ)", "Tecla ["),
        new Symbol("]", "Corchete de cierre", "Tecla ] (junto a Enter)", "Tecla ]"),
        new Symbol("(", "Paréntesis de apertura", "Mayús + 8", "Mayús + 9"),
        new Symbol(")", "Paréntesis de cierre", "Mayús + 9", "Mayús + 0"),
        new Symbol("<", "Menor que", "Tecla a la izquierda de Z", "Mayús + ,"),
        new Symbol(">", "Mayor que", "Mayús + tecla a la izquierda de Z", "Mayús + ."),
        new Symbol("@", "Arroba", "AltGr + Q", "Mayús + 2"),
        new Symbol("#", "Almohadilla", "Mayús + 3", "Mayús + 3"),
        new Symbol(";", "Punto y coma", "Mayús + ,", "Tecla ;"),
        new Symbol(":", "Dos puntos", "Mayús + .", "Mayús + ;"),
        new Symbol("\\", "Barra invertida", "AltGr + tecla ' (junto a 0)", "Tecla \\"),
        new Symbol("|", "Barra vertical", "Tecla a la izquierda de 1", "Mayús + \\"),
        new Symbol("/", "Barra inclinada", "Mayús + 7", "Tecla /"),
        new Symbol("_", "Guion bajo", "Mayús + -", "Mayús + -"),
        new Symbol("=", "Igual", "Mayús + 0", "Tecla ="),
        new Symbol("+", "Más", "Mayús + tecla + (junto a P)", "Mayús + ="),
        new Symbol("*", "Asterisco", "Mayús + tecla + (junto a P)", "Mayús + 8"),
        new Symbol("&", "Ampersand", "Mayús + 6", "Mayús + 7"),
        new Symbol("!", "Exclamación", "Mayús + 1", "Mayús + 1"),
        new Symbol("?", "Interrogación", "Mayús + tecla ' (junto a 0)", "Mayús + /"),
        new Symbol("\"", "Comillas dobles", "Mayús + 2", "Mayús + tecla '"),
        new Symbol("'", "Comilla simple", "Tecla ' (junto a 0)", "Tecla '")
    );

    private SymbolCatalog() { }

    public static List<Symbol> all() { return SYMBOLS; }

    public static Symbol find(String glyph) {
        return SYMBOLS.stream()
                .filter(symbol -> symbol.glyph().equals(glyph))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Símbolo desconocido: " + glyph));
    }
}
