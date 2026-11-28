// Tema:
// Modelo de cabecera Ethernet II: destino, origen y EtherType preceden los datos. No se genera una trama física ni se calcula FCS; se representa solo la cabecera y la carga lógica.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 TramaEthernet.java
// Ejecutar: java TramaEthernet

import java.nio.ByteBuffer;

public class TramaEthernet {
    public static void main(String[] args) throws Exception {
        ByteBuffer trama = ByteBuffer.allocate(17);
        trama.put(new byte[]{2,0,0,0,0,2});
        trama.put(new byte[]{2,0,0,0,0,1});
        trama.putShort((short) 0x0800);
        trama.put(new byte[]{1,2,3});
        System.out.println("Cabecera: 14");
        System.out.printf("EtherType: %04x%n", trama.getShort(12) & 0xffff);
        System.out.println("Carga: " + (trama.position() - 14));
    }
}

// Salida esperada:
// Cabecera: 14
// EtherType: 0800
// Carga: 3
