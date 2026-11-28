// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.1.3; ejemplo 8.1.3-2
// Compilar: javac --release 21 -encoding UTF-8 -d . ContextoEstaticoInvalido.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

public class ContextoEstaticoInvalido {
    int valor = 4;
    static void metodo() {
        int local = 2;
        class Interna {
            int a = valor; // No existe una instancia exterior.
            int b = local; // Esta captura sí es válida.
        }
    }
}

// Salida esperada:
// Error: non-static variable valor cannot be referenced from a static context
