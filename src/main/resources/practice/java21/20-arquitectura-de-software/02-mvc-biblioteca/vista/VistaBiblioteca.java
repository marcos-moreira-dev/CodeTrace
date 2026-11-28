// Tema:
// MVC de consola. La biblioteca presta y devuelve libros. El controlador interpreta acciones, el modelo conserva y valida el estado, la vista presenta los resultados; no se necesita interfaz gráfica.
// Rol: Vista de solo lectura; recibe estado del modelo, no modifica préstamos.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build modelo/Libro.java modelo/Biblioteca.java vista/VistaBiblioteca.java controlador/ControladorPrestamos.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package vista;

import modelo.Libro;

public class VistaBiblioteca {
    public void mostrar(Libro libro) {
        System.out.println(libro.titulo() + ": " + (libro.disponible() ? "disponible" : "prestado"));
    }
    public void error() { System.out.println("Operacion rechazada"); }
}

// Salida esperada:
// El principito: prestado
// Operacion rechazada
// El principito: disponible
