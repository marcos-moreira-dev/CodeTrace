// Tema:
// Modelo de alternancia: cada proceso conserva su propio contador. El cambio de contexto real lo realiza el kernel, no este arreglo.
// Modalidad: modelo teórico; no reproduce el kernel real.
// Compilar: javac --release 21 -encoding UTF-8 CambioContexto.java
// Ejecutar: java CambioContexto

public class CambioContexto {
    public static void main(String[] args) throws Exception {
        int[] contador = {0, 0};
        for (int paso = 0; paso < 4; paso++) {
            int activo = paso % contador.length;
            contador[activo]++;
            System.out.println("P" + activo + ": " + contador[activo]);
        }
    }
}

// Salida esperada:
// P0: 1
// P1: 1
// P0: 2
// P1: 2
