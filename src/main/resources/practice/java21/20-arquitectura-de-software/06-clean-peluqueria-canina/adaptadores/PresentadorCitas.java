// Tema:
// Clean Architecture. Peluquería canina agenda una cita; entidades y casos de uso no importan adaptadores. El controlador utiliza el límite de entrada y el interactor llama al límite de salida implementado por el presentador.
// Rol: Implementación del límite de salida; convierte respuesta en texto visible.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build entidades/Cita.java casosdeuso/EntradaCita.java casosdeuso/SalidaCita.java casosdeuso/RepositorioCitas.java casosdeuso/AgendarCita.java adaptadores/ControladorCitas.java adaptadores/PresentadorCitas.java infraestructura/CitasEnMemoria.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package adaptadores;

import casosdeuso.SalidaCita;

public class PresentadorCitas implements SalidaCita {
    public void aceptada(int hora, String mascota) {
        System.out.println("Cita: " + mascota + " a las " + hora);
    }
    public void rechazada(String motivo) { System.out.println("Rechazada: " + motivo); }
}

// Salida esperada:
// Cita: Toby a las 10
// Rechazada: Hora ocupada
// Rechazada: Hora invalida
