// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Componente didáctico reutilizable: este archivo no tiene main.
// Compilar: en una copia desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\\ct-java21" (Get-ChildItem -Recurse -Filter *.java -File | Where-Object { $_.FullName -notmatch '[\\/]00-fundamentos-especificacion-oficial[\\/]' }).FullName

/** Valor inmutable superficialmente; los componentes son String, double y Estado. */
public record Pedido(String codigo, double total, Estado estado) {
    public Pedido {
        if (codigo == null || codigo.isBlank() || total < 0)
            throw new IllegalArgumentException("Pedido inválido");
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (práctica)
 * Compilar todos los .java desde la carpeta java21 y ejecutar RecordsYEnums.
 * Salida esperada:
 *   Pedido[codigo=P-001, total=12.5, estado=RECIBIDO]
 *   Pedido[codigo=P-002, total=20.0, estado=ENTREGADO]
 * Este tipo no tiene main; su cliente demuestra el comportamiento.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 *
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java -File | Where-Object { $_.FullName -notmatch '[\\/]00-fundamentos-especificacion-oficial[\\/]' }).FullName
 *   java -cp $out RecordsYEnums
 * Nota: Pedido.java es un componente; la salida corresponde al cliente RecordsYEnums.java.
 * Salida esperada:
 *   Pedido[codigo=P-001, total=12.5, estado=RECIBIDO]
 *   Pedido[codigo=P-002, total=20.0, estado=ENTREGADO]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
