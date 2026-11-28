// Tema:
// Lectura sucia: T2 observa un valor escrito por T1 que aún no se confirmó y luego se revierte. Intercalación manual, no prueba de un nivel de aislamiento de un motor concreto.
// Modalidad: modelo teórico; no es un motor de bases de datos.
// Compilar: javac --release 21 -encoding UTF-8 LecturaSuciaSimulada.java
// Ejecutar: java LecturaSuciaSimulada

public class LecturaSuciaSimulada {
    public static void main(String[] args) throws Exception {
        int confirmado = 100;
        int borradorT1 = 70;
        int lecturaT2 = borradorT1; // Política incorrecta: deja ver datos sin commit.
        System.out.println("T2 lee: " + lecturaT2);
        borradorT1 = confirmado; // T1 revierte.
        System.out.println("Confirmado: " + confirmado);
        System.out.println("Lectura sucia: " + (lecturaT2 != borradorT1));
    }
}

// Salida esperada:
// T2 lee: 70
// Confirmado: 100
// Lectura sucia: true
