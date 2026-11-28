// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §4.8; ejemplo 4.8-2
// Compilar: javac --release 21 -encoding UTF-8 -d . BaseNumeros.java MiembrosRaw.java
// Ejecutar: java MiembrosRaw
// Comandos desde la carpeta de la unidad.

import java.util.List;
class BaseNumeros {
    List<Number> numeros() { return List.of(3, 4.5); }
}

// Salida esperada:
// [3, 4.5]
// []
// [uno]
