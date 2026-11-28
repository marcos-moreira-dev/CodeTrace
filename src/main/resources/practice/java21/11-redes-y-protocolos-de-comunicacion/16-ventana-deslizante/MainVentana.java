// Tema:
// Ventana deslizante: base es el primer número sin confirmar y siguiente el próximo por emitir. ACK acumulativo desplaza la base; modelo sin pérdidas ni wrap-around, no TCP completo.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 VentanaDeslizante.java MainVentana.java
// Ejecutar: java MainVentana

public class MainVentana {
    public static void main(String[] args) throws Exception {
        VentanaDeslizante v = new VentanaDeslizante(2);
        System.out.println(v.enviar());
        System.out.println(v.enviar());
        System.out.println("Llena: " + (v.enviar() == -1));
        v.confirmar(1);
        System.out.println(v.enviar());
    }
}

// Salida esperada:
// 0
// 1
// Llena: true
// 2
