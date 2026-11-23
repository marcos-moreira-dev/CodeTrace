package com.mmdev.codetrace.library;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * A technology-neutral structural audit of the full built-in curriculum.
 *
 * <p>Do not apply Java's one-type-per-file convention to languages that use
 * modules, functions, scripts, prototypes or header/source pairs differently.
 * All 14 language packs are checked for resource integrity; Java sources get
 * an additional informational scan for top-level versus nested named types.
 * This test deliberately does NOT attempt semantic parsing of other languages.</p>
 */
class PracticeCurriculumStructureAuditTest {
    @TempDir Path temp;

    private static final Pattern TYPE = Pattern.compile(
            "\\b(class|interface|record|enum)\\s+([\\p{L}_][\\p{L}\\p{N}_]*)");

    private record Type(String kind, String name, int depth) {}

    @Test
    void auditAllLanguagePacksAndReportJavaTypeOrganization() throws Exception {
        BuiltInLibrary library = new BuiltInLibrary(temp.resolve("practice-audit"));
        assertEquals(14, library.packs().size());

        int resources = 0, withOutput = 0;
        List<String> multipleTopLevel = new ArrayList<>();
        List<String> nestedNamedTypes = new ArrayList<>();
        Map<String, Integer> packCounts = new TreeMap<>();
        for (BuiltInLibrary.Pack pack : library.packs()) {
            Path root = library.open(pack);
            int count = 0;
            for (String relative : library.entries(pack)) {
                Path sourcePath = root.resolve(relative);
                assertTrue(Files.isRegularFile(sourcePath), pack.id() + "/" + relative);
                String source = Files.readString(sourcePath);
                assertFalse(source.isBlank(), pack.id() + "/" + relative);
                String lower = source.toLowerCase(Locale.ROOT);
                if (lower.contains("salida esperada")
                        || lower.contains("output esperado")
                        || lower.contains("resultado esperado")) withOutput++;
                if (pack.id().equals("java21")) {
                    List<Type> types = findNamedJavaTypes(source);
                    long top = types.stream().filter(t -> t.depth() == 0).count();
                    if (top > 1) multipleTopLevel.add(relative + " " + types);
                    List<Type> nested = types.stream().filter(t -> t.depth() > 0).toList();
                    if (!nested.isEmpty())
                        nestedNamedTypes.add(relative + ": " + nested);
                }
                count++;
            }
            resources += count;
            packCounts.put(pack.id(), count);
        }
        assertEquals(library.packs().stream().mapToInt(library::size).sum(), resources);
        // Machine-readable summary in GitHub Actions logs; this is a review
        // diagnostic, not a false claim that every exercise is semantically verified.
        System.out.println("CODETRACE_PRACTICE_AUDIT_BEGIN");
        System.out.println("Resources=" + resources + "; packs=" + packCounts.size()
                + "; filesWithOutputNote=" + withOutput);
        packCounts.forEach((id, count) ->
                System.out.println("PACK " + id + " " + count));
        System.out.println("JAVA multiple-top-level=" + multipleTopLevel.size()
                + "; nested-named=" + nestedNamedTypes.size());
        multipleTopLevel.forEach(line -> System.out.println("JAVA_MULTITOP " + line));
        nestedNamedTypes.forEach(line -> System.out.println("JAVA_NESTED " + line));
        System.out.println("CODETRACE_PRACTICE_AUDIT_END");
    }

