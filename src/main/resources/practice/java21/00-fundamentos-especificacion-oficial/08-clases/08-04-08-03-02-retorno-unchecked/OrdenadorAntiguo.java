// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.4.8.3; ejemplo 8.4.8.3-2
// Compilar: javac --release 21 -encoding UTF-8 -d . Ordenador.java OrdenadorAntiguo.java RetornoRaw.java
// Ejecutar: java RetornoRaw
// Comandos desde la carpeta de la unidad.

import java.util.*;
class OrdenadorAntiguo extends Ordenador {
    @Override List lista(Collection c) { return new ArrayList(c); }
}

// Salida esperada:
// [a, b]
