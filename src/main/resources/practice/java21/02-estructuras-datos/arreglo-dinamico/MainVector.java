// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . MainVector.java VectorEnteros.java
// Ejecutar: java codetrace.practica.vector.MainVector

package codetrace.practica.vector;

/**
 * Cliente para distinguir longitud lógica y capacidad de un vector.
 *
 * <p>Se agregan seis valores a un almacenamiento que inicia con
 * capacidad dos. La estrategia geométrica amortiza las copias. El
 * cliente demuestra la API pública sin tocar el arreglo interno.
 *
 * <p>Roles: VectorEnteros administra memoria y valida índices;
 * MainVector prepara un caso, ejecuta operaciones y presenta resultados.
 */
public final class MainVector {
    public static void main(String[] args) {
        VectorEnteros vector = new VectorEnteros();
        for (int i = 1; i <= 6; i++) vector.agregar(i * 10);
        System.out.println("Elementos: " + vector.tamaño());
        System.out.println("Capacidad: " + vector.capacidad());
        System.out.println("Tercero: " + vector.obtener(2));
    }
}

/*
 * Salida esperada de consola:
 *   Elementos: 6
 *   Capacidad: 8
 *   Tercero: 30
 * La capacidad crece geométricamente 2 -> 4 -> 8, sin cambiar el tamaño lógico.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . MainVector.java VectorEnteros.java
 *   java codetrace.practica.vector.MainVector
 * Salida esperada:
 *   Elementos: 6
 *   Capacidad: 8
 *   Tercero: 30
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
