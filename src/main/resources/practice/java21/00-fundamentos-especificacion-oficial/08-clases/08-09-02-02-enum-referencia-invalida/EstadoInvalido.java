// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.9.2; ejemplo 8.9.2-2
// Compilar: javac --release 21 -encoding UTF-8 -d . EstadoInvalido.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

import java.util.HashMap;
import java.util.Map;
enum EstadoInvalido {
    ABIERTO, CERRADO;
    static final Map<String, EstadoInvalido> mapa = new HashMap<>();
    EstadoInvalido() { mapa.put(name(), this); }
}

// Salida esperada:
// Error: illegal reference to static field from initializer
