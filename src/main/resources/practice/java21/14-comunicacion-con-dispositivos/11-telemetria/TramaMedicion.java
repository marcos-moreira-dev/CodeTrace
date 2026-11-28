// Tema:
// Telemetría binaria: marcador 0x7e, longitud fija 4, id y medición short big-endian, checksum de suma módulo 256. El decodificador conserva fragmentos y rechaza longitud y checksum incorrectos; no es autenticación ni protocolo industrial.
// Modalidad: modelo teórico; no controla hardware.
// Compilar: javac --release 21 -encoding UTF-8 TramaMedicion.java Codificador.java Decodificador.java MainTelemetria.java
// Ejecutar: java MainTelemetria

public record TramaMedicion(short id, short valor) { }

// Salida esperada:
// 3: 25
// Checksum invalido
