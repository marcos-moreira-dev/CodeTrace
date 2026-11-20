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
        assertEquals(1, catalogue.files().get("src/Main.java").typeableUnits());
        assertFalse(catalogue.files().get("logo.bin").typeable());
        assertEquals(1, catalogue.expectedUnits());

        VirtualWorkspace virtual = new VirtualWorkspace();
        virtual.createDirectory("src");
        virtual.createFile("src/Main.java");
        TypingSession session = new TypingSession("í\n");
        session.type("i\u0301\n");
        String sha = catalogue.files().get("src/Main.java").sha256();
        assertEquals(1, catalogue.resolvedUnits(virtual, Map.of("src/Main.java", session),
                Map.of("src/Main.java", sha)));
        assertEquals(0, catalogue.resolvedUnits(virtual, Map.of("src/Main.java", session),
                Map.of("src/Main.java", "f".repeat(64))));
    }

    @Test
    void mediaAndOversizedSourcesDoNotGetContentFingerprints() throws Exception {
        byte[] media = new byte[4096];
        java.util.Arrays.fill(media, (byte) 23);
        Files.write(folder.resolve("song.mp3"), media);
        Files.write(folder.resolve("movie.mkv"), new byte[8192]);
        Files.write(folder.resolve("TooLarge.java"), new byte[2 * 1024 * 1024 + 1]);
        Files.write(folder.resolve("Invalid.java"), new byte[] { (byte) 0xFF });
        Files.writeString(folder.resolve("Valid.java"), "class Valid { }");

        var catalogue = ReferenceCatalogue.scan(folder);
        for (String excluded : java.util.List.of(
                "song.mp3", "movie.mkv", "TooLarge.java", "Invalid.java")) {
            assertFalse(catalogue.files().get(excluded).typeable(), excluded);
            assertEquals("", catalogue.files().get(excluded).sha256(), excluded);
        }
        assertEquals(ReferenceFingerprint.sha256(folder.resolve("Valid.java")),
                catalogue.files().get("Valid.java").sha256());
        assertEquals(15, catalogue.files().get("Valid.java").typeableUnits());
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
    void exclusionsRemoveWholeSubtreesFromAllProgressMetrics() throws Exception {
        Files.createDirectories(folder.resolve("src/generated"));
        Files.writeString(folder.resolve("src/Main.java"), "ab");
        Files.writeString(folder.resolve("src/generated/Generated.java"), "cdef");
        var index = ReferenceCatalogue.scan(folder);
        var ex = ProgressExclusions.empty().withFolder("src/generated", true);
        assertEquals(6, index.expectedUnits(ProgressScope.defaults(), "src"));
        assertEquals(2, index.expectedUnits(ProgressScope.defaults(), "src", ex));
        assertEquals(1, index.includedFileCount(ProgressScope.defaults(), "src", ex));
        assertEquals(0, index.expectedUnits(
                ProgressScope.defaults(), "src/generated", ex));
        var virtual = new VirtualWorkspace();
        virtual.ensureFile("src/Main.java");
        var typed = new TypingSession("ab");
        typed.type("ab");
        assertEquals(2, index.resolvedUnits(virtual,
                Map.of("src/Main.java", typed),
                Map.of("src/Main.java", index.files().get("src/Main.java").sha256()),
                ProgressScope.defaults(), "src", ex));
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

    @org.junit.jupiter.api.Test
    void interruptedCatalogueScanDoesNotPublishPartialCounts() throws Exception {
        Thread.currentThread().interrupt();
        try {
            org.junit.jupiter.api.Assertions.assertThrows(
                    java.util.concurrent.CancellationException.class,
                    () -> ReferenceCatalogue.scan(folder));
        } finally {
            Thread.interrupted();
        }
    }

}
