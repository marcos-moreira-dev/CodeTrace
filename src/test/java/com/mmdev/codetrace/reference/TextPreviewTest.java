package com.mmdev.codetrace.reference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class TextPreviewTest {
    @TempDir Path folder;

    @Test
    void supportsUtf8WithoutChangingTheOriginal() throws Exception {
        Path file = folder.resolve("A.java");
        Files.writeString(file, "Niño\n");
        assertTrue(TextPreview.load(file).readable());
        assertEquals("Niño\n", TextPreview.load(file).content());
        assertEquals("Niño\n", Files.readString(file));
    }

    @Test
    void excludesBinaryBytes() throws Exception {
        Path file = folder.resolve("image.bin");
        Files.write(file, new byte[] {0, 1, 2});
        assertFalse(TextPreview.load(file).readable());
    }
}
