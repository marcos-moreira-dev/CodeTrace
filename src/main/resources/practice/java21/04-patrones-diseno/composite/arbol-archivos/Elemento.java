// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . Archivo.java Carpeta.java Elemento.java MainComposite.java
// Ejecutar: java codetrace.practica.patrones.composite.MainComposite
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Árbol de archivos de mapas y recursos del videojuego.
// Patrón composite: Tratar hojas y contenedores con una misma interfaz.
// Glosario: Mapa: escenario virtual; recurso: archivo usado por el juego.

package codetrace.practica.patrones.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite representa uniformemente hojas y grupos de objetos.
 * La carpeta agrega archivos/carpetas; el archivo es una hoja.
 * Cada tamaño se calcula de forma recursiva O(n), n nodos.
 * No son archivos reales del disco: modelo puro de consola.
 */
/** Contrato común de hojas y contenedores: Composite. */
public sealed interface Elemento permits Archivo, Carpeta {
    int bytes();
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . Archivo.java Carpeta.java Elemento.java MainComposite.java
 *   java codetrace.practica.patrones.composite.MainComposite
 * Nota: Elemento.java es un componente; la salida corresponde al cliente MainComposite.java.
 * Salida esperada:
 *   Bytes: 220
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
