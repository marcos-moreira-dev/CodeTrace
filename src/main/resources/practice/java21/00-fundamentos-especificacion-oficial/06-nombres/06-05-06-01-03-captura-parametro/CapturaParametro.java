// Tema:
// Una clase local puede capturar un parámetro efectivamente final en su contexto de instancia.
// Un contexto static de esa clase no dispone de la misma instancia de captura.
// Compilar: javac --release 21 -encoding UTF-8 -d . CapturaParametro.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

public class CapturaParametro {
    static void comprobar(String[] valores) {
        class Local {
            String leer() { return valores[0]; }
            static String leerEstatico() { return valores[0]; }
        }
    }
}

// Salida esperada:
// Error: non-static variable valores cannot be referenced from a static context
