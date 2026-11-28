// Tema:
// Clean Architecture. Peluquería canina agenda una cita; entidades y casos de uso no importan adaptadores. El controlador utiliza el límite de entrada y el interactor llama al límite de salida implementado por el presentador.
// Rol: Límite de salida; solo datos necesarios para presentar respuesta.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build entidades/Cita.java casosdeuso/EntradaCita.java casosdeuso/SalidaCita.java casosdeuso/RepositorioCitas.java casosdeuso/AgendarCita.java adaptadores/ControladorCitas.java adaptadores/PresentadorCitas.java infraestructura/CitasEnMemoria.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package casosdeuso;

public interface SalidaCita {
    void aceptada(int hora, String mascota);
    void rechazada(String motivo);
}

// Salida esperada:
// Cita: Toby a las 10
// Rechazada: Hora ocupada
// Rechazada: Hora invalida
