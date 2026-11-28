// Tema:
// En coordenadas cartesianas, la matriz de rotación antihoraria usa coseno y seno. Los resultados de punto flotante se redondean para esta rotación de 90 grados.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 Rotacion2D.java
// Ejecutar: java Rotacion2D

public class Rotacion2D {
    public static void main(String[] args) throws Exception {
        double angulo = Math.toRadians(90);
        double x = 2, y = 1;
        double nuevoX = Math.cos(angulo) * x - Math.sin(angulo) * y;
        double nuevoY = Math.sin(angulo) * x + Math.cos(angulo) * y;
        System.out.println(Math.round(nuevoX) + " " + Math.round(nuevoY));
    }
}

// Salida esperada:
// -1 2
