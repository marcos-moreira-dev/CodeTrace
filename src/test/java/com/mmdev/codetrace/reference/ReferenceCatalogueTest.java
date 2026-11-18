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
    void onlySourceFilesCountUnlessOtherCategoriesAreEnabled() throws Exception {
        Files.writeString(folder.resolve("App.java"), "abc");
        Files.writeString(folder.resolve("README.md"), "mucho texto");
        Files.writeString(folder.resolve("pom.xml"), "<project/>");
        var index = ReferenceCatalogue.scan(folder);
        var codeOnly = ProgressScope.defaults();
        assertEquals(3, index.expectedUnits(codeOnly));
        assertEquals(1, index.includedFileCount(codeOnly));
        var codeAndConfig = codeOnly.with(ProgressScope.Category.CONFIGURATION, true);
        assertEquals(3, index.expectedUnits(codeAndConfig));
        assertEquals(1, index.includedFileCount(codeAndConfig));
        assertEquals(2, index.includedFileCount(
                new ProgressScope(true, true, true, false)));
    }

    @Test
    void scopedProgressCountsOnlyCorrectlyTypedCharactersInSelectedFolder()
            throws Exception {
        Files.createDirectory(folder.resolve("src"));
        Files.createDirectory(folder.resolve("tests"));
        Files.writeString(folder.resolve("src/A.java"), "abcd");
        Files.writeString(folder.resolve("tests/B.java"), "123456");
        var index = ReferenceCatalogue.scan(folder);
        var scope = ProgressScope.defaults();
        assertEquals(4, index.expectedUnits(scope, "src"));
        assertEquals(6, index.expectedUnits(scope, "tests"));
        assertEquals(10, index.expectedUnits(scope, ""));
        assertEquals(1, index.includedFileCount(scope, "src"));

        var virtual = new VirtualWorkspace();
        virtual.ensureFile("src/A.java");
        var typing = new TypingSession("abcd");
        typing.type("abXd");
        var hashes = Map.of("src/A.java", index.files().get("src/A.java").sha256());
        assertEquals(3, index.resolvedUnits(virtual,
                Map.of("src/A.java", typing), hashes, scope, "src"));
        assertEquals(0, index.resolvedUnits(virtual,
                Map.of("src/A.java", typing), hashes, scope, "tests"));
    }

    @Test
    void emptyTextFileCompletesWhenCreated() throws Exception {
        Files.writeString(folder.resolve("empty.txt"), "");
        var index = ReferenceCatalogue.scan(folder);
        assertEquals(0, index.expectedUnits());
        VirtualWorkspace virtual = new VirtualWorkspace();
        virtual.createFile("empty.txt");
        assertEquals(0, index.resolvedUnits(virtual, Map.of(), Map.of()));
    }
}
