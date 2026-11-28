// Tema:
// Modelo de elección por anillo: un mensaje recorre una vez los participantes fijos y transporta el mayor identificador; al regresar se difunde el líder elegido. Supone identificadores únicos, entrega fiable y un iniciador; no resuelve elecciones concurrentes ni particiones.
// Modalidad: modelo teórico; nodos y transporte simulados en una JVM.
// Compilar: javac --release 21 -encoding UTF-8 EleccionAnillo.java MainEleccion.java
// Ejecutar: java MainEleccion

public class EleccionAnillo {
    public static int elegir(int[] nodos, int inicio) {
        if (nodos.length == 0 || inicio < 0 || inicio >= nodos.length) throw new IllegalArgumentException("Anillo");
        int candidato = nodos[inicio];
        for (int salto = 1; salto < nodos.length; salto++) {
            int actual = nodos[(inicio + salto) % nodos.length];
            candidato = Math.max(candidato, actual);
            System.out.println("Visita " + actual + ": candidato " + candidato);
        }
        return candidato;
    }
}

// Salida esperada:
// Visita 2: candidato 7
// Visita 9: candidato 9
// Visita 4: candidato 9
// Lider: 9
