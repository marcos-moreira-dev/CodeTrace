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
 * Salida esperada de consola:
 *   Se imprime el valor o coleccion calculado por el cliente.
 * Verificacion: termina sin excepciones inesperadas.
 */
