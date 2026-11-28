// Tema:
// MVC de consola. La biblioteca presta y devuelve libros. El controlador interpreta acciones, el modelo conserva y valida el estado, la vista presenta los resultados; no se necesita interfaz gráfica.
// Rol: Controlador: decide qué operación del modelo corresponde a la acción y actualiza la vista.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build modelo/Libro.java modelo/Biblioteca.java vista/VistaBiblioteca.java controlador/ControladorPrestamos.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package controlador;

import modelo.Biblioteca;
import vista.VistaBiblioteca;

public class ControladorPrestamos {
    private final Biblioteca modelo;
    private final VistaBiblioteca vista;
    public ControladorPrestamos(Biblioteca modelo, VistaBiblioteca vista) {
        this.modelo = modelo; this.vista = vista;
    }
    public void actuar(String accion, String id) {
        try {
            switch (accion) {
                case "prestar" -> modelo.prestar(id);
                case "devolver" -> modelo.devolver(id);
                default -> throw new IllegalArgumentException("Accion");
            }
            vista.mostrar(modelo.buscar(id));
        } catch (IllegalArgumentException | IllegalStateException e) {
            vista.error();
        }
    }
}

// Salida esperada:
// El principito: prestado
// Operacion rechazada
// El principito: disponible
