package com.mmdev.codetrace.reference;

import static org.junit.jupiter.api.Assertions.*;
import com.mmdev.codetrace.practice.TypingSession;
import com.mmdev.codetrace.workspace.VirtualWorkspace;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ReferenceCatalogueTest {
    @TempDir Path folder;

    @Test
    void indexesTextAndBinaryWithoutCountingBuildArtifacts() throws Exception {
        Files.createDirectory(folder.resolve("src"));
        Files.writeString(folder.resolve("src/Main.java"), "í\n");
        Files.write(folder.resolve("logo.bin"), new byte[]{0, 1, 2});
        Files.createDirectory(folder.resolve("target"));
        Files.writeString(folder.resolve("target/Generated.java"), "abc");

        var catalogue = ReferenceCatalogue.scan(folder);
        assertEquals(2, catalogue.files().size());
        assertEquals(2, catalogue.files().get("src/Main.java").typeableUnits());
        assertFalse(catalogue.files().get("logo.bin").typeable());
        assertEquals(2, catalogue.expectedUnits());

        VirtualWorkspace virtual = new VirtualWorkspace();
        virtual.createDirectory("src");
        virtual.createFile("src/Main.java");
        TypingSession session = new TypingSession("í\n");
        session.type("i\u0301\n");
        String sha = catalogue.files().get("src/Main.java").sha256();
        assertEquals(2, catalogue.resolvedUnits(virtual, Map.of("src/Main.java", session),
                Map.of("src/Main.java", sha)));
        assertEquals(0, catalogue.resolvedUnits(virtual, Map.of("src/Main.java", session),
                Map.of("src/Main.java", "f".repeat(64))));
    }

    @Test
    void emptyTextFileCompletesWhenCreated() throws Exception {
        Files.writeString(folder.resolve("empty.txt"), "");
        var index = ReferenceCatalogue.scan(folder);
        assertEquals(1, index.expectedUnits());
        VirtualWorkspace virtual = new VirtualWorkspace();
        virtual.createFile("empty.txt");
        assertEquals(1, index.resolvedUnits(virtual, Map.of(), Map.of()));
    }
}
