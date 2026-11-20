// CodeTrace | Referencia: Kotlin 2.2 / JVM 21
// Compilar en COPIA desde la carpeta de unidad: kotlinc -jvm-target 21 *.kt -include-runtime -d "$env:TEMP\ct-kotlin.jar"
// Ejecutar: java -jar "$env:TEMP\ct-kotlin.jar" (desde el cliente Main...)

import kotlin.math.abs
import kotlin.math.max

/**
 * AVL autoequilibrado: balance [-1,+1] y rotaciones.
 * Inserción O(log n) garantizada, a diferencia de un BST ordinario.
 */
class ArbolAVL{
    private class Nodo(val valor:Int){
        var izq:Nodo?=null
        var der:Nodo?=null
        var altura=1
    }
    private var raiz:Nodo?=null
    private fun altura(n:Nodo?):Int=n?.altura?:0
    private fun actualizar(n:Nodo){n.altura=1+max(altura(n.izq),altura(n.der))}
    private fun derecha(y:Nodo):Nodo{
        val x=y.izq!!
        y.izq=x.der;x.der=y
        actualizar(y);actualizar(x);return x
    }
    private fun izquierda(x:Nodo):Nodo{
        val y=x.der!!
        x.der=y.izq;y.izq=x
        actualizar(x);actualizar(y);return y
    }
    fun insertar(valor:Int){
        fun poner(n:Nodo?):Nodo{
            if(n==null)return Nodo(valor)
            when{
                valor<n.valor->n.izq=poner(n.izq)
                valor>n.valor->n.der=poner(n.der)
                else->return n
            }
            actualizar(n)
            val b=altura(n.izq)-altura(n.der)
            if(b>1){
                if(valor>n.izq!!.valor)n.izq=izquierda(n.izq!!)
                return derecha(n)
            }
            if(b< -1){
                if(valor<n.der!!.valor)n.der=derecha(n.der!!)
                return izquierda(n)
            }
            return n
        }
        raiz=poner(raiz)
    }
    fun inorden():List<Int>{
        val r=mutableListOf<Int>()
        fun visitar(n:Nodo?){
            if(n==null)return
            visitar(n.izq);r.add(n.valor);visitar(n.der)
        }
        visitar(raiz);return r
    }
    fun validar():Int{
        fun recorrer(n:Nodo?):Int{
            if(n==null)return 0
            val a=recorrer(n.izq);val b=recorrer(n.der)
            check(abs(a-b)<=1&&n.altura==1+max(a,b))
            return n.altura
        }
        return recorrer(raiz)
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * 
 * Ejemplo en consola (PowerShell; desde la carpeta del lenguaje):
 *   kotlinc .\02-estructuras-datos\arbol-avl\*.kt -jvm-target 21 -include-runtime -d "$env:TEMP\ct-kotlin.jar"
 *   java -jar "$env:TEMP\ct-kotlin.jar"
 * Nota: ArbolAVL.kt es un componente; la salida corresponde al cliente MainArbolAVL.kt.
 * Salida esperada:
 *   Altura AVL: 4
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
