// Tema:
// Replicación primaria/secundaria: una escritura crea una versión más reciente en la primaria. El retraso se controla entregando explícitamente la copia; la secundaria ignora actualizaciones anteriores. No simula durabilidad, elección ni recuperación de fallos.
// Modalidad: modelo teórico; nodos y transporte simulados en una JVM.
// Compilar: javac --release 21 -encoding UTF-8 Replica.java MainReplicacion.java
// Ejecutar: java MainReplicacion

public class MainReplicacion {
    public static void main(String[] args) {
        Replica primaria = new Replica(), secundaria = new Replica();
        primaria.escribir(10);
        secundaria.replicar(primaria.version(), primaria.leer());
        primaria.escribir(20);
        System.out.println("Primaria: " + primaria.leer());
        System.out.println("Secundaria antes: " + secundaria.leer());
        secundaria.replicar(primaria.version(), primaria.leer());
        secundaria.replicar(1, 10);
        System.out.println("Secundaria despues: " + secundaria.leer());
    }
}

// Salida esperada:
// Primaria: 20
// Secundaria antes: 10
// Secundaria despues: 20
