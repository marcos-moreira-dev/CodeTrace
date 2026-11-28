// Tema:
// Las declaraciones de tipos pueden referenciar otros tipos de su mismo alcance aunque
// aparezcan en otro archivo. El orden de los archivos no determina su visibilidad.
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
