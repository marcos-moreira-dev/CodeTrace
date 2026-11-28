// Tema:
// Un patrón record puede inferir los argumentos genéricos a partir del tipo de la expresión
// examinada. Los componentes extraídos reciben tipos adecuados sin declararlos explícitamente.
// Compilar: javac --release 21 -encoding UTF-8 -d . Transformador.java InferenciaRecord.java
// Ejecutar: java InferenciaRecord
// Comandos desde la carpeta de la unidad.

import java.util.function.UnaryOperator;
record Transformador<T>(T origen, T destino) implements UnaryOperator<T> {
    public T apply(T argumento) {
        return origen.equals(argumento) ? destino : null;
    }
}

// Salida esperada:
// false
// true
