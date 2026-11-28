// Tema:
// MVC de consola. La biblioteca presta y devuelve libros. El controlador interpreta acciones, el modelo conserva y valida el estado, la vista presenta los resultados; no se necesita interfaz gráfica.
// Rol: Entidad que encapsula disponibilidad.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build modelo/Libro.java modelo/Biblioteca.java vista/VistaBiblioteca.java controlador/ControladorPrestamos.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package modelo;

public class Libro {
    private final String titulo;
    private boolean prestado;
    public Libro(String titulo) { this.titulo = titulo; }
    public String titulo() { return titulo; }
    public boolean disponible() { return !prestado; }
    public void prestar() {
        if (prestado) throw new IllegalStateException("Ocupado");
        prestado = true;
    }
    public void devolver() {
        if (!prestado) throw new IllegalStateException("No prestado");
        prestado = false;
    }
}

// Salida esperada:
// El principito: prestado
// Operacion rechazada
// El principito: disponible
