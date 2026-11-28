// Tema:
// Las pruebas basadas en propiedades generan muchos casos reproducibles. Una inversión debe preservar longitud y aplicar dos veces la operación debe devolver el arreglo original; la semilla permite repetir el fallo.
// Modalidad: ejecución real con JDK 21.
// Compilar: javac --release 21 -encoding UTF-8 PruebaPropiedades.java
// Ejecutar: java PruebaPropiedades

import java.util.Arrays;
import java.util.Random;

public class PruebaPropiedades {
    static int[] invertir(int[] datos) {
        int[] copia = new int[datos.length];
        for (int i = 0; i < datos.length; i++) copia[i] = datos[datos.length - 1 - i];
        return copia;
    }

    public static void main(String[] args) throws Exception {
        Random azar = new Random(21);
        for (int caso = 0; caso < 100; caso++) {
            int[] datos = new int[azar.nextInt(20)];
            for (int i = 0; i < datos.length; i++) datos[i] = azar.nextInt();
            int[] invertido = invertir(datos);
            if (invertido.length != datos.length || !Arrays.equals(datos, invertir(invertido)))
                throw new AssertionError("Caso " + caso);
            for (int i = 0; i < datos.length; i++)
                if (invertido[i] != datos[datos.length - 1 - i]) throw new AssertionError("Posicion");
        }
        System.out.println("Propiedades: 100 casos OK");
    }
}

// Salida esperada:
// Propiedades: 100 casos OK
