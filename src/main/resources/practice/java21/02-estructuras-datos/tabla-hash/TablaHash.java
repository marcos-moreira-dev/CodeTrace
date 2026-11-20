// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde copia del paquete, PowerShell: New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: Compilar como parte del ejercicio; sin main propio

package codetrace.practica.hash;

import java.util.Objects;

/**
 * Tabla hash didáctica con sondeo lineal y claves String.
 *
 * <p>hashCode distribuye claves en el arreglo. Una colisión se resuelve
 * probando posiciones siguientes, sin sobrescribir otras claves.
 * Se limita la carga antes del 70 % para mantener búsquedas razonables.
 * En promedio insertar/obtener son O(1); en peor caso O(n).
 *
 * <p>La implementación es intencionadamente pequeña: no admite borrar
 * ni crece indefinidamente. Para sistemas reales, utilizar HashMap.
 */
public final class TablaHash {
    private final String[] claves;
    private final Integer[] valores;
    private int cantidad;

    public TablaHash(int capacidad) {
        if (capacidad < 4) throw new IllegalArgumentException("Capacidad mínima 4");
        claves = new String[capacidad];
        valores = new Integer[capacidad];
    }

    public void poner(String clave, int valor) {
        Objects.requireNonNull(clave);
        int i = Math.floorMod(clave.hashCode(), claves.length);
        for (int pasos = 0; pasos < claves.length; pasos++) {
            if (claves[i] == null) {
                if ((cantidad + 1) * 10 > claves.length * 7)
                    throw new IllegalStateException("Capacidad didáctica agotada");
                claves[i] = clave;
                valores[i] = valor;
                cantidad++;
                return;
            }
            if (claves[i].equals(clave)) {
                valores[i] = valor;
                return;
            }
            i = (i + 1) % claves.length;
        }
        throw new IllegalStateException("Tabla llena");
    }

    public Integer obtener(String clave) {
        Objects.requireNonNull(clave);
        int i = Math.floorMod(clave.hashCode(), claves.length);
        for (int pasos = 0; pasos < claves.length; pasos++) {
            if (claves[i] == null) return null;
            if (claves[i].equals(clave)) return valores[i];
            i = (i + 1) % claves.length;
        }
        return null;
    }

    public int tamaño() { return cantidad; }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.hash.MainHash
 * Nota: TablaHash.java es un componente; la salida corresponde al cliente MainHash.java.
 * Salida esperada:
 *   FB=15
 *   Ea=20
 *   Ausente=null
 *   Claves distintas=2
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
