// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde esta carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-kotlin-unit.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin-unit.jar"
// El cliente Main... es el único punto de entrada en cada unidad.

/**
 * Cliente Adapter: convertir la API de un proveedor de Fahrenheit a Celsius.
 *
 * El dominio habla en Celsius, el legado devuelve Fahrenheit y Adaptador
 * traduce la operación. El cliente depende de SensorCelsius y no conoce
 * los detalles del objeto legado. Es composición, no herencia de negocio.
 */
fun main() {
    val sensor: SensorCelsius = Adaptador(Legado())
    check(sensor.leer() == 25.0)
    println("Grados Celsius: " + sensor.leer())
}
