// Tema:
// Clean Architecture. Peluquería canina agenda una cita; entidades y casos de uso no importan adaptadores. El controlador utiliza el límite de entrada y el interactor llama al límite de salida implementado por el presentador.
// Rol: Interactor: valida entidad y disponibilidad, persiste y entrega resultado a través del límite.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build entidades/Cita.java casosdeuso/EntradaCita.java casosdeuso/SalidaCita.java casosdeuso/RepositorioCitas.java casosdeuso/AgendarCita.java adaptadores/ControladorCitas.java adaptadores/PresentadorCitas.java infraestructura/CitasEnMemoria.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package casosdeuso;

import entidades.Cita;

public class AgendarCita implements EntradaCita {
    private final RepositorioCitas repositorio;
    private final SalidaCita salida;
    public AgendarCita(RepositorioCitas repositorio, SalidaCita salida) {
        this.repositorio = repositorio; this.salida = salida;
    }
    public void agendar(int hora, String mascota) {
        Cita cita;
        try { cita = new Cita(hora, mascota); }
        catch (IllegalArgumentException e) { salida.rechazada("Datos invalidos"); return; }
        if (repositorio.ocupada(hora)) { salida.rechazada("Hora ocupada"); return; }
        repositorio.guardar(cita);
        salida.aceptada(cita.hora(), cita.mascota());
    }
}

// Salida esperada:
// Cita: Toby a las 10
// Rechazada: Hora ocupada
// Rechazada: Hora invalida
