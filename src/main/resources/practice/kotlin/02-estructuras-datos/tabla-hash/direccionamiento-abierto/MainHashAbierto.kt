// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde la carpeta de unidad: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin.jar" (desde el cliente Main...)

/**
 * Cliente hash abierto: las colisiones no sobrescriben otras claves.
 *
 * El sondeo lineal difiere del encadenamiento con listas; al llenar
 * todas las celdas se rechaza insertar una clave nueva. El programa
 * verifica actualización, inexistencia y saturación.
 */
fun main(){
    val t=HashAbierto(2)
    t.poner("A",1);t.poner("B",2);t.poner("A",3)
    check(t.buscar("A")==3&&t.buscar("B")==2&&t.buscar("Z")==null)
    check(t.cantidad==2)
    try{t.poner("C",4);error("Debió fallar")}
    catch(e:IllegalStateException){println("Hash lleno controlado")}
}
