// Tema:
// Peterson: dos participantes anuncian intención, ceden el turno al otro y esperan mientras el otro quiere entrar y posee turno. Enumeramos intercalaciones de pasos atómicos, bajo memoria secuencialmente consistente; no es una implementación Java con campos ordinarios.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 PetersonSimulado.java MainPeterson.java
// Ejecutar: java MainPeterson

public class MainPeterson {
    public static void main(String[] args) throws Exception {
        System.out.println("Exclusion mutua en el modelo: " + PetersonSimulado.seguro());
    }
}

// Salida esperada:
// Exclusion mutua en el modelo: true
