// Tema:
// Modelo de leer-modificar-escribir: dos participantes leen el mismo valor y luego escriben sus resultados. Se pierde una actualización.
// Modalidad: modelo y ejecución controlada.
// Compilar: javac --release 21 -encoding UTF-8 IntercalacionIncremento.java
// Ejecutar: java IntercalacionIncremento



public class IntercalacionIncremento {
    public static void main(String[] args) throws Exception {
        int contador = 0;
        int lecturaA = contador;
        int lecturaB = contador;
        contador = lecturaA + 1;
        contador = lecturaB + 1;
        System.out.println("Esperado: 2");
        System.out.println("Observado: " + contador);
    }
}

// Salida esperada:
// Esperado: 2
// Observado: 1
