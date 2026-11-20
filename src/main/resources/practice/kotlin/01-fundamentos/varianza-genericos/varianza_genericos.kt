// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar (PowerShell): kotlinc -jvm-target 21 '.\01-fundamentos\varianza-genericos\varianza_genericos.kt' -include-runtime -d "$env:TEMP\ct-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin.jar"

/**
 * Cliente de varianza: out T significa productor de elementos T.
 *
 * Un Productor<String> puede utilizarse como Productor<CharSequence>
 * sin conversión peligrosa, porque solo entrega valores. Esto
 * ilustra contratos genéricos y su relación con interfaces.
 */
interface Productor<out T> { fun obtener(): T }
class Texto(private val valor: String) : Productor<String> {
    override fun obtener() = valor
}
fun mostrar(fuente: Productor<CharSequence>) {
    println(fuente.obtener())
}
fun main() = mostrar(Texto("Kotlin"))
