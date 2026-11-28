// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java -File | Where-Object { $_.FullName -notmatch '[\\/]00-fundamentos-especificacion-oficial[\\/]' }).FullName
// Ejecutar: java -cp "$env:TEMP\ct-java21" ReferenciasYCallbacks
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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java -File | Where-Object { $_.FullName -notmatch '[\\/]00-fundamentos-especificacion-oficial[\\/]' }).FullName
 *   java -cp $out ReferenciasYCallbacks
 * Salida esperada:
 *   14
 *   10
 *   Operación finalizada
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
