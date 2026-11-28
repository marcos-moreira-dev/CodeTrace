// Tema:
// Contar operaciones permite comparar crecimiento sin ruido de JIT, sistema operativo ni reloj. Aquí se contrasta un recorrido lineal con todos los pares ordenados, no se afirma una velocidad real.
// Modalidad: ejecución real con JDK 21.
// Compilar: javac --release 21 -encoding UTF-8 CompararComplejidad.java
// Ejecutar: java CompararComplejidad

public class CompararComplejidad {
    public static void main(String[] args) throws Exception {
        for (int n : new int[]{10, 20}) {
            int lineal = 0, cuadratico = 0;
            for (int i = 0; i < n; i++) lineal++;
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++) cuadratico++;
            System.out.println(n + ": " + lineal + " vs " + cuadratico);
        }
    }
}

// Salida esperada:
// 10: 10 vs 100
// 20: 20 vs 400
