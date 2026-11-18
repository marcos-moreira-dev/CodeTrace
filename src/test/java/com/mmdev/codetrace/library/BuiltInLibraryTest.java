package com.mmdev.codetrace.library;

import static org.junit.jupiter.api.Assertions.*;
import com.mmdev.codetrace.languages.LanguageRegistry;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.tools.ToolProvider;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class BuiltInLibraryTest {
    @TempDir Path temp;

    @Test
    void allPacksAreBundledOfflineAndHaveRealCodeExtensions() throws Exception {
        var library = new BuiltInLibrary(temp.resolve("offline"));
        assertEquals(12, library.packs().size());
        assertEquals(12, library.packs().stream().map(BuiltInLibrary.Pack::id)
                .collect(Collectors.toSet()).size());
        for (var pack : library.packs()) {
            assertTrue(library.size(pack) >= 2, pack.name());
            Path root = library.open(pack);
            for (String relative : library.entries(pack)) {
                Path file = root.resolve(relative);
                assertTrue(Files.isRegularFile(file), relative);
                assertFalse(Files.readString(file).isBlank(), relative);
                assertNotEquals("Texto", LanguageRegistry.forFile(relative).name(),
                        "Unsupported syntax adapter for " + relative);
            }
        }
    }

    @Test
    void bundledJava21SourcesCompileWithTheActualJdk() throws Exception {
        var compiler = ToolProvider.getSystemJavaCompiler();
        assertNotNull(compiler, "Java development kit required");
        var library = new BuiltInLibrary(temp.resolve("reference"));
        var pack = library.packs().getFirst();
        Path source = library.open(pack);
        Path compiled = Files.createDirectory(temp.resolve("compiled"));
        List<String> arguments = new ArrayList<>(
                List.of("--release", "21", "-encoding", "UTF-8",
                        "-d", compiled.toString()));
        for (String path : library.entries(pack)) {
            arguments.add(source.resolve(path).toString());
        }
        assertEquals(0, compiler.run(null, null, null,
                arguments.toArray(String[]::new)),
                "Java 21 reference examples must compile together");
    }

    @Test
    void restoresInternalReferenceWithoutTouchingOtherProjects() throws Exception {
        var library = new BuiltInLibrary(temp.resolve("cache"));
        var javaPack = library.packs().getFirst();
        Path folder = library.open(javaPack);
        Path file = folder.resolve(library.entries(javaPack).getFirst());
        String original = Files.readString(file);
        Files.writeString(file, "changed");
        library.open(javaPack);
        assertEquals(original, Files.readString(file));
        assertFalse(Files.exists(temp.resolve("external-project")));
    }

    @Test
    void deeperCategoriesExistForJavaAndTypeScript() throws Exception {
        var library = new BuiltInLibrary(temp.resolve("cache"));
        assertEquals(16, library.size(library.packs().get(0)));
        assertEquals(16, library.size(library.packs().get(1)));
        for (var pack : library.packs().subList(0, 2)) {
            var names = library.entries(pack);
            assertTrue(names.stream().anyMatch(p -> p.toLowerCase().contains("euclides")));
            assertTrue(names.stream().anyMatch(p -> p.toLowerCase().contains("callback")));
            assertTrue(names.stream().anyMatch(p -> p.toLowerCase().contains("observador")));
            assertTrue(names.stream().anyMatch(p -> p.toLowerCase().contains("cola_circular") || p.contains("ColaCircular")));
            assertTrue(names.stream().anyMatch(p -> p.contains("estructuras")));
            assertTrue(names.stream().anyMatch(p -> p.contains("algoritmos")));
            assertTrue(names.stream().anyMatch(p -> p.contains("patrones")));
        }
    }
}
