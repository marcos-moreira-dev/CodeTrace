// Compilar (desde esta carpeta): javac -encoding UTF-8 -d . ArchivoMapa.java CarpetaMapa.java MainCompositeCS.java RecursoMapa.java
// Ejecutar: java codetrace.practica.counterstrike.composite.MainCompositeCS
// Dominio: Counter-Strike — árbol de mapas y archivos del juego, sin cargar contenidos protegidos.
// Patrón: Composite; ejercicio educativo no oficial.
// Glosario: Mapa = escenario virtual; recurso = archivo del videojuego; carpeta = contenedor de recursos.
package codetrace.practica.counterstrike.composite;

/** Elemento común: tanto archivos como carpetas responden a contar(). */
public sealed interface RecursoMapa permits ArchivoMapa, CarpetaMapa {
    int contar();
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . ArchivoMapa.java CarpetaMapa.java MainCompositeCS.java RecursoMapa.java
 *   java codetrace.practica.counterstrike.composite.MainCompositeCS
 * Nota: RecursoMapa.java pertenece al ejercicio; el cliente es MainCompositeCS.java.
 * Salida esperada:
 *   Mapas registrados: 3
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
