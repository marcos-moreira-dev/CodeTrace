// Tema:
// System.nanoTime mide intervalos monotónicos, no hora de calendario. Se calienta el método y se consume el resultado para evitar una comparación ingenua de tiempos. La prueba no exige una duración específica.
// Modalidad: ejecución real con JDK 21.
// Compilar: javac --release 21 -encoding UTF-8 MedirNanoTime.java
// Ejecutar: java MedirNanoTime

public class MedirNanoTime {
    static long sumar(int n) {
        long total = 0;
        for (int i = 1; i <= n; i++) total += i;
        return total;
    }

    public static void main(String[] args) throws Exception {
        long resultado = 0;
        for (int i = 0; i < 10; i++) resultado += sumar(1000);
        long inicio = System.nanoTime();
        resultado += sumar(1000);
        long duracion = System.nanoTime() - inicio;
        System.out.println("Resultado: " + resultado);
        System.out.println("Intervalo no negativo: " + (duracion >= 0));
    }
}

// Salida esperada:
// Resultado: 5505500
// Intervalo no negativo: true
