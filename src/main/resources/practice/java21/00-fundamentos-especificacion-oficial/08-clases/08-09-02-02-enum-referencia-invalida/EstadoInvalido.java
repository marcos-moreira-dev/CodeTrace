// Tema:
// El constructor de un enum no puede acceder directamente a determinados campos static no
// constantes del mismo enum, pues las constantes se crean antes de inicializar esos campos.
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
