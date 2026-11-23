// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Universo compartido: Arena Táctica, juego competitivo ficticio por equipos.
// Dominio: Tarifas de inscripción al torneo según la categoría.
// Patrón strategy-tarifas: Elegir entre reglas de cálculo intercambiables.
// Glosario: Inscripción: registro de un equipo para competir.
// Compilar: javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java).FullName
// Ejecutar: java -cp "$env:TEMP\ct-java21" codetrace.practica.tarifas.MainTarifas
package codetrace.practica.tarifas;

/**
 * Cliente del patrón Strategy usando una interfaz funcional.
 *
 * <p>Un sistema de cobros tiene reglas intercambiables de cálculo. En vez
 * de multiplicar condicionales dentro del cliente, se selecciona una
 * estrategia que cumple el contrato. Java permite implementar ese contrato
 * tanto con clases tradicionales como con lambdas.
 *
 * <p>Roles: EstrategiaTarifa = contrato; Tarifas = proveedores concretos;
 * MainTarifas = cliente que elige el comportamiento. No hay MVC porque no
 * existe una interfaz visual o controlador independiente que lo justifique.
 */
public final class MainTarifas {
    static double cobrar(double subtotal, EstrategiaTarifa estrategia) {
        return estrategia.total(subtotal);
    }

    public static void main(String[] args) {
        System.out.println(cobrar(100.0, Tarifas.normal()));
        System.out.println(cobrar(100.0, Tarifas.descuento(0.15)));
    }
}

/*
 * Salida esperada de consola:
 *   100.0
 *   85.0
 * El primer importe usa la tarifa normal; el segundo aplica 15 % de descuento.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.tarifas.MainTarifas
 * Salida esperada:
 *   100.0
 *   85.0
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
