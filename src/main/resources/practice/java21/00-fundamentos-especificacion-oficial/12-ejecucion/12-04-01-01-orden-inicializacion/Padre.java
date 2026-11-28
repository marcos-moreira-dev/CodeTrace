// CodeTrace | Fundamentos - Especificación oficial
// Referencia: Java SE 21, JLS §12.4.1; ejemplo 12.4.1-1
// Compilar: javac --release 21 -encoding UTF-8 -d . OrdenInicializacion.java Padre.java Hija.java
// Ejecutar: java OrdenInicializacion
// Comandos desde la carpeta de la unidad.

class Padre {
    static { System.out.println("padre"); }
}

// Salida esperada:
// padre
// hija
