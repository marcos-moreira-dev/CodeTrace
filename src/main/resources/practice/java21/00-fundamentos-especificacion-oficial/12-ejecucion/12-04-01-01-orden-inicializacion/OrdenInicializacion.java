// Tema:
// Al inicializar una clase se inicializa primero su superclase si aún no lo está. Los bloques
// static muestran ese orden.
// Compilar: javac --release 21 -encoding UTF-8 -d . OrdenInicializacion.java Padre.java Hija.java
// Ejecutar: java OrdenInicializacion
// Comandos desde la carpeta de la unidad.

public class OrdenInicializacion {
    public static void main(String[] args) { new Hija(); }
}

// Salida esperada:
// padre
// hija
