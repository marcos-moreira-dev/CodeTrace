// Tema:
// Una lambda puede tener un cuerpo de expresión o un bloque. Si el contrato devuelve un
// valor, el bloque debe retornar un valor en todas las rutas normales.
// Compilar: javac --release 21 -encoding UTF-8 -d . CuerpoLambda.java
// Ejecutar: java CuerpoLambda
// Comandos desde la carpeta de la unidad.

public class CuerpoLambda {
    public static void main(String[] args) {
        java.util.function.IntUnaryOperator doble = n -> n * 2;
        java.util.function.IntUnaryOperator signo = n -> {
            if (n < 0) return -1;
            if (n > 0) return 1;
            return 0;
        };
        System.out.println(doble.applyAsInt(6));
        System.out.println(signo.applyAsInt(-8));
    }
}

// Salida esperada:
// 12
// -1
