// Tema:
// Cliente solicita un servicio, servidor procesa la petición y responde. Aquí la llamada es local: muestra los roles sin confundirlos con máquinas físicas.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 Servidor.java Cliente.java MainClienteServidor.java
// Ejecutar: java MainClienteServidor

public class MainClienteServidor {
    public static void main(String[] args) throws Exception {
        Servidor servidor = new Servidor();
        Cliente cliente = new Cliente();
        System.out.println(cliente.solicitar(servidor, "pan"));
        System.out.println(cliente.solicitar(servidor, "leche"));
    }
}

// Salida esperada:
// Disponible
// No encontrado
