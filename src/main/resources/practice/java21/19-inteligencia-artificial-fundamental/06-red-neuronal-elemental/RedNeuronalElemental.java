// Tema:
// Red 2-2-1 con sigmoide, propagación hacia adelante y gradiente del error cuadrático. Los pesos iniciales fijos rompen simetría. El ajuste por muestra calcula deltas antes de cambiar los pesos de salida. Aprende AND como demostración, sin afirmar generalización.
// Modalidad: ejecución real del algoritmo sobre datos pequeños de prueba.
// Compilar: javac --release 21 -encoding UTF-8 RedNeuronalElemental.java
// Ejecutar: java RedNeuronalElemental

public class RedNeuronalElemental {
    private final double[][] w = {{0.2,-0.3},{-0.4,0.1}};
    private final double[] b = {0.1,-0.1}, v = {0.3,-0.2};
    private double c = 0.1;
    static double sigmoide(double z) { return 1.0 / (1.0 + Math.exp(-z)); }
    double[] ocultas(double[] x) {
        double[] h = new double[2];
        for (int j = 0; j < h.length; j++) {
            double z = b[j];
            for (int i = 0; i < x.length; i++) z += w[j][i] * x[i];
            h[j] = sigmoide(z);
        }
        return h;
    }
    double salida(double[] h) {
        double z = c;
        for (int j = 0; j < h.length; j++) z += v[j] * h[j];
        return sigmoide(z);
    }
    double predecir(double[] x) { return salida(ocultas(x)); }
    void ajustar(double[] x, double objetivo, double tasa) {
        double[] h = ocultas(x);
        double p = salida(h);
        double delta = (objetivo - p) * p * (1 - p);
        for (int j = 0; j < h.length; j++) {
            double dOculta = delta * v[j] * h[j] * (1 - h[j]);
            for (int i = 0; i < x.length; i++) w[j][i] += tasa * dOculta * x[i];
            b[j] += tasa * dOculta;
            v[j] += tasa * delta * h[j];
        }
        c += tasa * delta;
    }
    double error(double[][] x, double[] y) {
        double suma = 0;
        for (int i = 0; i < x.length; i++) {
            double e = y[i] - predecir(x[i]);
            suma += e * e;
        }
        return suma / x.length;
    }

    public static void main(String[] args) {
        double[][] x = {{0,0},{0,1},{1,0},{1,1}};
        double[] y = {0,0,0,1};
        RedNeuronalElemental red = new RedNeuronalElemental();
        double inicial = red.error(x,y);
        for (int epoca = 0; epoca < 3000; epoca++)
            for (int i = 0; i < x.length; i++) red.ajustar(x[i], y[i], 0.5);
        double finalError = red.error(x,y);
        System.out.println("Error disminuye: " + (finalError < inicial));
        System.out.println("Error menor a 0.02: " + (finalError < 0.02));
        for (double[] entrada : x) System.out.print(red.predecir(entrada) >= 0.5 ? 1 : 0);
        System.out.println();
    }
}

// Salida esperada:
// Error disminuye: true
// Error menor a 0.02: true
// 0001
