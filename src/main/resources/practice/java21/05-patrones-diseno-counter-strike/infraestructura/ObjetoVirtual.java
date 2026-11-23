// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Dominio: Counter-Strike, simulador narrativo de 3 rondas automáticas.
// Glosario: CT = antiterroristas; T = terroristas; PV = vida del jugador.
// Patrón de organización: infraestructura MVC, no un nuevo patrón GoF.
package codetrace.practica.counterstrike.infraestructura;

/**
 * Elemento del inventario de un jugador ficticio de Counter-Strike.
 *
 * "MP9" es el nombre de un subfusil del videojuego. Aquí es solamente
 * un objeto con nombre, categoría, coste en créditos y un contador
 * imaginario de capacidad. No se modela el uso de armas reales.
 *
 * @param nombre etiqueta visible en el inventario
 * @param categoria familia del objeto, p. ej. "subfusil"
 * @param precio coste en créditos del videojuego, no en dólares
 * @param capacidadVirtual contador ilustrativo de la interfaz del juego
 */
public record ObjetoVirtual(String nombre, String categoria,
                            int precio, int capacidadVirtual) {
    public ObjetoVirtual {
        if (nombre == null || nombre.isBlank()
                || categoria == null || categoria.isBlank()
                || precio < 0 || capacidadVirtual < 0)
            throw new IllegalArgumentException("Ficha de inventario inválida");
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Ejemplo en consola (PowerShell; desde la carpeta java21):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out codetrace.practica.counterstrike.MainCounterStrike
 * MP9 es solo una etiqueta del inventario ficticio con coste y capacidad ilustrativos.
 * Salida esperada:
 *   [TIENDA] Astra añadió MP9 | créditos=950 | capacidad ilustrativa=30
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
