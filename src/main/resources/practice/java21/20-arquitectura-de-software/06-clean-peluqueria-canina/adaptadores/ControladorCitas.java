// Tema:
// Clean Architecture. Peluquería canina agenda una cita; entidades y casos de uso no importan adaptadores. El controlador utiliza el límite de entrada y el interactor llama al límite de salida implementado por el presentador.
// Rol: Traduce texto externo a parámetros del límite de entrada.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build entidades/Cita.java casosdeuso/EntradaCita.java casosdeuso/SalidaCita.java casosdeuso/RepositorioCitas.java casosdeuso/AgendarCita.java adaptadores/ControladorCitas.java adaptadores/PresentadorCitas.java infraestructura/CitasEnMemoria.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package adaptadores;

import casosdeuso.EntradaCita;
import casosdeuso.SalidaCita;

public class ControladorCitas {
    private final EntradaCita entrada;
    private final SalidaCita salida;
    public ControladorCitas(EntradaCita entrada, SalidaCita salida) {
        this.entrada = entrada; this.salida = salida;
    }
    public void recibir(String hora, String mascota) {
        int numero;
        try { numero = Integer.parseInt(hora); }
        catch (NumberFormatException e) { salida.rechazada("Hora invalida"); return; }
        entrada.agendar(numero, mascota);
    }
}

// Salida esperada:
// Cita: Toby a las 10
// Rechazada: Hora ocupada
// Rechazada: Hora invalida
