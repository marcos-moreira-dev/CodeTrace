// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar desde esta carpeta (PowerShell): kotlinc -jvm-target 21 Observador.kt MainObservador.kt -include-runtime -d "$env:TEMP\observer.jar"
// Ejecutar: java -jar "$env:TEMP\observer.jar"

/**
 * Cliente Observer: suscribir dos funciones y publicar un cambio.
 *
 * Kotlin permite usar fun interface Escucha con lambdas. El observable
 * mantiene el estado; los oyentes reaccionan a las notificaciones,
 * sin que el emisor dependa de sus implementaciones.
 */
fun main() {
    val sensor = Observador()
    val eventos = mutableListOf<String>()
    sensor.suscribir { n -> eventos.add("vista:$n") }
    sensor.suscribir { n -> eventos.add("log:$n") }
    sensor.actualizar(42)
    println(eventos)
    check(eventos.size == 2 && sensor.valor == 42)
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\04-patrones-diseno\observer\basico\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Salida esperada:
 *   [vista:42, log:42]
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
