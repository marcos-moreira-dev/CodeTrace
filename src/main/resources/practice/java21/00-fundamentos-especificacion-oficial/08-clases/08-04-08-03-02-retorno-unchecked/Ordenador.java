// Tema:
// La interoperabilidad con tipos raw puede permitir una sobrescritura mediante conversión
// unchecked del retorno. Esa advertencia señala una garantía de tipos que el compilador no
// puede demostrar.
// Compilar: javac --release 21 -encoding UTF-8 -d . Ordenador.java OrdenadorAntiguo.java RetornoRaw.java
// Ejecutar: java RetornoRaw
// Comandos desde la carpeta de la unidad.

import java.util.*;
class Ordenador { List<String> lista(Collection<String> c) { return new ArrayList<>(c); } }

// Salida esperada:
// [a, b]
