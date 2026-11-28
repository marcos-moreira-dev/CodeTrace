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
        assertEquals(14, library.packs().size());
        assertEquals(14, library.packs().stream().map(BuiltInLibrary.Pack::id)
                .collect(Collectors.toSet()).size());
        for (var pack : library.packs()) {
            assertTrue(library.size(pack) >= (java.util.Set.of("java21", "kotlin", "typescript")
                    .contains(pack.id()) ? 16 : 6), pack.name());
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
            if (path.matches("0[1-5]-.*")) {
                arguments.add(source.resolve(path).toString());
            }
        }
        assertEquals(0, compiler.run(null, null, null,
                arguments.toArray(String[]::new)),
                "Existing Java 21 reference examples must compile together");
    }

    @Test
    void javaCatalogRenamesOnlyTheExistingFundamentalsAndKeepsOldCache() throws Exception {
        Path cache = temp.resolve("practice-library");
        Path previous = cache.resolve("v15/java21/01-fundamentos");
        Files.createDirectories(previous);
        Path prior = previous.resolve("prior.java");
        Files.writeString(prior, "prior session reference");
        var library = new BuiltInLibrary(cache.resolve(BuiltInLibrary.CATALOG_VERSION));
        var javaPack = library.packs().getFirst();
        assertEquals(28, library.entries(javaPack).stream()
                .filter(p -> p.startsWith("01-fundamentos-en-accion/")).count());
        assertFalse(library.entries(javaPack).stream().anyMatch(p -> p.startsWith("01-fundamentos/")));
        assertTrue(Files.isDirectory(library.open(javaPack).resolve("01-fundamentos-en-accion")));
        assertEquals("prior session reference", Files.readString(prior));
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
        var extended = library.packs().stream()
                .filter(pack -> java.util.Set.of("java21", "typescript", "kotlin")
                        .contains(pack.id())).toList();
        assertEquals(3, extended.size());
        for (var pack : extended) {
            assertTrue(library.size(pack) >= 16);
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

    @Test
    void everyExerciseDocumentsItsVersionAndCompilingOrCheckingCommand()
            throws Exception {
        var library = new BuiltInLibrary(temp.resolve("versions"));
        for (var pack : library.packs()) {
            Path root = library.open(pack);
            for (String relative : library.entries(pack)) {
                String text = Files.readString(root.resolve(relative));
                if (pack.id().equals("java21") && relative.equals("14-comunicacion-con-dispositivos/12-interoperabilidad-nativa/README.md")) {
                    assertTrue(text.startsWith("# Interoperabilidad nativa"), relative);
                    continue;
                }
                if (pack.id().equals("java21")
                        && (relative.startsWith("00-fundamentos-especificacion-oficial/")
                        || relative.matches("(0[6-9]|1[0-9]|20)-.*"))) {
                    assertTrue(text.startsWith("// Tema:\n"), relative);
                    assertTrue(text.contains("Compilar:") && text.contains("Salida esperada:"), relative);
                    assertFalse(text.contains("Referencia: Java SE 21") || text.contains("Tema JLS:"), relative);
                    continue;
                }
                String firstLines = text.lines().limit(10)
                        .collect(Collectors.joining("\n")).toLowerCase(java.util.Locale.ROOT);
                assertTrue(firstLines.contains("codetrace"), pack.id() + "/" + relative);
                assertTrue(firstLines.contains("referencia:") || firstLines.contains("kotlin 2.2"),
                        "Missing version in " + pack.id() + "/" + relative);
                assertTrue(firstLines.contains("compilar:")
                        || firstLines.contains("comprobar")
                        || firstLines.contains("ejecutar:")
                        || firstLines.contains("tipos/sintaxis: tsc "),
                        "Missing CLI command in " + pack.id() + "/" + relative);
            }
        }
    }
    @Test
    void allFourCurriculumPurposesAndDocumentedJavaClientsArePresent() throws Exception {
        var library = new BuiltInLibrary(temp.resolve("curriculum"));
        assertEquals("v33", BuiltInLibrary.CATALOG_VERSION);
        assertEquals(2012, library.packs().stream().mapToInt(library::size).sum());
        for (var pack : library.packs()) {
            var entries = library.entries(pack);
            assertTrue(entries.stream().allMatch(x -> x.split("/").length >= 3
                    || x.equals(java.util.Map.of(
                        "java21", "05-patrones-diseno-counter-strike/MainCounterStrike.java",
                        "python", "05-patrones-diseno-counter-strike/main.py",
                        "csharp", "05-patrones-diseno-counter-strike/MainCounterStrike.cs",
                        "cpp", "05-patrones-diseno-counter-strike/main.cpp",
                        "typescript", "05-patrones-diseno-counter-strike/MainCounterStrike.ts"
                    ).get(pack.id()))),
                    "Every reference is in an exercise directory except the documented integrator: "
                            + pack.id());
            assertTrue(entries.stream().anyMatch(x -> x.startsWith(pack.id().equals("java21") ? "01-fundamentos-en-accion/" : "01-fundamentos/")), pack.id());
            assertTrue(entries.stream().anyMatch(x -> x.startsWith("03-algoritmos/")), pack.id());
            assertTrue(entries.stream().noneMatch(x -> x.contains("07-proyectos/")
                    || x.contains("laboratorios/") || x.contains("/complejidad/")), pack.id());
        }
        var javaPack = library.packs().stream().filter(x -> x.id().equals("java21")).findFirst().orElseThrow();
        assertTrue(library.entries(javaPack).contains("01-fundamentos-en-accion/comparadores/ComparadoresEncadenados.java"));
        assertTrue(library.entries(javaPack).contains("03-algoritmos/programacion-dinamica/cambio-monedas/MainMonedas.java"));
        assertTrue(library.entries(javaPack).contains("04-patrones-diseno/strategy-tarifas/MainTarifas.java"));
        for (var expected : java.util.Map.of("python", 33L, "csharp", 18L, "cpp", 35L, "typescript", 33L).entrySet()) {
            var pack = library.packs().stream().filter(p -> p.id().equals(expected.getKey()))
                    .findFirst().orElseThrow();
            assertEquals(expected.getValue().longValue(), library.entries(pack).stream()
                    .filter(p -> p.startsWith("05-patrones-diseno-counter-strike/")).count(),
                    "Counter-Strike track: " + pack.id());
        }
        assertEquals(79, library.entries(javaPack).stream()
                .filter(path -> path.startsWith("05-patrones-diseno-counter-strike/"))
                .count(), "Separate Counter-Strike track preserves the original 04 lessons");
        assertEquals(14, library.entries(javaPack).stream()
                .filter(path -> path.startsWith("05-patrones-diseno-counter-strike/")
                        && path.substring(path.lastIndexOf('/') + 1).startsWith("Main"))
                .count(), "One runnable client for each Counter-Strike pattern");
        assertTrue(library.entries(javaPack).contains(
                "05-patrones-diseno-counter-strike/MainCounterStrike.java"),
                "The MVC integration client lives at the Counter-Strike track root");
        assertTrue(library.entries(javaPack).contains("02-estructuras-datos/arbol-bst/MainArbol.java"));
        assertTrue(library.entries(javaPack).contains("02-estructuras-datos/tabla-hash/MainHash.java"));
        assertTrue(library.entries(javaPack).contains("03-algoritmos/backtracking/CuatroReinas.java"));
        assertTrue(library.entries(javaPack).contains("03-algoritmos/programacion-dinamica/mochila-01/Mochila01.java"));
        assertTrue(library.entries(javaPack).contains("04-patrones-diseno/adapter/MainAdapter.java"));
        assertTrue(library.entries(javaPack).contains("04-patrones-diseno/decorator/MainDecorator.java"));
        assertTrue(library.entries(javaPack).contains("02-estructuras-datos/grafos/tipos/MainTiposGrafo.java"));
        assertTrue(library.entries(javaPack).contains("02-estructuras-datos/grafos/representaciones/MainRepresentaciones.java"));
        assertTrue(library.entries(javaPack).contains("03-algoritmos/grafos/caminos-minimos/MainCaminosMinimos.java"));
        assertTrue(library.entries(javaPack).contains("03-algoritmos/grafos/expansion-minima/MainExpansionMinima.java"));
        assertTrue(library.entries(javaPack).contains("03-algoritmos/grafos/topologico/MainTopologico.java"));
        assertTrue(library.entries(javaPack).contains("03-algoritmos/grafos/recorridos/caminos-sin-peso/MainCaminosSinPeso.java"));
        assertTrue(library.entries(javaPack).contains("04-patrones-diseno/factory/basico/MainFactoryBasica.java"));
        assertTrue(library.entries(javaPack).contains("04-patrones-diseno/observer/basico/MainObservadorBasico.java"));
        assertTrue(library.entries(javaPack).contains("01-fundamentos-en-accion/excepciones/checked-personalizada/MainExcepcionChecked.java"));
        assertTrue(library.entries(javaPack).contains("01-fundamentos-en-accion/excepciones/unchecked-dominio/MainExcepcionUnchecked.java"));
        assertTrue(library.entries(javaPack).contains("02-estructuras-datos/cola-circular/MainColaCircular.java"));
        assertTrue(library.entries(javaPack).contains("04-patrones-diseno/strategy/basico/MainStrategy.java"));

        for (String idioma : java.util.List.of("java21", "kotlin", "csharp", "typescript",
                "python", "cpp", "ruby", "javascript")) {
            var paquete = library.packs().stream()
                    .filter(x -> x.id().equals(idioma)).findFirst().orElseThrow();
            var rutas = library.entries(paquete);
            for (String familia : java.util.List.of("linked-list/simple/", "linked-list/doble/",
                    "linked-list/circular/", "linked-list/doble-circular/", "arbol-avl/",
                    "union-find/", "trie/", "heap-maximo/", "deque/")) {
                assertTrue(rutas.stream().anyMatch(x -> x.startsWith("02-estructuras-datos/" + familia)),
                        idioma + " no contiene " + familia);
            }
            for (String patron : java.util.List.of("builder/", "abstract-factory/", "composite/",
                    "command/", "template-method/")) {
                assertTrue(rutas.stream().anyMatch(x -> x.startsWith("04-patrones-diseno/" + patron)),
                        idioma + " no contiene patrón " + patron);
            }
        }
        assertTrue(library.entries(javaPack).contains(
                "02-estructuras-datos/linked-list/doble/MainListaDoble.java"));
        assertTrue(library.entries(javaPack).contains(
                "04-patrones-diseno/command/deshacer/MainCommand.java"));
        for (String subtema : java.util.List.of(
                "ordenamientos/burbuja-optimizada/MainBurbuja.java",
                "ordenamientos/insercion-binaria/MainInsercionBinaria.java",
                "busqueda-rangos/MainLimitesOrdenados.java",
                "ordenamientos/MainOrdenamientos.java")) {
            assertTrue(library.entries(javaPack).contains("03-algoritmos/" + subtema));
        }
        for (String lenguaje : java.util.List.of("kotlin", "csharp", "typescript",
                "python", "cpp", "ruby", "javascript")) {
            var paquete = library.packs().stream().filter(x -> x.id().equals(lenguaje))
                    .findFirst().orElseThrow();
            var rutas = library.entries(paquete);
            for (String unidad : java.util.List.of(
                    "ordenamientos/comparativa-cinco/", "ordenamientos/insercion-binaria/",
                    "busqueda-rangos/")) {
                assertTrue(rutas.stream().anyMatch(x -> x.startsWith("03-algoritmos/" + unidad)),
                        lenguaje + " no contiene " + unidad);
            }
        }
        var cppPack = library.packs().stream().filter(x -> x.id().equals("cpp")).findFirst().orElseThrow();
        assertTrue(library.entries(cppPack).contains("01-fundamentos/compilacion-y-enlazado/biblioteca-estatica-dinamica/main.cpp"));
        assertTrue(library.entries(javaPack).contains("02-estructuras-datos/grafos/genericos/MainGrafoGenerico.java"));
        assertTrue(library.entries(javaPack).contains("03-algoritmos/grafos/componentes-fuertes/MainComponentesFuertes.java"));
        assertTrue(library.entries(javaPack).contains("03-algoritmos/grafos/caminos-minimos/MainCasosLimite.java"));
        var kotlinPack = library.packs().stream().filter(x -> x.id().equals("kotlin")).findFirst().orElseThrow();
        assertTrue(library.entries(kotlinPack).contains("04-patrones-diseno/factory/basico/MainFactory.kt"));
        assertTrue(library.entries(kotlinPack).contains("04-patrones-diseno/observer/basico/MainObservador.kt"));
        var tsPack = library.packs().stream().filter(x -> x.id().equals("typescript")).findFirst().orElseThrow();
        assertTrue(library.entries(tsPack).contains("04-patrones-diseno/observer/basico/MainObserverBasico.ts"));
    }

    @Test
    void multiFileProjectsAndAssemblyAreActuallyBundled() throws Exception {
        var library = new BuiltInLibrary(temp.resolve("architectures"));
        assertEquals(14, library.packs().size());
        var all = library.packs().stream().collect(
                Collectors.toMap(BuiltInLibrary.Pack::id, p -> p));
        assertTrue(library.entries(all.get("java21")).contains(
                "04-patrones-diseno/factory-method/MainFactoryMethod.java"));
        assertTrue(library.entries(all.get("java21")).contains(
                "04-patrones-diseno/chain-of-responsibility/reportes-partida/MainCadenaResponsabilidad.java"));
        assertTrue(library.entries(all.get("python")).contains(
                "04-patrones-diseno/chain-of-responsibility/reportes-partida/main.py"));
        assertTrue(library.entries(all.get("java21")).contains(
                "04-patrones-diseno/observer/CentroEventos.java"));
        assertTrue(library.entries(all.get("typescript")).contains(
                "04-patrones-diseno/observer/BusEventos.ts"));
        assertTrue(library.entries(all.get("python")).contains(
                "04-patrones-diseno/factory-method/creadores.py"));
        assertTrue(library.entries(all.get("c")).contains(
                "04-patrones-diseno/observer/Observer.h"));
        assertTrue(library.entries(all.get("nasm-x64")).contains(
                "03-algoritmos/euclides/euclides.asm"));
        assertTrue(library.entries(all.get("java21")).contains(
                "03-algoritmos/dijkstra/Dijkstra.java"));
        assertEquals(2012, library.packs().stream().mapToInt(library::size).sum());
    }

}
