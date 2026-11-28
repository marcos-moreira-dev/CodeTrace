// Tema:
// Un patrón record puede inferir los argumentos genéricos a partir del tipo de la expresión
// examinada. Los componentes extraídos reciben tipos adecuados sin declararlos explícitamente.
// Compilar: javac --release 21 -encoding UTF-8 -d . Transformador.java InferenciaRecord.java
// Ejecutar: java InferenciaRecord
// Comandos desde la carpeta de la unidad.

import java.util.function.UnaryOperator;
public class InferenciaRecord {
    static void comprobar(UnaryOperator<? extends CharSequence> operador) {
        if (operador instanceof Transformador(var origen, var destino)) {
            System.out.println(destino.length() < origen.length());
        }
    }
    public static void main(String[] args) {
        comprobar(new Transformador<>("largo", "corto"));
        comprobar(new Transformador<>("palabra", "sol"));
    }
}

// Salida esperada:
// false
// true
