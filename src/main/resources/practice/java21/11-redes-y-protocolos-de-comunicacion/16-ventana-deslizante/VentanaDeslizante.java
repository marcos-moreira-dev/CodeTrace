// Tema:
// Ventana deslizante: base es el primer número sin confirmar y siguiente el próximo por emitir. ACK acumulativo desplaza la base; modelo sin pérdidas ni wrap-around, no TCP completo.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 VentanaDeslizante.java MainVentana.java
// Ejecutar: java MainVentana

public class VentanaDeslizante {
    private final int capacidad;
    private int base, siguiente;
    public VentanaDeslizante(int capacidad) {
        if (capacidad <= 0) throw new IllegalArgumentException("Capacidad");
        this.capacidad = capacidad;
    }
    public int enviar() {
        return siguiente - base < capacidad ? siguiente++ : -1;
    }
    public void confirmar(int hastaExclusivo) {
        if (hastaExclusivo < base || hastaExclusivo > siguiente)
            throw new IllegalArgumentException("ACK");
        base = hastaExclusivo;
    }
}

// Salida esperada:
// 0
// 1
// Llena: true
// 2
