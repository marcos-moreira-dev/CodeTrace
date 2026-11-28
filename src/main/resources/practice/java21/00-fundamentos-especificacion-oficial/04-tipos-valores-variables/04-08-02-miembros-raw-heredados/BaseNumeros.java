// Tema:
// Los miembros de instancia declarados en una clase genérica sufren borrado al usarla como
// raw. Los miembros heredados de una superclase no genérica y los miembros static conservan
// sus tipos.
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
