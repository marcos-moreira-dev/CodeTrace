// Tema:
// PCM de 16 bits representa amplitudes discretas con signo. Se limita al intervalo [-1,1] y se usa escala simétrica 32767 para simplificar; esto no usa el código -32768.
// Modalidad: ejecución real con datos locales.
// Compilar: javac --release 21 -encoding UTF-8 CuantizacionPCM.java
// Ejecutar: java CuantizacionPCM

public class CuantizacionPCM {
    static short cuantizar(double amplitud) {
        double limitada = Math.max(-1, Math.min(1, amplitud));
        return (short) Math.round(limitada * 32767);
    }

    public static void main(String[] args) throws Exception {
        for (double amplitud : new double[]{-1, -0.5, 0, 0.5, 1, 1.2})
            System.out.println(cuantizar(amplitud));
    }
}

// Salida esperada:
// -32767
// -16383
// 0
// 16384
// 32767
// 32767
