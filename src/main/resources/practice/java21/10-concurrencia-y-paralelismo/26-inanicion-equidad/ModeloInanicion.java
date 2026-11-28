// Tema:
// Un modelo de prioridad estricta puede postergar indefinidamente un trabajo de baja prioridad si siempre llegan trabajos más prioritarios. Se observa un prefijo finito, no una prueba empírica de infinito.
// Modalidad: modelo y ejecución controlada.
// Compilar: javac --release 21 -encoding UTF-8 ModeloInanicion.java
// Ejecutar: java ModeloInanicion

public class ModeloInanicion {
    public static void main(String[] args) throws Exception {
        int esperaBaja = 0;
        for (int reloj = 0; reloj < 5; reloj++) {
            // En cada instante llega un trabajo de prioridad alta.
            System.out.println("Atiende alta");
            esperaBaja++;
        }
        System.out.println("Espera baja: " + esperaBaja);
    }
}

// Salida esperada:
// Atiende alta
// Atiende alta
// Atiende alta
// Atiende alta
// Atiende alta
// Espera baja: 5
