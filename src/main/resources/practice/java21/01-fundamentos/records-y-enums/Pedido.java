// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Componente didáctico reutilizable: este archivo no tiene main.
// Compilar: en una copia desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName

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
