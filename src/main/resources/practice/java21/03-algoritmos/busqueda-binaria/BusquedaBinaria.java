// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 BusquedaBinaria.java MainBusquedaBinaria.java
// Ejecutar: java MainBusquedaBinaria

public final class BusquedaBinaria {
    public static int buscar(int[] ordenados, int objetivo) {
        int izquierda = 0;
        int derecha = ordenados.length - 1;
        while (izquierda <= derecha) {
            int medio = izquierda + (derecha - izquierda) / 2;
            if (ordenados[medio] == objetivo) return medio;
            if (ordenados[medio] < objetivo) izquierda = medio + 1;
            else derecha = medio - 1;
        }
        return -1;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 BusquedaBinaria.java MainBusquedaBinaria.java
 *   java MainBusquedaBinaria
 * Nota: BusquedaBinaria.java es un componente; la salida corresponde al cliente MainBusquedaBinaria.java.
 * Salida esperada:
 *   Índice: 4; inexistente: -1
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
