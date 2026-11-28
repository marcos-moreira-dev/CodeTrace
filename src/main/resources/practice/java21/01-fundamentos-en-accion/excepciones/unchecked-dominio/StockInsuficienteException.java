// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA desde la raíz java21 (PowerShell):
// javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-curso-java" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: usar desde la clase cliente Main... del mismo ejemplo

/**
 * Excepción unchecked de una operación de inventario.
 *
 * Extender RuntimeException evita exigir catch/throws en cada llamador.
 * Puede ser apropiado para violaciones de contrato; el tipo concreto
 * comunica más que IllegalStateException cuando el dominio lo justifica.
 */
public final class StockInsuficienteException extends RuntimeException {
    public StockInsuficienteException(String sku, int pedido, int disponible) {
        super("Producto " + sku + ": solicitado " + pedido
                + ", disponible " + disponible);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out MainExcepcionUnchecked
 * Nota: StockInsuficienteException.java es un componente; la salida corresponde al cliente MainExcepcionUnchecked.java.
 * Salida esperada:
 *   Rechazo controlado: Producto A-001: solicitado 10, disponible 3
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
