// Tema:
// Una sobrescritura no puede añadir una excepción checked más amplia que las permitidas por el
// método sobrescrito. El cliente conserva las garantías de su contrato original.
// Compilar: javac --release 21 -encoding UTF-8 -d . Trabajo.java TrabajoInvalido.java
// ERROR INTENCIONAL de compilación. No ejecutar.
// Comandos desde la carpeta de la unidad.

class TrabajoInvalido extends Trabajo {
    @Override void realizar() throws java.io.IOException {}
}

// Salida esperada:
// Error: overridden method does not throw
