// CodeTrace | Referencia: Java 21 LTS / JDK 21
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
        System.out.println(s);
    }
}

/*
 * Salida esperada de consola (toString automático del record Solicitud):
 *   Solicitud[ruta=/facturas, metodo=POST, cabeceras=[Content-Type: application/json]]
 * El producto conserva una sola cabecera y valores válidos.
 */
