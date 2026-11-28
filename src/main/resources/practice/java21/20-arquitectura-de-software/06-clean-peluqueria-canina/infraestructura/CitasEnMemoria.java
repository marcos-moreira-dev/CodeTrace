// Tema:
// Clean Architecture. Peluquería canina agenda una cita; entidades y casos de uso no importan adaptadores. El controlador utiliza el límite de entrada y el interactor llama al límite de salida implementado por el presentador.
// Rol: Implementa almacenamiento sin que el interactor conozca el mapa.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build entidades/Cita.java casosdeuso/EntradaCita.java casosdeuso/SalidaCita.java casosdeuso/RepositorioCitas.java casosdeuso/AgendarCita.java adaptadores/ControladorCitas.java adaptadores/PresentadorCitas.java infraestructura/CitasEnMemoria.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package infraestructura;

import casosdeuso.RepositorioCitas;
import entidades.Cita;
import java.util.HashMap;

public class CitasEnMemoria implements RepositorioCitas {
    private final HashMap<Integer,Cita> citas = new HashMap<>();
    public boolean ocupada(int hora) { return citas.containsKey(hora); }
    public void guardar(Cita cita) {
        if (citas.putIfAbsent(cita.hora(), cita) != null) throw new IllegalStateException("Ocupada");
    }
}

// Salida esperada:
// Cita: Toby a las 10
// Rechazada: Hora ocupada
// Rechazada: Hora invalida
