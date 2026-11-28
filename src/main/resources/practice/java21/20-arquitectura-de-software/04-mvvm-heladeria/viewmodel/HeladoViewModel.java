// Tema:
// MVVM. La heladería publica cambios de estado mediante PropertyChangeSupport. La vista observa el estado del ViewModel y envía acciones; el ViewModel no conoce la vista. No se requiere JavaFX.
// Rol: Mantiene estado de presentación observable; publica cambios y errores mediante propiedades.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build modelo/Helado.java viewmodel/HeladoViewModel.java vista/VistaHeladeria.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package viewmodel;

import modelo.Helado;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class HeladoViewModel {
    private final PropertyChangeSupport cambios = new PropertyChangeSupport(this);
    private String estado = "";
    public void observar(PropertyChangeListener escucha) { cambios.addPropertyChangeListener("estado", escucha); }
    public String estado() { return estado; }
    public void elegir(String sabor, int cantidad) {
        String nuevo;
        try {
            Helado helado = new Helado(sabor, cantidad);
            nuevo = helado.sabor() + ": " + helado.totalCentavos() + " centavos";
        } catch (IllegalArgumentException e) { nuevo = "Seleccion rechazada"; }
        String anterior = estado;
        estado = nuevo;
        cambios.firePropertyChange("estado", anterior, nuevo);
    }
}

// Salida esperada:
// vainilla: 200 centavos
// chocolate: 450 centavos
// Seleccion rechazada