    @Test
    void independentJavaTeachingRolesReallyHaveOneNamedTypePerFile() throws Exception {
        BuiltInLibrary library = new BuiltInLibrary(temp.resolve("roles"));
        BuiltInLibrary.Pack java = library.packs().stream()
                .filter(pack -> pack.id().equals("java21")).findFirst().orElseThrow();
        Path root = library.open(java);
        List<String> roles = List.of(
                "04-patrones-diseno/factory/basico/Factory.java",
                "04-patrones-diseno/factory/basico/Mensaje.java",
                "04-patrones-diseno/factory/basico/Correo.java",
                "01-fundamentos/records-y-enums/RecordsYEnums.java",
                "01-fundamentos/records-y-enums/Estado.java",
                "01-fundamentos/records-y-enums/Pedido.java",
                "04-patrones-diseno/strategy/basico/Strategy.java",
                "04-patrones-diseno/strategy/basico/Descuento.java",
                "04-patrones-diseno/observer/basico/Observador.java",
                "04-patrones-diseno/observer/basico/Escucha.java",
                "04-patrones-diseno/command/deshacer/Comando.java",
                "04-patrones-diseno/command/deshacer/AgregarTexto.java",
                "04-patrones-diseno/composite/arbol-archivos/Elemento.java",
                "04-patrones-diseno/composite/arbol-archivos/Archivo.java",
                "04-patrones-diseno/composite/arbol-archivos/Carpeta.java",
                "04-patrones-diseno/abstract-factory/tema-consola/Boton.java",
                "04-patrones-diseno/abstract-factory/tema-consola/Dialogo.java",
                "04-patrones-diseno/abstract-factory/tema-consola/Fabrica.java",
                "04-patrones-diseno/abstract-factory/tema-consola/FabricaClara.java",
                "04-patrones-diseno/abstract-factory/tema-consola/FabricaOscura.java");
        for (String relative : roles) {
            assertTrue(library.entries(java).contains(relative), relative);
            List<Type> declared = findNamedJavaTypes(Files.readString(root.resolve(relative)));
            assertEquals(1, declared.size(),
                    "One independent named type per file: " + relative + " " + declared);
            assertEquals(0, declared.getFirst().depth(), relative);
            String basename = Path.of(relative).getFileName().toString()
                    .replaceFirst("\\.java$", "");
            assertEquals(basename, declared.getFirst().name(), relative);
        }
    }

    /**
     * Mask strings and comments, preserving positions and line breaks so a
     * brace-depth counter distinguishes nested named types from file-level
     * classes. Java text blocks and character literals are also masked.
     * This is a structural heuristic, not a Java compiler replacement.
     */
    private static List<Type> findNamedJavaTypes(String source) {
        StringBuilder masked = new StringBuilder(source);
        int state = 0; // 0 code, 1 line comment, 2 block, 3 string, 4 char, 5 text block
        for (int i = 0; i < masked.length(); i++) {
            char c = source.charAt(i), next = i + 1 < source.length()
                    ? source.charAt(i + 1) : '\0';
            if (state == 0) {
                if (c == '/' && next == '/') { state = 1; masked.setCharAt(i, ' '); continue; }
                if (c == '/' && next == '*') { state = 2; masked.setCharAt(i, ' '); continue; }
                if (c == '"') {
                    state = source.startsWith("\"\"\"", i) ? 5 : 3;
                } else if (c == '\'') state = 4;
                else continue;
            } else if (state == 1) {
                if (c == '\n') { state = 0; continue; }
            } else if (state == 2) {
                if (c == '*' && next == '/') {
                    masked.setCharAt(i, ' ');
                    i++;
                    masked.setCharAt(i, ' ');
                    state = 0;
                    continue;
                }
            } else if (state == 5 && source.startsWith("\"\"\"", i)) {
                for (int j = 0; j < 3 && i < masked.length(); j++, i++)
                    masked.setCharAt(i, ' ');
                i--;
                state = 0;
                continue;
            } else if ((state == 3 && c == '"')
                    || (state == 4 && c == '\'')) {
                state = 0;
            } else if (c == '\\' && (state == 3 || state == 4)
                    && i + 1 < masked.length()) {
                masked.setCharAt(i, ' ');
                i++;
                masked.setCharAt(i, ' ');
                continue;
            }
            if (c != '\n') masked.setCharAt(i, ' ');
        }
        String code = masked.toString();
        Matcher matcher = TYPE.matcher(code);
        int index = 0, depth = 0;
        List<Type> types = new ArrayList<>();
        while (matcher.find()) {
            for (; index < matcher.start(); index++) {
                if (code.charAt(index) == '{') depth++;
                else if (code.charAt(index) == '}') depth--;
            }
            if (depth >= 0)
                types.add(new Type(matcher.group(1), matcher.group(2), depth));
        }
        return types;
    }
}
