// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde esta carpeta: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-kotlin-unit.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin-unit.jar"
// El cliente Main... es el único punto de entrada en cada unidad.

/** Contrato deseado del dominio, que no conoce Fahrenheit. */
interface SensorCelsius { fun leer(): Double }

/** Proveedor externo simulado, que no debemos modificar. */
class Legado { fun fahrenheit(): Double = 77.0 }

/** Adapter transforma el contrato legado en el esperado. */
class Adaptador(private val proveedor: Legado) : SensorCelsius {
    override fun leer(): Double = (proveedor.fahrenheit() - 32.0) * 5.0 / 9.0
}
