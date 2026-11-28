// Tema:
// Clean Architecture. Peluquería canina agenda una cita; entidades y casos de uso no importan adaptadores. El controlador utiliza el límite de entrada y el interactor llama al límite de salida implementado por el presentador.
// Rol: Cablea límites y adaptadores y prueba una cita aceptada y otra rechazada.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build entidades/Cita.java casosdeuso/EntradaCita.java casosdeuso/SalidaCita.java casosdeuso/RepositorioCitas.java casosdeuso/AgendarCita.java adaptadores/ControladorCitas.java adaptadores/PresentadorCitas.java infraestructura/CitasEnMemoria.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

import casosdeuso.AgendarCita;
import adaptadores.ControladorCitas;
import adaptadores.PresentadorCitas;
import infraestructura.CitasEnMemoria;

public class Main {
    public static void main(String[] args) {
        var presentador = new PresentadorCitas();
        var controlador = new ControladorCitas(new AgendarCita(new CitasEnMemoria(), presentador), presentador);
        controlador.recibir("10", "Toby");
        controlador.recibir("10", "Luna");
        controlador.recibir("abc", "Luna");
    }
}

// Salida esperada:
// Cita: Toby a las 10
// Rechazada: Hora ocupada
// Rechazada: Hora invalida
