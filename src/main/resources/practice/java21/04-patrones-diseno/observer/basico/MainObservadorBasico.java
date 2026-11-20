// CodeTrace | Referencia: Java 21 LTS / JDK 21
// Compilar: New-Item -ItemType Directory -Force "$env:TEMP\ct-java21" | Out-Null; javac --release 21 -encoding UTF-8 -d "$env:TEMP\ct-java21" (Get-ChildItem -Recurse -Filter *.java | ForEach-Object FullName)
// Ejecutar: java -cp "$env:TEMP\ct-java21" MainObservadorBasico

/**
 * Cliente Observer básico con callbacks y lambdas de Java 21.
 *
 * <p>Un emisor puede notificar a múltiples suscriptores cuando
 * cambia su valor, sin conocer lo que esos suscriptores hacen.
 * Observador.java contiene la lista de callbacks y el mecanismo
 * de publicación; este cliente crea oyentes y provoca cambios.
 *
 * <p>Esta versión sencilla no incluye desuscripción ni políticas
 * ante excepciones de observadores. En un sistema productivo esos
 * contratos importan y el ciclo de vida de los suscriptores debe
 * definirse para evitar referencias retenidas.
 */
public final class MainObservadorBasico {
    public static void main(String[] args) {
        Observador sensor = new Observador();
        StringBuilder eventos = new StringBuilder();
        sensor.suscribir(v -> eventos.append("consola=").append(v).append(" "));
        sensor.suscribir(v -> eventos.append("log=").append(v));
        sensor.actualizar(42);
        System.out.println(eventos);
        if (sensor.valor() != 42 || !eventos.toString().contains("log=42"))
            throw new AssertionError("Fallo de notificación");
    }
}

/*
 * Salida esperada de consola:
 *   consola=42 log=42
 * Dos suscriptores reciben el mismo valor mediante callbacks.
 */

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   $out=Join-Path $env:TEMP "ct-practica-java"
 *   New-Item -ItemType Directory -Force -Path $out | Out-Null
 *   javac --release 21 -encoding UTF-8 -d $out (Get-ChildItem -Recurse -Filter *.java).FullName
 *   java -cp $out MainObservadorBasico
 * Salida esperada:
 *   consola=42 log=42
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
