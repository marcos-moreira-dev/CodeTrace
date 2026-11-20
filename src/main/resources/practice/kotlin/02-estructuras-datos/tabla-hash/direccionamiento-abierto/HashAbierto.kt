// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde la carpeta de unidad: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin.jar" (desde el cliente Main...)

/** Sondeo lineal en hash de capacidad fija. Sin borrados: harían falta tumbas. */
class HashAbierto(capacidad:Int){
    private val a:Array<Pair<String,Int>?>
    var cantidad=0
        private set
    init{require(capacidad>0);a=arrayOfNulls(capacidad)}
    private fun indice(x:String)=Math.floorMod(x.hashCode(),a.size)
    fun poner(clave:String,valor:Int){
        val ini=indice(clave)
        repeat(a.size){paso->
            val i=(ini+paso)%a.size
            val actual=a[i]
            if(actual==null){a[i]=clave to valor;cantidad++;return}
            if(actual.first==clave){a[i]=clave to valor;return}
        }
        throw IllegalStateException("Tabla llena")
    }
    fun buscar(clave:String):Int?{
        val ini=indice(clave)
        repeat(a.size){paso->
            val actual=a[(ini+paso)%a.size]?:return null
            if(actual.first==clave)return actual.second
        }
        return null
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\02-estructuras-datos\tabla-hash\direccionamiento-abierto\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Nota: HashAbierto.kt es un componente; la salida corresponde al cliente MainHashAbierto.kt.
 * Salida esperada:
 *   Hash lleno controlado
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
