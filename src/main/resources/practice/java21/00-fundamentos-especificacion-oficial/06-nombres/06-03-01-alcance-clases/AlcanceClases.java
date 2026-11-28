// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §6.3; ejemplo 6.3-1
// Compilar: javac --release 21 -encoding UTF-8 -d . AlcanceClases.java Nodo.java Cadena.java
// Ejecutar: java AlcanceClases
// Comandos desde la carpeta de la unidad.

public class AlcanceClases {
    public static void main(String[] args) {
        Nodo n = new Nodo();
        n.lista = new Cadena();
        n.lista.primero = n;
        System.out.println(n == n.lista.primero);
    }
}

// Salida esperada:
// true
