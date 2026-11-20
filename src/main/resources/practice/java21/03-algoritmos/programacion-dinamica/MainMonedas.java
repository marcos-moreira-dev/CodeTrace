// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar/Comprobar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-biblioteca" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: java -cp "$env:TEMP\ct-biblioteca" codetrace.practica.monedas.MainMonedas

package codetrace.practica.monedas;

/**
 * Cliente: cuando una elección local no garantiza el mejor resultado.
 *
 * <p>Con denominaciones 1, 3 y 4 para formar 6, tomar la moneda más grande
 * primero selecciona 4+1+1 (tres monedas). Programación dinámica calcula
 * el mínimo de dos monedas, 3+3, mediante estados que reutilizan respuestas
 * para montos anteriores.
 *
 * <p>Este caso permite comparar un algoritmo voraz, O(k log k) para ordenar
 * k denominaciones, frente a DP O(m*k) tiempo y O(m) memoria para monto m.
 * El cliente solo prepara datos y muestra el contraste: no realiza DP.
 */
public final class MainMonedas {
    private MainMonedas() {}

    public static void main(String[] args) {
        int[] monedas = {1, 3, 4};
        int monto = 6;
        System.out.println("Voraz: " + Monedas.voraz(monedas, monto));
        System.out.println("Óptimo DP: " + Monedas.dinamica(monedas, monto));
    }
}
