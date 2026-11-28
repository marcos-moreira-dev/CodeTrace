// Tema:
// MVVM. La heladería publica cambios de estado mediante PropertyChangeSupport. La vista observa el estado del ViewModel y envía acciones; el ViewModel no conoce la vista. No se requiere JavaFX.
// Rol: Suscribe el estado observable y transmite las acciones; no conoce Helado.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build modelo/Helado.java viewmodel/HeladoViewModel.java vista/VistaHeladeria.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package vista;

import viewmodel.HeladoViewModel;

public class VistaHeladeria {
    private final HeladoViewModel vm;
    public VistaHeladeria(HeladoViewModel vm) {
        this.vm = vm;
        vm.observar(evento -> System.out.println(evento.getNewValue()));
    }
    public void seleccionar(String sabor, int cantidad) { vm.elegir(sabor, cantidad); }
}

// Salida esperada:
// vainilla: 200 centavos
// chocolate: 450 centavos
// Seleccion rechazada
