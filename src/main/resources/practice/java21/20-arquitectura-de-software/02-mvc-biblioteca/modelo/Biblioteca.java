// Tema:
// MVC de consola. La biblioteca presta y devuelve libros. El controlador interpreta acciones, el modelo conserva y valida el estado, la vista presenta los resultados; no se necesita interfaz gráfica.
// Rol: Modelo de aplicación; localiza libros y ejecuta operaciones sin llamar a la vista.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build modelo/Libro.java modelo/Biblioteca.java vista/VistaBiblioteca.java controlador/ControladorPrestamos.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package modelo;

import java.util.Map;

public class Biblioteca {
    private final Map<String,Libro> libros = Map.of("L1", new Libro("El principito"));
    public Libro buscar(String id) {
        Libro libro = libros.get(id);
        if (libro == null) throw new IllegalArgumentException("Libro desconocido");
        return libro;
    }
    public void prestar(String id) { buscar(id).prestar(); }
    public void devolver(String id) { buscar(id).devolver(); }
}

// Salida esperada:
// El principito: prestado
// Operacion rechazada
// El principito: disponible
