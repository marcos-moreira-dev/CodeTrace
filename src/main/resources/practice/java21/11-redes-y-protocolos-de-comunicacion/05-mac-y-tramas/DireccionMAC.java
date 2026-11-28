// Tema:
// Una dirección MAC Ethernet de 48 bits se representa mediante seis octetos. El bit menos significativo del primer octeto indica dirección individual o de grupo.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 DireccionMAC.java
// Ejecutar: java DireccionMAC

public class DireccionMAC {
    public static void main(String[] args) throws Exception {
        byte[] mac = {2, 0, 0, 0, 0, 17};
        for (int i = 0; i < mac.length; i++) {
            if (i != 0) System.out.print(":");
            System.out.printf("%02x", mac[i] & 255);
        }
        System.out.println();
        System.out.println("Grupo: " + ((mac[0] & 1) != 0));
    }
}

// Salida esperada:
// 02:00:00:00:00:11
// Grupo: false
