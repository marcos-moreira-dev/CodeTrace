// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar (PowerShell): kotlinc -jvm-target 21 '.\04-patrones-diseno\adapter\unidades\adapter_unidades.kt' -include-runtime -d "$env:TEMP\ct-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin.jar"

/**
 * Cliente Adapter en Kotlin para una API con unidades incompatibles.
 *
 * Un termómetro externo habla Fahrenheit; la aplicación necesita
 * Celsius. El adaptador conoce el proveedor y el contrato del
 * dominio, encapsulando la conversión en un solo componente.
 */
interface SensorCelsius { fun leer(): Double }
class Legado { fun fahrenheit(): Double = 77.0 }
class Adaptador(private val proveedor: Legado) : SensorCelsius {
    override fun leer() = (proveedor.fahrenheit() - 32.0) * 5.0 / 9.0
}
fun main() {
    val sensor: SensorCelsius = Adaptador(Legado())
    println("Celsius: " + sensor.leer())
}
