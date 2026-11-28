// Tema:
// Lectura no repetible: T1 consulta una fila dos veces y T2 confirma un cambio entre ambas lecturas. Cada lectura toma el último valor confirmado; no se simula un snapshot.
// Modalidad: modelo teórico; no es un motor de bases de datos.
// Compilar: javac --release 21 -encoding UTF-8 LecturaNoRepetible.java
// Ejecutar: java LecturaNoRepetible

public class LecturaNoRepetible {
    public static void main(String[] args) throws Exception {
        int confirmado = 100;
        int primeraT1 = confirmado;
        confirmado = 80; // Commit de T2.
        int segundaT1 = confirmado;
        System.out.println("Primera: " + primeraT1);
        System.out.println("Segunda: " + segundaT1);
    }
}

// Salida esperada:
// Primera: 100
// Segunda: 80
