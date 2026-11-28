// CodeTrace | Referencia: Java 21 / JDK 21
// Desde esta carpeta.
// Compilar: javac -encoding UTF-8 -d . ArbolAVL.java MainArbolAVL.java
// Ejecutar: java codetrace.practica.estructuras.avl.MainArbolAVL

package codetrace.practica.estructuras.avl;

import java.util.ArrayList;
import java.util.List;

/**
 * AVL: BST autoequilibrado con factor de balance entre -1 y 1.
 * Rotaciones simples y dobles restauran el invariante tras insertar.
 * Inserción/búsqueda O(log n) garantizado, espacio O(n).
 * Para entender AVL, comparar su altura con BST no balanceado.
 */
public final class ArbolAVL {
    private static final class Nodo {
        int valor, altura=1;
        Nodo izq, der;
        Nodo(int valor) { this.valor=valor; }
    }
    private Nodo raiz;
    private int altura(Nodo n) { return n==null?0:n.altura; }
    private void actualizar(Nodo n) { n.altura=1+Math.max(altura(n.izq),altura(n.der)); }
    private int balance(Nodo n) { return altura(n.izq)-altura(n.der); }
    private Nodo girarDerecha(Nodo y) {
        Nodo x=y.izq, centro=x.der;
        x.der=y; y.izq=centro;
        actualizar(y); actualizar(x); return x;
    }
    private Nodo girarIzquierda(Nodo x) {
        Nodo y=x.der, centro=y.izq;
        y.izq=x; x.der=centro;
        actualizar(x); actualizar(y); return y;
    }
    private Nodo insertar(Nodo n,int valor) {
        if(n==null)return new Nodo(valor);
        if(valor<n.valor)n.izq=insertar(n.izq,valor);
        else if(valor>n.valor)n.der=insertar(n.der,valor);
        else return n;
        actualizar(n);
        int b=balance(n);
        if(b>1) {
            if(valor>n.izq.valor)n.izq=girarIzquierda(n.izq);
            return girarDerecha(n);
        }
        if(b< -1) {
            if(valor<n.der.valor)n.der=girarDerecha(n.der);
            return girarIzquierda(n);
        }
        return n;
    }
    public void insertar(int valor) { raiz=insertar(raiz,valor); }
    public int altura() { return altura(raiz); }
    public List<Integer> inorden() {
        List<Integer> salida=new ArrayList<>();
        recorrer(raiz,salida); return salida;
    }
    private void recorrer(Nodo n,List<Integer> salida) {
        if(n==null)return;
        recorrer(n.izq,salida); salida.add(n.valor); recorrer(n.der,salida);
    }
    public void validar() { validar(raiz); }
    private int validar(Nodo n) {
        if(n==null)return 0;
        int a=validar(n.izq), b=validar(n.der);
        if(Math.abs(a-b)>1||n.altura!=1+Math.max(a,b))
            throw new AssertionError("AVL desbalanceado");
        return n.altura;
    }
}

/*
 * CodeTrace | EJEMPLO DE CONSOLA (practica)
 * Desde esta carpeta:
 *   javac -encoding UTF-8 -d . ArbolAVL.java MainArbolAVL.java
 *   java codetrace.practica.estructuras.avl.MainArbolAVL
 * Nota: ArbolAVL.java es un componente; la salida corresponde al cliente MainArbolAVL.java.
 * Salida esperada:
 *   Altura del AVL: 4
 
 * CodeTrace | FIN EJEMPLO DE CONSOLA
 */
