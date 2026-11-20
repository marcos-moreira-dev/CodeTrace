// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar/Comprobar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-biblioteca" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: java -cp "$env:TEMP\ct-biblioteca" ReferenciasYCallbacks

import java.util.function.IntUnaryOperator;

/**
 * Cliente para distinguir callback, lambda y referencia a método.
 *
 * <p>Un método puede recibirse como comportamiento mediante una interfaz
 * funcional. No se invoca cuando se pasa como argumento, sino cuando
 * el receptor decide ejecutarlo. {@code ::} adapta una referencia a un
 * método existente; una lambda puede construir comportamiento nuevo.
 *
 * <p>Runnable es otra interfaz funcional conocida: describe una operación
 * sin argumentos ni resultado. No es equivalente a llamar a la operación.
 */
public class ReferenciasYCallbacks {
    static int duplicar(int n) { return n * 2; }

    static int aplicar(int valor, IntUnaryOperator operacion) {
        return operacion.applyAsInt(valor);
    }

    public static void main(String[] args) {
        System.out.println(aplicar(7, ReferenciasYCallbacks::duplicar));
        System.out.println(aplicar(7, n -> n + 3));
        Runnable aviso = () -> System.out.println("Operación finalizada");
        aviso.run();
    }
}
