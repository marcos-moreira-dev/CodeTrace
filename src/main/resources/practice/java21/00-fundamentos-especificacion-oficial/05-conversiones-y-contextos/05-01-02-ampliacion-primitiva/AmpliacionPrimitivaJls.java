// Tema:
// Una ampliación numérica no requiere cast, pero puede perder precisión: float no representa
// exactamente todos los enteros int. Al convertir de vuelta puede aparecer una diferencia.
// Compilar: javac --release 21 -encoding UTF-8 AmpliacionPrimitivaJls.java
// Ejecutar: java AmpliacionPrimitivaJls

public class AmpliacionPrimitivaJls {
    public static void main(String[] args) {
        int exacto = 16_777_217;
        float redondeado = exacto;
        System.out.println(exacto - (int) redondeado);
    }
}

// Salida esperada:
// 1
