// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §18.5.5; ejemplo 18.5.5-1
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
