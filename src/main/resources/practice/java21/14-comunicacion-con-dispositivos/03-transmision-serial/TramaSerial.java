// Tema:
// Trama UART 8E1: inicio 0, ocho bits de datos menos significativo primero, paridad par y parada 1. Modelo lógico, sin voltajes ni temporización.
// Modalidad: modelo teórico; no controla hardware.
// Compilar: javac --release 21 -encoding UTF-8 TramaSerial.java
// Ejecutar: java TramaSerial

public class TramaSerial {
    public static void main(String[] args) {
        int dato = 0x53;
        StringBuilder trama = new StringBuilder("0");
        for (int i = 0; i < 8; i++) trama.append((dato >>> i) & 1);
        trama.append(Integer.bitCount(dato) % 2);
        trama.append('1');
        System.out.println(trama);
        System.out.println("Bits: " + trama.length());
    }
}

// Salida esperada:
// 01100101001
// Bits: 11
