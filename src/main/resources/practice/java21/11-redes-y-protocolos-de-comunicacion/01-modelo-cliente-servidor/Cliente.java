// Tema:
// Cliente solicita un servicio, servidor procesa la petición y responde. Aquí la llamada es local: muestra los roles sin confundirlos con máquinas físicas.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 Servidor.java Cliente.java MainClienteServidor.java
// Ejecutar: java MainClienteServidor

public class Cliente {
    public String solicitar(Servidor servidor, String producto) {
        return servidor.consultar(producto);
    }
}

// Salida esperada:
// Disponible
// No encontrado
