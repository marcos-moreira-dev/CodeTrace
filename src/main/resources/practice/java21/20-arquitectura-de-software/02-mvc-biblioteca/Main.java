// Tema:
// MVC de consola. La biblioteca presta y devuelve libros. El controlador interpreta acciones, el modelo conserva y valida el estado, la vista presenta los resultados; no se necesita interfaz gráfica.
// Rol: Simula acciones de interfaz y conecta modelo, vista y controlador.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build modelo/Libro.java modelo/Biblioteca.java vista/VistaBiblioteca.java controlador/ControladorPrestamos.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

import modelo.Biblioteca;
import vista.VistaBiblioteca;
import controlador.ControladorPrestamos;

public class Main {
    public static void main(String[] args) {
        var controlador = new ControladorPrestamos(new Biblioteca(), new VistaBiblioteca());
        controlador.actuar("prestar", "L1");
        controlador.actuar("prestar", "L1");
        controlador.actuar("devolver", "L1");
    }
}

// Salida esperada:
// El principito: prestado
// Operacion rechazada
// El principito: disponible
