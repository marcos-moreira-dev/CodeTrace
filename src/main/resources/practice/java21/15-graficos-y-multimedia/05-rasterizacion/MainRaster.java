// Tema:
// Bresenham rasteriza una línea discreta con acumulador de error entero; permite todas las direcciones y pendientes. Incluye ambos extremos. Se muestran las coordenadas sin renderizar una ventana.
// Modalidad: modelo teórico.
// Compilar: javac --release 21 -encoding UTF-8 LineaBresenham.java MainRaster.java
// Ejecutar: java MainRaster

public class MainRaster {
    public static void main(String[] args) throws Exception {
        System.out.println(LineaBresenham.puntos(0, 0, 4, 2));
        System.out.println(LineaBresenham.puntos(2, 2, 2, 0));
    }
}

// Salida esperada:
// [0,0, 1,1, 2,1, 3,2, 4,2]
// [2,2, 2,1, 2,0]
