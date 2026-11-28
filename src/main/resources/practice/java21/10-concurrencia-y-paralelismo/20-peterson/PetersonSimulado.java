// Tema:
// Peterson: dos participantes anuncian intención, ceden el turno al otro y esperan mientras el otro quiere entrar y posee turno. Enumeramos intercalaciones de pasos atómicos, bajo memoria secuencialmente consistente; no es una implementación Java con campos ordinarios.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 PetersonSimulado.java MainPeterson.java
// Ejecutar: java MainPeterson

public class PetersonSimulado {
    public static boolean seguro() {
        return explorar(new int[2], new boolean[2], 0);
    }

    private static boolean explorar(int[] pc, boolean[] quiere, int turno) {
        if (pc[0] == 3 && pc[1] == 3) return false;
        for (int i = 0; i < 2; i++) {
            int otro = 1 - i;
            if (pc[i] == 5 || (pc[i] == 2 && quiere[otro] && turno == otro)) continue;
            int[] siguiente = pc.clone();
            boolean[] bandera = quiere.clone();
            int nuevoTurno = turno;
            // 0: anunciar; 1: ceder; 2: esperar; 3: sección crítica;
            // 4: retirar intención; 5: terminado.
            if (pc[i] == 0) bandera[i] = true;
            if (pc[i] == 1) nuevoTurno = otro;
            if (pc[i] == 4) bandera[i] = false;
            siguiente[i]++;
            if (!explorar(siguiente, bandera, nuevoTurno)) return false;
        }
        return true;
    }
}

// Salida esperada:
// Exclusion mutua en el modelo: true
