// Tema:
// MVVM. La heladería publica cambios de estado mediante PropertyChangeSupport. La vista observa el estado del ViewModel y envía acciones; el ViewModel no conoce la vista. No se requiere JavaFX.
// Rol: Conecta vista y ViewModel y simula selección válida y rechazada.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build modelo/Helado.java viewmodel/HeladoViewModel.java vista/VistaHeladeria.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

import vista.VistaHeladeria;
import viewmodel.HeladoViewModel;

public class Main {
    public static void main(String[] args) {
        var vista = new VistaHeladeria(new HeladoViewModel());
        vista.seleccionar("vainilla", 2);
        vista.seleccionar("chocolate", 3);
        vista.seleccionar("chocolate", 0);
    }
}

// Salida esperada:
// vainilla: 200 centavos
// chocolate: 450 centavos
// Seleccion rechazada
