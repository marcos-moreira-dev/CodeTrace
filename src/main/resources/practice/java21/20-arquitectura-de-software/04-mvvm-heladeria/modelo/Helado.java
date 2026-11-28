// Tema:
// MVVM. La heladería publica cambios de estado mediante PropertyChangeSupport. La vista observa el estado del ViewModel y envía acciones; el ViewModel no conoce la vista. No se requiere JavaFX.
// Rol: Modelo inmutable con precio y reglas de selección.
// Modalidad: aplicación real de consola, datos ficticios en memoria.
// Compilar: javac --release 21 -encoding UTF-8 -d build modelo/Helado.java viewmodel/HeladoViewModel.java vista/VistaHeladeria.java Main.java
// Ejecutar: java -cp build Main (desde la raíz del microproyecto).

package modelo;

public record Helado(String sabor, int cantidad) {
    public Helado {
        if (!"vainilla".equals(sabor) && !"chocolate".equals(sabor))
            throw new IllegalArgumentException("Sabor");
        if (cantidad < 1 || cantidad > 10) throw new IllegalArgumentException("Cantidad");
    }
    public int totalCentavos() { return cantidad * ("chocolate".equals(sabor) ? 150 : 100); }
}

// Salida esperada:
// vainilla: 200 centavos
// chocolate: 450 centavos
// Seleccion rechazada
