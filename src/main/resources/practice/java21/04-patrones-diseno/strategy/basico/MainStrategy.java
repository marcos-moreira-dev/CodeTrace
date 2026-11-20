// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar en COPIA: javac --release 21 -encoding UTF-8 Strategy.java MainStrategy.java
// Ejecutar: java MainStrategy

/**
 * Cliente Strategy: reglas intercambiables en un mismo contexto.
 *
 * El cliente configura dos lambdas sobre la interfaz Descuento.
 * Strategy.cobrar() no pregunta cuál es la clase concreta: ejecuta
 * el contrato. Se evita forzar subclases cuando la política es
 * una función sin estado, pero una clase sería válida para estrategias
 * configurables y con responsabilidad propia.
 */
public final class MainStrategy {
    public static void main(String[] args) {
        Strategy.Descuento normal = importe -> importe;
        Strategy.Descuento fidelidad = importe -> importe * .90;
        double subtotal = 100;
        double esperado = Strategy.cobrar(subtotal, fidelidad);
        if (esperado != 90) throw new AssertionError("Descuento incorrecto");
        System.out.println("Normal: " + Strategy.cobrar(subtotal, normal));
        System.out.println("Fidelidad: " + esperado);
    }
}

/*
 * Salida esperada de consola:
 *   Normal: 100.0
 *   Fidelidad: 90.0
 * Se cambia la política de cobro sin modificar el contexto Strategy.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out MainStrategy
 * Salida esperada:
 *   Normal: 100.0
 *   Fidelidad: 90.0
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
