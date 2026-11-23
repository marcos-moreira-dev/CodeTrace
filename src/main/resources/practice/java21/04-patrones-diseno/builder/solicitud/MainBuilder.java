// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Consulta de estadísticas de un torneo ficticio.
// Patrón builder: Construir solicitudes gradualmente con datos opcionales.
// Glosario: Estadística: resultado agregado de partidas; torneo: competición por equipos.
// Compilar en COPIA desde java21: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-practice" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-practice" codetrace.practica.patrones.builder.MainBuilder

package codetrace.practica.patrones.builder;

/**
 * Cliente Builder: construir un objeto válido paso a paso.
 *
 * <p>Builder aporta claridad cuando hay opciones y validación, sin
 * forzar un constructor con muchos parámetros opcionales. Se separa
 * proceso de construcción del producto. Este ejemplo usa un record
 * inmutable y un Builder fluido. No supone que Builder es siempre
 * preferible: con dos campos triviales basta un constructor.
 */
public final class MainBuilder {
    public static void main(String[] args) {
        Solicitud s=new Solicitud.Builder().ruta("/facturas")
            .metodo("POST").cabecera("Content-Type: application/json").construir();
        if(!s.ruta().equals("/facturas")||s.cabeceras().size()!=1)
            throw new AssertionError("Builder");
        System.out.println(s.metodo()+" "+s.ruta()+" ("+s.cabeceras().size()+" cabecera)");
    }
}

/*
 * Salida esperada de consola:
 *   POST /facturas (1 cabecera)
 * El record Solicitud es inmutable y el Builder valida sus opciones.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.patrones.builder.MainBuilder
 * Salida esperada:
 *   POST /facturas (1 cabecera)
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
