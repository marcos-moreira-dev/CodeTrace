// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §8.4.8.3; ejemplo 8.4.8.3-3
// Compilar: javac --release 21 -encoding UTF-8 -d . Trabajo.java TrabajoInvalido.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

class TrabajoInvalido extends Trabajo {
    @Override void realizar() throws java.io.IOException {}
}

// Salida esperada:
// Error: overridden method does not throw
