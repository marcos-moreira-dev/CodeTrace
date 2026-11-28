// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . HashAbierto.java MainHashAbierto.java
// Ejecutar: java codetrace.practica.estructuras.hashabierto.MainHashAbierto

package codetrace.practica.estructuras.hashabierto;

/**
 * Cliente de tabla hash: resolución por sondeo lineal, no por listas.
 *
 * <p>Con solo dos cubetas se producen colisiones. Una colisión NO
 * autoriza sobrescribir otra clave: se sigue buscando otra celda.
 * El ejemplo muestra actualización, ausencia y saturación. La
 * eliminación requeriría tombstones para no romper cadenas de sondeo.
 */
public final class MainHashAbierto {
    public static void main(String[] args) {
        var t=new HashAbierto(2);
        t.poner("A",1);t.poner("B",2);t.poner("A",3);
        if(t.cantidad()!=2||t.buscar("A")!=3||t.buscar("B")!=2
                ||t.buscar("X")!=null)throw new AssertionError("Hash");
        try { t.poner("C",4);throw new AssertionError("Aceptó tabla llena"); }
        catch(IllegalStateException esperado){System.out.println("Tabla llena controlada");}
    }
}

/*
 * Salida esperada de consola:
 *   Tabla llena controlada
 * Se actualiza la clave A y se comprueba la saturación de dos cubetas.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . HashAbierto.java MainHashAbierto.java
 *   java codetrace.practica.estructuras.hashabierto.MainHashAbierto
 * Salida esperada:
 *   Tabla llena controlada
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
