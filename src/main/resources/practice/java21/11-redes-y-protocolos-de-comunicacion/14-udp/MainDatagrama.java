// Tema:
// UDP envía datagramas independientes. Cada receive obtiene un datagrama, no un flujo; un buffer demasiado pequeño puede truncarlo. Se limita la espera y se usa loopback.
// Modalidad: ejecución real.
// Compilar: javac --release 21 -encoding UTF-8 Receptor.java Emisor.java MainDatagrama.java
// Ejecutar: java MainDatagrama

public class MainDatagrama {
    public static void main(String[] args) throws Exception {
        try (Receptor receptor = new Receptor()) {
            Emisor.enviar(receptor.puerto(), "Hola UDP");
            System.out.println(receptor.recibir());
        }
    }
}

// Salida esperada:
// Hola UDP
