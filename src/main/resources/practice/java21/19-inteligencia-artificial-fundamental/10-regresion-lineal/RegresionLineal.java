// Tema:
// Regresión lineal por mínimos cuadrados: pendiente = covariación / variación de x, intercepto = mediaY - pendiente*mediaX. Se rechaza variación cero. Se predice un número continuo, no una clase.
// Modalidad: ejecución real del algoritmo sobre datos pequeños de prueba.
// Compilar: javac --release 21 -encoding UTF-8 RegresionLineal.java
// Ejecutar: java RegresionLineal

public class RegresionLineal {
    public static void main(String[] args) {
        double[] x = {1,2,3,4}, y = {3,5,7,9};
        double mediaX = 0, mediaY = 0;
        for (int i = 0; i < x.length; i++) { mediaX += x[i]; mediaY += y[i]; }
        mediaX /= x.length; mediaY /= y.length;
        double cov = 0, var = 0;
        for (int i = 0; i < x.length; i++) {
            cov += (x[i]-mediaX)*(y[i]-mediaY);
            var += (x[i]-mediaX)*(x[i]-mediaX);
        }
        if (var == 0) throw new IllegalArgumentException("X constante");
        double m = cov/var, b = mediaY-m*mediaX;
        double error = 0;
        for (int i = 0; i < x.length; i++) {
            double e = y[i]-(m*x[i]+b);
            error += e*e;
        }
        System.out.println("Pendiente: " + m);
        System.out.println("Intercepto: " + b);
        System.out.println("Prediccion x=5: " + (m*5+b));
        System.out.println("ECM: " + error/x.length);
    }
}

// Salida esperada:
// Pendiente: 2.0
// Intercepto: 1.0
// Prediccion x=5: 11.0
// ECM: 0.0
