package com.mmdev.codetrace.symbols;

import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import org.junit.jupiter.api.Test;

class SymbolCatalogTest {
    @Test
    void symbolsAreUniqueAndEachLayoutHasAKeyboardHint() {
        var seen = new HashSet<String>();
        for (var symbol : SymbolCatalog.all()) {
            assertTrue(seen.add(symbol.glyph()));
            for (var layout : SymbolCatalog.Layout.values()) {
                assertFalse(symbol.keys(layout).isBlank());
            }
        }
    }

    @Test
    void latinAmericanBraceAndAtSignShortcuts() {
        assertTrue(SymbolCatalog.find("{").keys(SymbolCatalog.Layout.LATAM).contains("Mayús"));
        assertEquals("AltGr + Q",
                SymbolCatalog.find("@").keys(SymbolCatalog.Layout.LATAM));
    }

    @Test
    void userCanSwitchToUsShortcuts() {
        assertEquals("Mayús + 2", SymbolCatalog.find("@").keys(SymbolCatalog.Layout.US));
    }
}
