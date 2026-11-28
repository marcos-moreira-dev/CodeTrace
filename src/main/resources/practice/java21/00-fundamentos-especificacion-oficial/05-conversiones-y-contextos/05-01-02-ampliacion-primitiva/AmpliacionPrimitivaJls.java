// CodeTrace | Fundamentos - Especificacion oficial
// Referencia: Java SE 21, JLS §5.1.2; ejemplo 5.1.2-1
// Compilar: javac --release 21 -encoding UTF-8 AmpliacionPrimitivaJls.java
// Ejecutar: java AmpliacionPrimitivaJls
// Tema JLS:
// Una ampliación puede perder precisión aunque no necesite cast: float no representa
// exactamente todos los valores int. El entero elegido se redondea; al convertirlo de
// vuelta a int aparece una diferencia.
// Convertir un int a float puede perder precisión aunque sea una ampliación.

public class AmpliacionPrimitivaJls {
    public static void main(String[] args) {
        int exacto = 16_777_217;
        float redondeado = exacto;
        System.out.println(exacto - (int) redondeado);
    }
}

// Salida esperada:
// 1
