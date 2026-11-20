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

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\04-patrones-diseno\adapter\unidades\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Nota: adapter_unidades.kt es un componente; la salida corresponde al cliente MainAdapter.kt.
 * Salida esperada:
 *   Grados Celsius: 25.0
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
