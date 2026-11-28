// Tema:
// Una comunicación asíncrona desacopla envío y recepción. Un buzón FIFO local modela entrega ordenada de un canal; no garantiza orden global ni durabilidad en una red real.
// Modalidad: modelo teórico; nodos y transporte simulados en una JVM.
// Compilar: javac --release 21 -encoding UTF-8 Mensaje.java Buzon.java MainMensajeria.java
// Ejecutar: java MainMensajeria

public record Mensaje(String origen, String destino, String contenido) { }

// Salida esperada:
// Pendientes: 2
// pedido
// confirmacion
// Vacio: true
