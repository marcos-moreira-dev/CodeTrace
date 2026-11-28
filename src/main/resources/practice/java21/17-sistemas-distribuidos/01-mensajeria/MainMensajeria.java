// Tema:
// Una comunicación asíncrona desacopla envío y recepción. Un buzón FIFO local modela entrega ordenada de un canal; no garantiza orden global ni durabilidad en una red real.
// Modalidad: modelo teórico; nodos y transporte simulados en una JVM.
// Compilar: javac --release 21 -encoding UTF-8 Mensaje.java Buzon.java MainMensajeria.java
// Ejecutar: java MainMensajeria

public class MainMensajeria {
    public static void main(String[] args) {
        Buzon buzon = new Buzon();
        buzon.enviar(new Mensaje("A", "B", "pedido"));
        buzon.enviar(new Mensaje("A", "B", "confirmacion"));
        System.out.println("Pendientes: " + buzon.pendientes());
        System.out.println(buzon.recibir().orElseThrow().contenido());
        System.out.println(buzon.recibir().orElseThrow().contenido());
        System.out.println("Vacio: " + buzon.recibir().isEmpty());
    }
}

// Salida esperada:
// Pendientes: 2
// pedido
// confirmacion
// Vacio: true
