// Tema:
// Clasificador por plantillas binarias de 5x5: compara una entrada con diez patrones fijos y elige menor distancia de Hamming. Un empate o una distancia superior al umbral se rechaza. No es OCR general ni aprendizaje a partir de muestras.
// Modalidad: ejecución real del algoritmo sobre datos pequeños de prueba.
// Compilar: javac --release 21 -encoding UTF-8 ReconocerDigito.java
// Ejecutar: java ReconocerDigito

public class ReconocerDigito {
    private static final String[][] PLANTILLAS = {
        {"01110","10001","10001","10001","01110"},
        {"00100","01100","00100","00100","01110"},
        {"01110","10001","00010","00100","11111"},
        {"11110","00001","01110","00001","11110"},
        {"10010","10010","11111","00010","00010"},
        {"11111","10000","11110","00001","11110"},
        {"01110","10000","11110","10001","01110"},
        {"11111","00001","00010","00100","00100"},
        {"01110","10001","01110","10001","01110"},
        {"01110","10001","01111","00001","01110"}
    };
    static int reconocer(String[] imagen) {
        if (imagen.length != 5) throw new IllegalArgumentException("Altura");
        for (String fila : imagen)
            if (!fila.matches("[01]{5}")) throw new IllegalArgumentException("Fila");
        int mejor = -1, minimo = 26;
        for (int d = 0; d < PLANTILLAS.length; d++) {
            int diferencia = 0;
            for (int y = 0; y < 5; y++)
                for (int x = 0; x < 5; x++)
                    if (imagen[y].charAt(x) != PLANTILLAS[d][y].charAt(x)) diferencia++;
            if (diferencia < minimo) { minimo = diferencia; mejor = d; }
            else if (diferencia == minimo) mejor = -1;
        }
        return minimo <= 2 ? mejor : -1;
    }

    public static void main(String[] args) {
        String[] entrada = PLANTILLAS[2].clone();
        System.out.println("Exacto: " + reconocer(entrada));
        char[] fila = entrada[0].toCharArray();
        fila[0] = fila[0] == '1' ? '0' : '1';
        entrada[0] = new String(fila);
        System.out.println("Con ruido: " + reconocer(entrada));
        System.out.println("Vacio: " + reconocer(new String[]{"00000","00000","00000","00000","00000"}));
    }
}

// Salida esperada:
// Exacto: 2
// Con ruido: 2
// Vacio: -1
