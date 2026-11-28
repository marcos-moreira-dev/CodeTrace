// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . Archivo.java Carpeta.java Elemento.java MainComposite.java
// Ejecutar: java codetrace.practica.patrones.composite.MainComposite
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Árbol de archivos de mapas y recursos del videojuego.
// Patrón composite: Tratar hojas y contenedores con una misma interfaz.
// Glosario: Mapa: escenario virtual; recurso: archivo usado por el juego.
// Este archivo define exactamente un tipo reutilizable, sin main.

package codetrace.practica.patrones.composite;

/** Ejemplo de consola: ejecutar codetrace.practica.patrones.composite.MainComposite; salida esperada: Bytes: 220. */

/** Hoja inmutable; su peso se obtiene del componente del record. */
public record Archivo(String nombre, int bytes) implements Elemento {
    public Archivo {
        if (bytes < 0) throw new IllegalArgumentException("bytes");
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . Archivo.java Carpeta.java Elemento.java MainComposite.java
 *   java codetrace.practica.patrones.composite.MainComposite
 * Nota: Archivo.java es un componente; la salida corresponde al cliente MainComposite.java.
 * Salida esperada:
 *   Bytes: 220
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
