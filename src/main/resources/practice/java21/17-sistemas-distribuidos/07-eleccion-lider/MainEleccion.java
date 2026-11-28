// Tema:
// Modelo de elección por anillo: un mensaje recorre una vez los participantes fijos y transporta el mayor identificador; al regresar se difunde el líder elegido. Supone identificadores únicos, entrega fiable y un iniciador; no resuelve elecciones concurrentes ni particiones.
// Modalidad: modelo teórico; nodos y transporte simulados en una JVM.
// Compilar: javac --release 21 -encoding UTF-8 EleccionAnillo.java MainEleccion.java
// Ejecutar: java MainEleccion

public class MainEleccion {
    public static void main(String[] args) {
        int lider = EleccionAnillo.elegir(new int[]{4, 7, 2, 9}, 1);
        System.out.println("Lider: " + lider);
    }
}

// Salida esperada:
// Visita 2: candidato 7
// Visita 9: candidato 9
// Visita 4: candidato 9
// Lider: 9
