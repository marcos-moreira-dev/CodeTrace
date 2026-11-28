// Tema:
// Replicación primaria/secundaria: una escritura crea una versión más reciente en la primaria. El retraso se controla entregando explícitamente la copia; la secundaria ignora actualizaciones anteriores. No simula durabilidad, elección ni recuperación de fallos.
// Modalidad: modelo teórico; nodos y transporte simulados en una JVM.
// Compilar: javac --release 21 -encoding UTF-8 Replica.java MainReplicacion.java
// Ejecutar: java MainReplicacion

public class Replica {
    private int version, valor;
    public void escribir(int nuevo) { valor = nuevo; version++; }
    public void replicar(int v, int dato) {
        if (v > version) { version = v; valor = dato; }
    }
    public int version() { return version; }
    public int leer() { return valor; }
}

// Salida esperada:
// Primaria: 20
// Secundaria antes: 10
// Secundaria despues: 20
