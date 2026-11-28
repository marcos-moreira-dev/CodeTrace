// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde la raíz java21 (PowerShell):
// javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-curso-java" (Get-ChildItem -Recurse -Filter *.java -File | Where-Object { $_.FullName -notmatch '[\\/]00-fundamentos-especificacion-oficial[\\/]' }).FullName
// Ejecutar: usar desde la clase cliente Main... del mismo ejemplo

/** Modelo mínimo de inventario: unidades nunca negativas. */
public final class Inventario {
    private final String sku;
    private int unidades;

    public Inventario(String sku, int unidades) {
        if (sku == null || sku.isBlank() || unidades < 0)
            throw new IllegalArgumentException("Datos de inventario");
        this.sku = sku;
        this.unidades = unidades;
    }

    public void despachar(int solicitadas) {
        if (solicitadas <= 0) throw new IllegalArgumentException("Cantidad positiva");
        if (solicitadas > unidades)
            throw new StockInsuficienteException(sku, solicitadas, unidades);
        unidades -= solicitadas;
    }
    public int disponibles() { return unidades; }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java -File | Where-Object { $_.FullName -notmatch '[\\/]00-fundamentos-especificacion-oficial[\\/]' }).FullName
 *   java -cp $out MainExcepcionUnchecked
 * Nota: Inventario.java es un componente; la salida corresponde al cliente MainExcepcionUnchecked.java.
 * Salida esperada:
 *   Rechazo controlado: Producto A-001: solicitado 10, disponible 3
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
