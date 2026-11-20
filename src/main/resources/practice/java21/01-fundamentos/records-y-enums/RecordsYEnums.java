// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar desde copia del paquete, PowerShell: New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: java -cp "$env:TEMP\ct-java21" RecordsYEnums

import java.util.List;

/**
 * Cliente de dos construcciones muy habituales en Java moderno.
 *
 * <p>Un enum representa un conjunto cerrado de valores con identidad estable.
 * Un record agrupa datos y genera constructor, accesores, equals, hashCode
 * y toString. Su inmutabilidad es superficial: un componente mutable
 * continúa siendo mutable, por lo que aquí se utiliza una String.
 *
 * <p>No todos los objetos deben ser records: una entidad mutable con reglas
 * de ciclo de vida puede justificar una clase tradicional.
 */
public class RecordsYEnums {
    enum Estado { RECIBIDO, PREPARANDO, ENTREGADO }

    record Pedido(String codigo, double total, Estado estado) {
        Pedido {
            if (codigo == null || codigo.isBlank() || total < 0)
                throw new IllegalArgumentException("Pedido inválido");
        }
    }

    public static void main(String[] args) {
        List<Pedido> pedidos = List.of(
            new Pedido("P-001", 12.5, Estado.RECIBIDO),
            new Pedido("P-002", 20, Estado.ENTREGADO)
        );
        pedidos.forEach(System.out::println);
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out RecordsYEnums
 * Salida esperada:
 *   Pedido[codigo=P-001, total=12.5, estado=RECIBIDO]
 *   Pedido[codigo=P-002, total=20.0, estado=ENTREGADO]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
